/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.calls;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.hendraanggrian.appcompat.widget.SocialTextView;
import com.hendraanggrian.appcompat.widget.SocialView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.onurkagan.ksnack_lib.KSnack.KSnack;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.R;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.models.postAction;
import com.soshoplus.lite.models.simpleResponse;
import com.soshoplus.lite.ui.hashTagsPosts;
import com.soshoplus.lite.ui.user_profile.userProfile;
import com.soshoplus.lite.utils.queries;
import com.soshoplus.lite.utils.retrofitInstance;
import com.soshoplus.lite.adapters.timelineFeedAdapter;
import com.soshoplus.lite.models.postsfeed.post;
import com.soshoplus.lite.models.postsfeed.postList;
import com.soshoplus.lite.models.postsfeed.reactions.like_dislike;
import com.soshoplus.lite.models.postsfeed.sharepost.shareResponse;
import com.soshoplus.lite.models.userprofile.userData;
import com.soshoplus.lite.ui.auth.signIn;
import com.soshoplus.lite.utils.xpopup.previewProfilePopup;
import com.soshoplus.lite.utils.xpopup.sharePopup;
import com.soshoplus.lite.utils.xpopup.timelineAdFullViewPopup;
import com.soshoplus.lite.utils.xpopup.timelineImageViewPopup;

import java.io.File;
import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.transform.CircleCropTransformation;
import de.adorsys.android.securestoragelibrary.SecurePreferences;
import de.adorsys.android.securestoragelibrary.SecureStorageException;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class timelineCalls {
    
    private final static String TAG = "Timeline Calls";
    /*context*/
    private Context context;
    /*......*/
    private queries rxJavaQueries;
    private String accessToken, userId, timezone;
    private BasePopupView popupView;
    
    /*TIMELINE FEED*/
    private Observable<postList> postListObserve;
    private List<post> timelinePosts;
    private static String firstData = null;
    private timelineFeedAdapter feedAdapter;
    
    /*POST LIKE_DISLIKE*/
    private Observable<like_dislike> like_dislikeObservable;
    
    /*SHARE ON TIMELINE*/
    private Observable<shareResponse> shareResponseObservable;
    private static String share_post_on_timeline = "share_post_on_timeline";
    
    /*.......*/
    private KSnack snack;
    /*........*/
    private static int totalItems;
    private static String lastItemID;
    
    /*........*/
    private static String fullName, timeAgo, noLikes, noComments;
    private static boolean isLiked;
    /*......*/
    private static String adFullName, adLocation, adDescription, adHeadline;
    
    /*......*/
    private Observable<postAction> postActionObservable;
    private static String[] post_option = {"Report post", "Copy link", "Share post", "Save post", "Hide post"};

    /*......*/
    private Observable<simpleResponse> createPostResponse;
    
    /*constructor*/
    public timelineCalls (Context context) {
        this.context = context;
    
        userId = SecurePreferences.getStringValue(context, "userId", "0");
        timezone = SecurePreferences.getStringValue(context, "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(context, "accessToken"
                , "0");
    
        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);
    }
    
    public void getTimelineFeed(RecyclerView timelinePostsList,
                                ProgressBar progressBarTimeline, String type, RelativeLayout timelineErrorLayout,
                                MaterialButton tryAgain, SmartRefreshLayout refreshPostsLayout, String hashTag) {

        /*load posts*/
        Log.d(TAG, "LOADING : " + firstData);
        loadPosts(timelinePostsList, timelineErrorLayout,
                tryAgain, progressBarTimeline, refreshPostsLayout, type, hashTag);
        
        tryAgain.setOnClickListener(view -> {
            /*hide error layout*
            disable refreshing*/
            timelineErrorLayout.setVisibility(View.GONE);
            
            /*.........*/
            timelinePostsList.setVisibility(View.GONE);
            progressBarTimeline.setVisibility(View.VISIBLE);

            /*hide progress*/
            new Handler().postDelayed(() -> {
                progressBarTimeline.setVisibility(View.GONE);
            }, 500);
        });

        refreshPostsLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                firstData = "0";
                loadPosts(timelinePostsList, timelineErrorLayout,
                        tryAgain, progressBarTimeline, refreshPostsLayout, type, hashTag);
            }
        });

    }
    
    private void loadPosts(RecyclerView timelinePostsList,
                           RelativeLayout timelineErrorLayout, MaterialButton tryAgain,
                           ProgressBar progressBarTimeline, SmartRefreshLayout refreshPostsLayout,
                           String type, String hashTag) {
    
        postListObserve =
                rxJavaQueries.getTimelinePosts(accessToken,
                BuildConfig.server_key, type, hashTag, "10", firstData);
    
        postListObserve.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<postList>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
                
                    @Override
                    public void onNext (@NonNull postList postList) {
                        
                        if (firstData == null) {
        
                            if(postList.getApiStatus() == 200) {
                                Log.d(TAG, "onNext: LOAD FIRST DATA");

                                timelinePostsList.setLayoutManager(new LinearLayoutManager(context));
            
                                timelinePosts = getAll(postList);
            
                                if (timelinePosts != null) {
                                    for (post firstPosts : timelinePosts) {
                                        Log.d(TAG, "onNext: POSTS ID : " + firstPosts.getPostId());
                                    }

                                    /*last item ID*/
                                    totalItems = timelinePosts.size();
                                    if (totalItems > 2) {
                                        lastItemID = timelinePosts.get(totalItems - 2).getPostId();
                                        firstData = lastItemID;
                                        Log.d(TAG, "onNext: AFTER POST ID : " + lastItemID);
                                    }
                                }

                                /*pullTorefesh */
                                if (refreshPostsLayout.isRefreshing()) {
                                    refreshPostsLayout.finishRefresh();
                                }
    
                                /*hide progress*/
                                new Handler().postDelayed(() -> progressBarTimeline.setVisibility(View.GONE), 500);
                                
                                /*initialize adapter*/
                                feedAdapter = new timelineFeedAdapter(timelinePosts);
                                feedAdapter.setAnimationEnable(false);
            
                                /*setting adapter*/
                                timelinePostsList.setAdapter(feedAdapter);

                                /*.........*/
                                timelinePostsList.setVisibility(View.VISIBLE);

                                /*setting loadmore module*/
                                if (totalItems > 2) {
                                    /*setting loadmore module*/
                                    feedAdapter.getLoadMoreModule().setAutoLoadMore(true);
                                    feedAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
                                        /*load more posts*/
                                        loadPosts(timelinePostsList, timelineErrorLayout, tryAgain,
                                                progressBarTimeline, refreshPostsLayout, type, hashTag);
                                    });
                                }
                                feedAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                                    @Override
                                    public void onItemChildClick (@androidx.annotation.NonNull BaseQuickAdapter adapter,
                                                                  @androidx.annotation.NonNull View view, int position) {

                                        switch (view.getId()) {
                                            case R.id.like_btn:
                                                MaterialButton likeBtn = view.findViewById(R.id.like_btn);
                                                likePost(feedAdapter.getData().get(position).getPostId(), position, likeBtn);
                                                break;
                                            case R.id.post_option:
                                                new XPopup.Builder(context).asCenterList(null, post_option, (option_position, text) -> {

                                                    switch (option_position) {

                                                        case 0:
                                                            reportPOST(feedAdapter.getData().get(position).getPostId());
                                                            break;
                                                        case 1:
                                                            copyLink(feedAdapter.getData().get(position).getUrl(), view.getContext());
                                                            break;
                                                        case 2:
                                                            sharePOST(feedAdapter.getData().get(position));
                                                            break;
                                                        case 3:
                                                            savePOST(feedAdapter.getData().get(position).getPostId());
                                                            break;
                                                        case 4:
                                                            hidePOST(feedAdapter, position);
                                                            break;
                                                        default:
                                                            Log.d(TAG, "onSelect: " + feedAdapter.getData().get(position).getPostId());
                                                    }
                                                }).show();
                                                break;
                                            case R.id.profile_pic:
                                                new Handler().postDelayed(() -> {
                                                    new XPopup.Builder(context).asCustom(new previewProfilePopup(context,
                                                            feedAdapter.getData().get(position).getUserId()).show());

                                                }, 1000);
                                                break;
                                            case R.id.ad_media:
                                                ImageView ad_media = view.findViewById(R.id.ad_media);
                                                showAdViewPopup(ad_media, feedAdapter.getData().get(position).getAdMedia(), position);
                                                break;
                                            case R.id.post_image: {
                                                ImageView post_image = view.findViewById(R.id.post_image);
                                                /*......*/
                                                showViewPopup(post_image, feedAdapter.getData().get(position).getPostFile(), position);
                                                break;
                                            }
                                            case R.id.shared_post_image: {
                                                ImageView post_image = view.findViewById(R.id.shared_post_image);

                                                /*......*/
                                                showViewPopup(post_image, feedAdapter.getData().get(position).getPostFile(), position);
                                                break;
                                            }
                                            case R.id.article_thumbnail: {
                                                ImageView post_image = view.findViewById(R.id.article_thumbnail);
                                                /*......*/
                                                showViewPopup(post_image, feedAdapter.getData().get(position).getBlog().getThumbnail(), position);
                                                break;
                                            }

                                            /*mentions/ hashtags/ links*/
                                            case R.id.post_contents: {
                                                SocialTextView socialTextView = view.findViewById(R.id.post_contents);
                                                socialTextView.setOnLongClickListener(null);
                                                socialTextView.setOnHashtagClickListener(new SocialView.OnClickListener() {
                                                    @Override
                                                    public void onClick(@androidx.annotation.NonNull SocialView view,
                                                                        @androidx.annotation.NonNull CharSequence text) {

                                                        Intent intent = new Intent(context, hashTagsPosts.class);
                                                        intent.putExtra("hashTag", text.toString());
                                                        context.startActivity(intent);
                                                    }
                                                });

                                                socialTextView.setOnMentionClickListener(new SocialView.OnClickListener() {
                                                    @Override
                                                    public void onClick(@androidx.annotation.NonNull SocialView view,
                                                                        @androidx.annotation.NonNull CharSequence text) {

                                                        Intent intent = new Intent(context, userProfile.class);
                                                        intent.putExtra("username", text.toString());
                                                        context.startActivity(intent);
                                                    }
                                                });
                                                break;
                                            }
                                        }
                                    }
                                });
                            }
                            else {
                                apiErrors errors = postList.getErrors();
                                Log.d(TAG, "ERROR FROM API : " + errors.getErrorText());

                                if (errors.getErrorId().equals("2")) {
                                    popupView = new XPopup.Builder(context).asConfirm("Oops..!", "Sorry session expired please sign in again",
                                            null, "Dismiss", () -> {

                                        popupView.delayDismissWith(500, new Runnable() {
                                            @Override
                                            public void run() {

                                                try {
                                                    SecurePreferences.clearAllValues(context);
                                                    Log.d(TAG, "onNext: " + "USER LOGGED OUT");

                                                    Intent intent = new Intent(context, signIn.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                    context.startActivity(intent);
                                                    ((FragmentActivity) context).finish();

                                                } catch (SecureStorageException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });

                                    }, null, true, 0).show();
                                }
                                else {
                                    /*displaying error*/
                                    timelineErrorLayout.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                        else {
                            if (postList.getApiStatus() == 200) {
                                Log.d(TAG, "onNext: LOAD MORE DATA");
                                
                                /*getting new posts*/
                                List<post> tobeAdded = getAll(postList);
    
                                if (tobeAdded != null) {
                                    
                                    for (post firstPosts : tobeAdded) {
                                        Log.d(TAG, "onNext: POSTS ID : " + firstPosts.getPostId());
                                    }

                                    if (feedAdapter == null) {
                                        /*refresh data*/
                                        firstData = "0";
                                        loadPosts(timelinePostsList, timelineErrorLayout, tryAgain, progressBarTimeline, refreshPostsLayout, type, hashTag);
                                    }
                                    else {
                                        feedAdapter.addData(tobeAdded);
                                        /*last item ID*/
                                        /*last item ID*/
                                        totalItems = tobeAdded.size();

                                        if (totalItems > 2) {
                                            lastItemID = tobeAdded.get(totalItems - 2).getPostId();
                                            firstData = lastItemID;
                                            Log.d(TAG, "onNext: AFTER POST ID : " + lastItemID);

                                            feedAdapter.getLoadMoreModule().loadMoreComplete();
                                        }
                                        else {
                                            feedAdapter.getLoadMoreModule().loadMoreEnd();
                                        }

                                        Log.d(TAG, "ADAPTER ITEM COUNT : " + feedAdapter.getItemCount());
                                    }
                                }
                            }
                            else {
                                apiErrors errors = postList.getErrors();
                                Log.d(TAG, "ERROR FROM API : " + errors.getErrorId());
                                Log.d(TAG, "ERROR FROM API : " + errors.getErrorText());

                                /*displaying load more failed*/
                                feedAdapter.getLoadMoreModule().loadMoreFail();
                            }
                        }
                    }
    
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
    
                        /*displaying error*/
                        timelineErrorLayout.setVisibility(View.VISIBLE);
                    }
                
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void showAdViewPopup(ImageView ad_media, String adMedia, int position) {

        timelineAdFullViewPopup adFullViewPopup= new timelineAdFullViewPopup(context);
        adFullViewPopup.isShowSaveButton(false);
        adFullViewPopup.setSingleSrcView(ad_media, adMedia);
        adFullViewPopup.setXPopupImageLoader(new XPopupImageLoader() {
            @Override
            public void loadImage(int position, @androidx.annotation.NonNull Object uri,
                                  @androidx.annotation.NonNull ImageView imageView) {

                ImageLoader imageLoader = Coil.imageLoader(context);
                ImageRequest imageRequest = new ImageRequest.Builder(context)
                        .data(uri)
                        .crossfade(true)
                        .transformations(new CircleCropTransformation())
                        .target(imageView)
                        .build();
                imageLoader.enqueue(imageRequest);
            }

            @Override
            public File getImageFile(@androidx.annotation.NonNull Context context,
                                     @androidx.annotation.NonNull Object uri) {
                return null;
            }
        });

        /*......*/
        Gson gson = new Gson();
        String toJson = gson.toJson(feedAdapter.getData().get(position).getUserData());
        userData user_data = gson.fromJson(toJson, userData.class);

        adFullName = user_data.getName();
        adLocation = feedAdapter.getData().get(position).getLocation();
        adDescription = feedAdapter.getData().get(position).getDescription();
        adHeadline = feedAdapter.getData().get(position).getHeadline();
    }

    private void showViewPopup(ImageView post_image, String postFile, int position) {

        timelineImageViewPopup imageViewPopup = new timelineImageViewPopup(context);
        imageViewPopup.setSingleSrcView(post_image, postFile);
        imageViewPopup.isShowSaveButton(false);
        imageViewPopup.setXPopupImageLoader(new XPopupImageLoader() {
            @Override
            public void loadImage(int position, @androidx.annotation.NonNull Object uri,
                                  @androidx.annotation.NonNull ImageView imageView) {

                ImageLoader imageLoader = Coil.imageLoader(context);
                ImageRequest imageRequest = new ImageRequest.Builder(context)
                        .data(uri)
                        .crossfade(true)
                        .target(imageView)
                        .build();
                imageLoader.enqueue(imageRequest);
            }

            @Override
            public File getImageFile(@androidx.annotation.NonNull Context context,
                                     @androidx.annotation.NonNull Object uri) {
                return null;
            }
        });

        /*.......*/
        fullName = feedAdapter.getData().get(position).getPublisherInfo().getName();
        timeAgo = feedAdapter.getData().get(position).getPostTime();
        noLikes = feedAdapter.getData().get(position).getPostLikes();
        noComments = feedAdapter.getData().get(position).getPostComments();
        isLiked = feedAdapter.getData().get(position).isLiked();

        /*show popup*/
        new XPopup.Builder(context).asCustom(imageViewPopup).show();
    }

    /*liking a post*/
    private void likePost(String postId, int position, MaterialButton likeBtn) {

        /*update button*/
        if(feedAdapter.getData().get(position).isLiked()) {
            feedAdapter.getData().get(position).setLiked(false);
            likeBtn.setIconResource(R.drawable.ic_like);
        } else {
            feedAdapter.getData().get(position).setLiked(true);
            likeBtn.setIconResource(R.drawable.ic_liked);
        }

        /*notify adapter*/
//        feedAdapter.notifyItemChanged(position);

        like_dislikeObservable = rxJavaQueries.like_dislikePost(accessToken,
                BuildConfig.server_key, postId, "like");
        like_dislikeObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<like_dislike>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
                
                    @Override
                    public void onNext (@NonNull like_dislike like_dislike) {
                        if (like_dislike.getApiStatus() == 200) {
                            Log.d(TAG, "onNext: liked/disliked");
                        }
                        else {
                            apiErrors apiErrors = like_dislike.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        }
                    }
                
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    
                        /*TODO repeat if failed*/
                        likePost(postId, position, likeBtn);
                    }
                
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
    
    /*get all post first round*/
    private List<post> getAll (postList postList) {
        timelinePosts=  postList.getPostList();
        return timelinePosts != null ? postList.getPostList(): null;
    }
    
    /*share post direct to timeline*/
    public void shareOnTimeline (String postId) {
        
        snack = new KSnack((FragmentActivity) context);
        snack.setMessage("Sharing to your timeline ...");
        snack.show();
    
        shareResponseObservable =
                rxJavaQueries.sharePostInTimeline(accessToken, BuildConfig.server_key,
                        share_post_on_timeline,
                        postId, userId, "");
        shareResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<shareResponse>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
                
                    @Override
                    public void onNext (@NonNull shareResponse shareResponse) {
                        if (shareResponse.getApiStatus() == 200) {
                            Log.d(TAG, "onNext: SHARED");
                        
                            snack.setBackColor(R.color.green);
                            snack.setMessage("Post shared");
                            snack.setDuration(3500);
                        }
                        else {
                            apiErrors apiErrors = shareResponse.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        
                            snack.setMessage("Error occurred while sharing");
                            snack.setBackColor(R.color.indian_red);
                            snack.setDuration(5000);
                            snack.setAction("Try again", view -> {
                                snack.dismiss();
                                shareOnTimeline(postId);
                            });
                        }
                    }
                
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    
                        snack.setMessage("Error occurred while sharing");
                        snack.setBackColor(R.color.indian_red);
                        snack.setDuration(5000);
                        snack.setAction("Try again", view -> {
                            snack.dismiss();
                            shareOnTimeline(postId);
                        });
                    }
                
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
    
    /*share on other apps*/
    public void shareOnOtherApps (String postUrl, String fullName) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, fullName);
        intent.putExtra(Intent.EXTRA_TEXT, postUrl);
        context.startActivity(Intent.createChooser(intent, "choose " +
                "one"));
    }
    
    /*AD info*/
    public static void getADInfo (TextView full_name, TextView location, TextView description,
                                  TextView headline) {
        /*setting up*/
        full_name.setText(adFullName);
        location.setText(adLocation);
        description.setText(Html.fromHtml(adDescription));
        headline.setText(adHeadline);
    }

    /*post image info*/
    public static void getInfo (TextView full_name, TextView time_ago, TextView no_likes,
                                TextView no_comments, MaterialButton like, MaterialButton comment) {
    
        /*setting up*/
        full_name.setText(fullName);
        time_ago.setText(timeAgo);
        no_likes.setText(noLikes + " likes");
        no_comments.setText(noComments + " comments");
    
        /*setting like btn*/
        if (isLiked) {
            like.setIconResource(R.drawable.ic_liked);
            like.setText("Liked");
        }
    }

    /*copy post link*/
    private void copyLink (String postLink, Context context) {
        ClipboardManager clipboard =
                (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("post link", postLink);
        clipboard.setPrimaryClip(clip);

        Log.d(TAG, "copyLink: " + clip.toString());
        Log.d(TAG, "copyLink: " + postLink);

        Toast.makeText(context, "Link copied", Toast.LENGTH_SHORT).show();
    }
    
    /*report post*/
    public void reportPOST (String postId) {
        postActionObservable = rxJavaQueries.postAction(accessToken,
                BuildConfig.server_key, postId, "report");
        
        postActionObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<postAction>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
    
                    @Override
                    public void onNext (@NonNull postAction postAction) {
                        if (postAction.getApiStatus() == 200) {
                            /*TODO update UI on report*/
                            Toast.makeText(context, "Post reported",
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            apiErrors errors = postAction.getErrors();
                            Log.d(TAG, "onNext: " + errors.getErrorId());
    
                            Toast.makeText(context, "Failed to report post",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
    
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        Toast.makeText(context, "Failed to report post",
                                Toast.LENGTH_LONG).show();
                    }
    
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
    
    /*save post*/
    private void savePOST (String postId) {
    
        postActionObservable = rxJavaQueries.postAction(accessToken,
                BuildConfig.server_key, postId, "save");
    
        postActionObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<postAction>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
                
                    @Override
                    public void onNext (@NonNull postAction postAction) {
                        if (postAction.getApiStatus() == 200) {
                            Toast.makeText(context, "Post saved",
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            apiErrors errors = postAction.getErrors();
                            Log.d(TAG, "onNext: " + errors.getErrorId());
                        
                            Toast.makeText(context, "Failed to save post",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        Toast.makeText(context, "Failed to save post",
                                Toast.LENGTH_LONG).show();
                    }
                
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    /*share post*/
    private void sharePOST (post post) {
        new Handler().postDelayed(() -> {
            new XPopup.Builder(context).asCustom(new sharePopup(context, post.getPostId(), post.getUrl(),
                    post.getPublisherInfo().getName()).show());
        }, 500);
    }
    
    /*hide post*/
    public static void hidePOST(timelineFeedAdapter feedAdapter, int position) {
        feedAdapter.getData().remove(position);
        feedAdapter.notifyItemRemoved(position);
    }

    /*create a new post*/
    public void createNewPost(String postText, ProgressBar progressBar, String post_color) {
        createPostResponse = rxJavaQueries.createPost(accessToken,
                BuildConfig.server_key, userId, postText,post_color, null, "");

        createPostResponse.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<simpleResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull simpleResponse simpleResponse) {
                        if (simpleResponse.getApiStatus() == 200) {
                            Log.d(TAG, "onNext: " + simpleResponse.getApiStatus());

                            progressBar.setProgress(100);

                            Toast toast = Toast.makeText(context, "Post created successful ... ", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                            toast.show();
                        }
                        else {
                            apiErrors errors  = simpleResponse.getErrors();
                            Log.d(TAG, "ERROR: " + errors.getErrorId());

                            progressBar.setProgress(0);
                            Toast toast = Toast.makeText(context, "Failed to create post ... ", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                            toast.show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());

                        progressBar.setProgress(0);
                        Toast toast = Toast.makeText(context, "Failed to create post ... ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                        toast.show();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
