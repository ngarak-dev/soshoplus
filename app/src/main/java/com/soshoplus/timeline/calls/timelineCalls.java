/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.calls;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.onurkagan.ksnack_lib.KSnack.KSnack;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.soshoplus.timeline.BuildConfig;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.adapters.timelineFeedAdapter;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.postsfeed.post;
import com.soshoplus.timeline.models.postsfeed.postList;
import com.soshoplus.timeline.models.postsfeed.reactions.like_dislike;
import com.soshoplus.timeline.models.postsfeed.sharepost.shareResponse;
import com.soshoplus.timeline.models.userprofile.userData;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;
import com.soshoplus.timeline.utils.xpopup.previewProfilePopup;
import com.soshoplus.timeline.utils.xpopup.sharePopup;
import com.soshoplus.timeline.utils.xpopup.timelineAdFullViewPopup;
import com.soshoplus.timeline.utils.xpopup.timelineImageViewPopup;

import java.io.File;
import java.util.List;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
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
    
    /*TIMELINE FEED*/
    private final static String get_news_feed = "get_news_feed";
    private Observable<postList> postListObserve;
    private List<post> timelinePosts;
    private LinearLayoutManager linearLayoutManager;
    private static String afterPostId = "0";
    private timelineFeedAdapter feedAdapter;
    
    /*POST LIKE_DISLIKE*/
    private Observable<like_dislike> like_dislikeObservable;
    
    /*SHARE ON TIMELINE*/
    private Observable<shareResponse> shareResponseObservable;
    private static String share_post_on_timeline = "share_post_on_timeline";
    
    /*.......*/
    private KSnack snack;
    /*........*/
    private static int totalItems, lastItemPosition;
    private static String lastItemID;
    
    /*........*/
    private static String fullName, timeAgo, noLikes, noComments;
    private static boolean isLiked;
    /*......*/
    private static String adFullName, adLocation, adDescription, adHeadline;
    
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
    
    public void getTimelineFeed (RecyclerView timelinePostsList, SmartRefreshLayout timelineSmartRefresh,
                                 ProgressBar progressBarTimeline, RelativeLayout timelineErrorLayout, MaterialButton tryAgain) {
    
        linearLayoutManager = new LinearLayoutManager(context);
        timelinePostsList.setLayoutManager(linearLayoutManager);
        timelinePostsList.setHasFixedSize(true);
    
        /*load posts*/
        Log.d(TAG, "LOADING : " + afterPostId);
        loadPosts(timelinePostsList, afterPostId, timelineErrorLayout, tryAgain);
        
        /*hide progress*/
        new Handler().postDelayed(() -> {
            progressBarTimeline.setVisibility(View.GONE);
        }, 500);

        /*onLoad more listener*/
        timelineSmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore (@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                /*load more posts*/
                loadPosts(timelinePostsList, lastItemID, timelineErrorLayout, tryAgain);
                /*finish load more*/
                new Handler().postDelayed(refreshLayout::finishLoadMore, 500);
            }
        });
        /*pull to refresh*/
        timelineSmartRefresh.setOnRefreshListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore (@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                /**/
            }
    
            @Override
            public void onRefresh (@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                /*afterpost == 0*/
                afterPostId = "0";
                /*.........*/
                timelineErrorLayout.setVisibility(View.GONE);
                timelinePostsList.setVisibility(View.GONE);
                progressBarTimeline.setVisibility(View.VISIBLE);
                
                /*refresh data*/
                loadPosts(timelinePostsList, afterPostId, timelineErrorLayout,
                        tryAgain);
                /*finish refresh*/
                /*hide progress*/
                new Handler().postDelayed(() -> {
                    progressBarTimeline.setVisibility(View.GONE);
                    refreshLayout.finishRefresh();
                }, 500);
                
//                new Handler().postDelayed(refreshLayout::finishRefresh, 500);
            }
        });
        
        tryAgain.setOnClickListener(view -> {
            /*afterpost  == 0*/
            afterPostId = "0";
            
            /*hide error layout*
            disable refreshing*/
            timelineErrorLayout.setVisibility(View.GONE);
            timelineSmartRefresh.setEnableRefresh(false);
            timelineSmartRefresh.setEnableLoadMore(false);
            /*.........*/
            timelinePostsList.setVisibility(View.GONE);
            progressBarTimeline.setVisibility(View.VISIBLE);
    
            /*refresh data*/
            loadPosts(timelinePostsList, "0", timelineErrorLayout, tryAgain);
            /*finish refresh*/
            /*hide progress*/
            new Handler().postDelayed(() -> {
                progressBarTimeline.setVisibility(View.GONE);
            }, 500);
    
            /*enable smart refresh*/
            timelineSmartRefresh.setEnableRefresh(true);
            timelineSmartRefresh.setEnableLoadMore(true);
        });
    }
    
    private void loadPosts (RecyclerView timelinePostsList, String afterPostId,
                            RelativeLayout timelineErrorLayout, MaterialButton tryAgain) {
    
        postListObserve =
                rxJavaQueries.getTimelinePosts(accessToken,
                BuildConfig.server_key, get_news_feed, "10", afterPostId);
    
        postListObserve.observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<postList>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
                
                    @Override
                    public void onNext (@NonNull postList postList) {
                        if (afterPostId.equals("0")) {
        
                            if(postList.getApiStatus() == 200) {
                                Log.d(TAG, "onNext: LOAD FIRST DATA");
            
                                timelinePosts = getAll(postList);
            
                                if (timelinePosts != null) {
                                    for (post firstPosts : timelinePosts) {
                                        Log.d(TAG, "onNext: FIRST POSTS ID : " + firstPosts.getPostId());
                                    }
    
                                    /*last item ID*/
                                    lastItemID = timelinePosts.get(7).getPostId();
                                    Log.d(TAG, "LAST ITEM ID : " + lastItemID);
                                }
                                
                                /*initialize adapter*/
                                feedAdapter = new timelineFeedAdapter(timelinePosts,
                                                new timelineFeedAdapter.onClickListener() {
                                                    @Override
                                                    public void onVideoClickPlay (String postFile) {
                                    
                                                    }
                                
                                                    @Override
                                                    public void onAudioClickPlay (String postFile, Chip play, Chip pause) {
                                    
                                                    }
                                
                                                    @Override
                                                    public void onLikePost (String postId, MaterialButton likes, TextView no_likes) {
                                                        likePost(postId, likes,
                                                                no_likes);
                                                    }
                                
                                                    @Override
                                                    public void onShareClicked (String post_Id, String url, String name) {
                                                        /*passing string*/
                                                        new XPopup.Builder(context).asCustom(new sharePopup(context, post_Id, url, name)).show();
                                                    }
                                
                                                    @Override
                                                    public void onProfilePicClicked (String userId) {
                                                        new XPopup.Builder(context).asCustom(new previewProfilePopup(context, userId)).show();
                                                    }
    
                                                    @Override
                                                    public void viewFullImage (Context context, post post, ImageView post_image) {
                                                        /*initializing popup*/
                                                        timelineImageViewPopup imageViewPopup =
                                                                new timelineImageViewPopup(context);
                                                        /*setting up*/
                                                        imageViewPopup.setSingleSrcView(post_image, post.getPostFileFull());
                                                        imageViewPopup.isShowSaveButton(false);
                                                        imageViewPopup.setXPopupImageLoader(new XPopupImageLoader() {
                                                            @Override
                                                            public void loadImage (int position, @androidx.annotation.NonNull Object uri,
                                                                                   @androidx.annotation.NonNull ImageView imageView) {
                                                                Glide.with(imageView).load(uri).into(imageView);
                                                            }
        
                                                            @Override
                                                            public File getImageFile (@androidx.annotation.NonNull Context context,
                                                                                      @androidx.annotation.NonNull Object uri) {
                                                                return null;
                                                            }
                                                        });
                                                        /*show popup*/
                                                        new XPopup.Builder(context).asCustom(imageViewPopup).show();
    
                                                        /*.......*/
                                                        fullName =
                                                                post.getPublisherInfo().getName();
                                                        timeAgo = post.getPostTime();
                                                        noLikes = post.getPostLikes();
                                                        noComments = post.getPostComments();
                                                        isLiked = post.isLiked();
                                                    }
    
                                                    @Override
                                                    public void viewFullADImage (Context context, post post, ImageView ad_media) {
                                                        /*initializing popup*/
                                                        timelineAdFullViewPopup imageViewPopup =
                                                                new timelineAdFullViewPopup(context);
                                                        /*setting up*/
                                                        imageViewPopup.setSingleSrcView(ad_media, post.getAdMedia());
                                                        imageViewPopup.isShowSaveButton(false);
                                                        imageViewPopup.setXPopupImageLoader(new XPopupImageLoader() {
                                                            @Override
                                                            public void loadImage (int position, @androidx.annotation.NonNull Object uri,
                                                                                   @androidx.annotation.NonNull ImageView imageView) {
                                                                Glide.with(imageView).load(uri).into(imageView);
                                                            }
        
                                                            @Override
                                                            public File getImageFile (@androidx.annotation.NonNull Context context,
                                                                                      @androidx.annotation.NonNull Object uri) {
                                                                return null;
                                                            }
                                                        });
                                                        /*show popup*/
                                                        new XPopup.Builder(context).asCustom(imageViewPopup).show();
    
                                                        /*......*/
                                                        /*Converting Object to json data*/
                                                        Gson gson = new Gson();
                                                        String toJson = gson.toJson(post.getUserData());
                                                        /*getting data from json string using pojo class*/
                                                        userData user_data = gson.fromJson(toJson, userData.class);
    
                                                        adFullName = user_data.getName();
                                                        adLocation = post.getLocation();
                                                        adDescription = post.getDescription();
                                                        adHeadline = post.getHeadline();
                                                    }
                                                });
            
                                /*setting adapter*/
                                timelinePostsList.setAdapter(feedAdapter);
                                
                                /*.........*/
                                timelinePostsList.setVisibility(View.VISIBLE);
                            }
                            else {
                                apiErrors errors = postList.getErrors();
                                Log.d(TAG, "ERROR FROM API : " + errors.getErrorText());
            
                                /*displaying error*/
                                timelineErrorLayout.setVisibility(View.VISIBLE);
                            }
                        }
                        else {
                            if (postList.getApiStatus() == 200) {
                                /*getting new posts*/
                                List<post> tobeAdded = getAll(postList);
                                Log.d(TAG, "onNext: LOAD MORE DATA");
                                
                                /*........*/
                                feedAdapter.updatePostsList(tobeAdded);
                                feedAdapter.notifyDataSetChanged();
    
                                /*last item ID*/
                                lastItemID = timelinePosts.get(7).getPostId();
                                Log.d(TAG, "LAST ITEM ID : " + lastItemID);
                                
                                Log.d(TAG, "ADAPTER ITEM COUNT : " + feedAdapter.getItemCount());
                            }
                            else {
                                apiErrors errors = postList.getErrors();
                                Log.d(TAG, "ERROR FROM API : " + errors.getErrorText());
            
                                /*displaying a snackbar*/
                                snack = new KSnack((FragmentActivity) context);
                                snack.setMessage("Oops !\nSomething went " +
                                        "wrong\nPlease check your internet " +
                                        "connection");
                                snack.setAction("DISMISS", view -> {
                                    snack.dismiss();
                                });
                                snack.setAction("TRY AGAIN", view -> {
                                    snack.dismiss();
                                    loadPosts(timelinePostsList, afterPostId, timelineErrorLayout, tryAgain);
                                });
                                snack.show();
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
    
    /*liking a post*/
    private void likePost (String postId, MaterialButton likes, TextView no_likes) {
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
                            no_likes.setText(like_dislike.getLikesData().getCount());
                        
                            if (like_dislike.getAction().equals("liked")) {
                                likes.setIconResource(R.drawable.ic_liked);
                                likes.setIconTintResource(R.color.colorPrimary);
                                likes.setText("Liked");
                                likes.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                            }
                            else {
                                likes.setIconResource(R.drawable.ic_like);
                                likes.setIconTintResource(R.color.black);
                                likes.setText("Like");
                                likes.setTextColor(context.getResources().getColor(R.color.black));
                            }
                        }
                        else {
                            apiErrors apiErrors = like_dislike.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                        }
                    }
                
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    
                        /*TODO repeate if failed*/
                        likePost(postId, likes, no_likes);
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
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                        
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
}
