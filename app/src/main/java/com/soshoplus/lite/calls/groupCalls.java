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
import android.view.View;
import android.widget.ImageView;
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
import com.lxj.xpopup.animator.BlurAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.onurkagan.ksnack_lib.KSnack.KSnack;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.R;
import com.soshoplus.lite.adapters.timelineFeedAdapter;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.models.groups.group;
import com.soshoplus.lite.models.postAction;
import com.soshoplus.lite.models.postsfeed.post;
import com.soshoplus.lite.models.postsfeed.postList;
import com.soshoplus.lite.models.postsfeed.reactions.like_dislike;
import com.soshoplus.lite.models.postsfeed.sharepost.shareResponse;
import com.soshoplus.lite.models.userprofile.userData;
import com.soshoplus.lite.ui.hashTagsPosts;
import com.soshoplus.lite.ui.user_profile.userProfile;
import com.soshoplus.lite.utils.queries;
import com.soshoplus.lite.utils.retrofitInstance;
import com.soshoplus.lite.utils.xpopup.adGroupPopup;
import com.soshoplus.lite.utils.xpopup.imageGroupPopup;
import com.soshoplus.lite.utils.xpopup.previewProfilePopup;
import com.soshoplus.lite.utils.xpopup.sharePopup;

import java.io.File;
import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.transform.CircleCropTransformation;
import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class groupCalls {

    private final static String TAG = "Group Calls";
    private final static String get_group_posts = "get_group_posts";
    private static String firstData = "0";
    private static int totalItems;
    private static String lastItemID;
    private static String share_post_on_timeline = "share_post_on_timeline";
    private static String fullName, timeAgo, noLikes, noComments;
    private static boolean isLiked;
    private static String adFullName, adLocation, adDescription, adHeadline;
    private static String[] post_option = {"Report post", "Copy link", "Share post", "Save post", "Hide post"};
    private KSnack kSnack;
    private Context context;
    private Observable<group> groupInfoObservable;
    private Observable<postList> postListObserve;
    private List<post> groupPosts;
    private timelineFeedAdapter feedAdapter;
    /*.......*/
    /*POST LIKE_DISLIKE*/
    private Observable<like_dislike> like_dislikeObservable;
    /*SHARE ON TIMELINE*/
    private Observable<shareResponse> shareResponseObservable;
    /*......*/
    private Observable<postAction> postActionObservable;

    private static String userId, timezone, accessToken;
    private queries rxJavaQueries;

    public groupCalls(Context context) {
        this.context = context;
        kSnack = new KSnack((FragmentActivity) context);

        userId = SecurePreferences.getStringValue(context, "userId", "0");
        timezone = SecurePreferences.getStringValue(context, "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(context, "accessToken", "0");
        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);
    }

    /*AD info*/
    public static void getADInfo(TextView full_name, TextView location, TextView description,
                                 TextView headline) {
        /*setting up*/
        full_name.setText(adFullName);
        location.setText(adLocation);
        description.setText(Html.fromHtml(adDescription));
        headline.setText(adHeadline);
    }

    /*post image info*/
    public static void getInfo(TextView full_name, TextView time_ago, TextView no_likes,
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

    /*hide post*/
    public static void hidePOST(timelineFeedAdapter feedAdapter, int position) {
        feedAdapter.getData().remove(position);
        feedAdapter.notifyItemRemoved(position);
    }

    public void getGroupInfo(ImageView groupProfilePic, ImageView groupCover,
                             TextView noMembers, TextView groupPrivacy, TextView groupCategory, String group_id,
                             String no_members, MaterialButton joinBtn) {

        groupInfoObservable = rxJavaQueries.getGroupInfo(accessToken, BuildConfig.server_key, group_id);

        groupInfoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<group>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull group group) {
                        if (group.getApiStatus() == 200) {

                            ImageLoader imageLoader = Coil.imageLoader(context);
                            ImageRequest imageRequest = new ImageRequest.Builder(context)
                                    .data(group.getGroupInfo().getAvatar())
                                    .crossfade(true)
                                    .transformations(new CircleCropTransformation())
                                    .target(groupProfilePic)
                                    .build();
                            imageLoader.enqueue(imageRequest);

                            imageRequest = new ImageRequest.Builder(context)
                                    .data(group.getGroupInfo().getCover())
                                    .placeholder(R.color.light_grey)
                                    .crossfade(true)
                                    .target(groupCover)
                                    .build();
                            imageLoader.enqueue(imageRequest);

                            noMembers.setText(no_members + " Members");
                            groupCategory.setText(group.getGroupInfo().getCategory());

                            if (group.getGroupInfo().getPrivacy().equals("1")) {
                                groupPrivacy.setText("Public group");
                            } else {
                                groupPrivacy.setText("Private group");
                            }

                            if (!group.getGroupInfo().isIsJoined()) {
                                joinBtn.setVisibility(View.VISIBLE);
                            }
                        } else {
                            apiErrors errors = group.getErrors();
                            Log.d(TAG, "Error: " + errors.getErrorId());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    public void getGroupPosts(RecyclerView groupPostList, String group_id, SmartRefreshLayout refreshPostsLayout) {

        postListObserve = rxJavaQueries.getGroupPosts(accessToken, BuildConfig.server_key,
                group_id, get_group_posts, "5", firstData);

        postListObserve.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<postList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull postList postList) {
                        if (firstData.equals("0")) {
                            if (postList.getApiStatus() == 200) {
                                Log.d(TAG, "onNext: " + "LOAD FIRST DATA");

                                groupPostList.setLayoutManager(new LinearLayoutManager(context));

                                groupPosts = getPosts(postList);

                                if (groupPosts != null) {
                                    for (post firstPosts : groupPosts) {
                                        Log.d(TAG, "onNext: POSTS ID : " + firstPosts.getPostId());
                                    }

                                    /*last item ID*/
                                    totalItems = groupPosts.size();
                                    if (totalItems > 2) {
                                        lastItemID = groupPosts.get(totalItems - 2).getPostId();
                                        firstData = lastItemID;
                                        Log.d(TAG, "onNext: AFTER POST ID : " + lastItemID);
                                    }
                                }

                                /*refresh layout*/
                                if (refreshPostsLayout.isRefreshing()) {
                                    refreshPostsLayout.finishRefresh();
                                }

                                /*initialize adapter*/
                                feedAdapter = new timelineFeedAdapter(groupPosts);
                                feedAdapter.setAnimationEnable(false);

                                /*setting adapter*/
                                groupPostList.setAdapter(feedAdapter);

                                groupPostList.setVisibility(View.VISIBLE);

                                if (totalItems > 2) {
                                    /*setting loadmore module*/
                                    feedAdapter.getLoadMoreModule().setAutoLoadMore(true);
                                    feedAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
                                        /*load more posts*/
                                        getGroupPosts(groupPostList, group_id, refreshPostsLayout);
                                    });
                                }

                                /*on click post*/
                                feedAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                                    @Override
                                    public void onItemChildClick(@androidx.annotation.NonNull BaseQuickAdapter adapter,
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

                                                }, 500);
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
                            } else {
                                apiErrors errors = postList.getErrors();
                                Log.d(TAG, "ERROR FROM API : " + errors.getErrorId());

                                kSnack.setMessage("Failed to load posts");
                                kSnack.setDuration(3000);
                            }
                        } else {
                            /*load more*/
                            if (postList.getApiStatus() == 200) {
                                Log.d(TAG, "onNext: LOAD MORE DATA");

                                /*getting new posts*/
                                groupPosts = getPosts(postList);

                                if (groupPosts != null) {

                                    for (post firstPosts : groupPosts) {
                                        Log.d(TAG, "onNext: POSTS ID : " + firstPosts.getPostId());
                                    }

                                    if (feedAdapter == null) {
                                        /*refresh data*/
                                        firstData = "0";
                                        getGroupPosts(groupPostList, group_id, refreshPostsLayout);
                                    } else {
                                        feedAdapter.addData(groupPosts);
                                        /*last item ID*/
                                        totalItems = groupPosts.size();

                                        if (totalItems > 2) {
                                            lastItemID = groupPosts.get(totalItems - 2).getPostId();
                                            firstData = lastItemID;
                                            Log.d(TAG, "onNext: AFTER POST ID : " + lastItemID);

                                            feedAdapter.getLoadMoreModule().loadMoreComplete();
                                        } else {
                                            feedAdapter.getLoadMoreModule().loadMoreEnd();
                                        }


                                        Log.d(TAG, "ADAPTER ITEM COUNT : " + feedAdapter.getItemCount());
                                    }
                                }
                            } else {
                                apiErrors errors = postList.getErrors();
                                Log.d(TAG, "ERROR FROM API : " + errors.getErrorId());

                                /*displaying load more failed*/
                                feedAdapter.getLoadMoreModule().loadMoreFail();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());

                        kSnack.setMessage("Failed to load posts");
                        kSnack.setDuration(3000);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });

    }

    private List<post> getPosts(postList postList) {
        groupPosts = postList.getPostList();
        return groupPosts != null ? postList.getPostList() : null;
    }

    private void showAdViewPopup(ImageView ad_media, String adMedia, int position) {

        adGroupPopup adFullViewPopup = new adGroupPopup(context);
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

        imageGroupPopup imageViewPopup = new imageGroupPopup(context);
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
        new XPopup.Builder(context)
                .asCustom(imageViewPopup).show();
    }

    /*liking a post*/
    private void likePost(String postId, int position, MaterialButton likeBtn) {

        /*update button*/
        if (feedAdapter.getData().get(position).isLiked()) {
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
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull like_dislike like_dislike) {
                        if (like_dislike.getApiStatus() == 200) {
                            Log.d(TAG, "onNext: liked/disliked");
                        } else {
                            apiErrors apiErrors = like_dislike.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());

                        /*TODO repeat if failed*/
                        likePost(postId, position, likeBtn);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    /*copy post link*/
    private void copyLink(String postLink, Context context) {
        ClipboardManager clipboard =
                (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("post link", postLink);
        clipboard.setPrimaryClip(clip);

        Log.d(TAG, "copyLink: " + clip.toString());
        Log.d(TAG, "copyLink: " + postLink);

        Toast.makeText(context, "Link copied", Toast.LENGTH_SHORT).show();
    }

    /*report post*/
    public void reportPOST(String postId) {
        postActionObservable = rxJavaQueries.postAction(accessToken,
                BuildConfig.server_key, postId, "report");

        postActionObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<postAction>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull postAction postAction) {
                        if (postAction.getApiStatus() == 200) {
                            /*TODO update UI on report*/
                            Toast.makeText(context, "Post reported",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            apiErrors errors = postAction.getErrors();
                            Log.d(TAG, "onNext: " + errors.getErrorId());

                            Toast.makeText(context, "Failed to report post",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        Toast.makeText(context, "Failed to report post",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    /*save post*/
    private void savePOST(String postId) {

        postActionObservable = rxJavaQueries.postAction(accessToken,
                BuildConfig.server_key, postId, "save");

        postActionObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<postAction>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull postAction postAction) {
                        if (postAction.getApiStatus() == 200) {
                            Toast.makeText(context, "Post saved",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            apiErrors errors = postAction.getErrors();
                            Log.d(TAG, "onNext: " + errors.getErrorId());

                            Toast.makeText(context, "Failed to save post",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        Toast.makeText(context, "Failed to save post",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    /*share post*/
    private void sharePOST(post post) {
        new Handler().postDelayed(() -> {
            new XPopup.Builder(context).asCustom(new sharePopup(context, post.getPostId(), post.getUrl(),
                    post.getPublisherInfo().getName()).show());
        }, 500);
    }
}
