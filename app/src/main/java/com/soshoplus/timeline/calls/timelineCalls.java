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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnUpFetchListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.onurkagan.ksnack_lib.KSnack.KSnack;
import com.soshoplus.timeline.BuildConfig;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.adapters.timelineFeedAdapter;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.postAction;
import com.soshoplus.timeline.models.postsfeed.post;
import com.soshoplus.timeline.models.postsfeed.postList;
import com.soshoplus.timeline.models.postsfeed.reactions.like_dislike;
import com.soshoplus.timeline.models.postsfeed.sharepost.shareResponse;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;

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
    private static String firstData = "0";
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
    
    /*......*/
    private Observable<postAction> postActionObservable;
    
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
    
    public void getTimelineFeed (RecyclerView timelinePostsList,
                                 ProgressBar progressBarTimeline, RelativeLayout timelineErrorLayout, MaterialButton tryAgain) {
    
        /*load posts*/
        Log.d(TAG, "LOADING : " + firstData);
        loadPosts(timelinePostsList, timelineErrorLayout,
                tryAgain, progressBarTimeline);
        
        tryAgain.setOnClickListener(view -> {
            /*hide error layout*
            disable refreshing*/
            timelineErrorLayout.setVisibility(View.GONE);
            
            /*.........*/
            timelinePostsList.setVisibility(View.GONE);
            progressBarTimeline.setVisibility(View.VISIBLE);
    
            /*refresh data*/
            loadPosts(timelinePostsList, timelineErrorLayout, tryAgain, progressBarTimeline);
            /*finish refresh*/
            /*hide progress*/
            new Handler().postDelayed(() -> {
                progressBarTimeline.setVisibility(View.GONE);
            }, 500);
        });
    }
    
    private void loadPosts (RecyclerView timelinePostsList,
                            RelativeLayout timelineErrorLayout, MaterialButton tryAgain, ProgressBar progressBarTimeline) {
    
        postListObserve =
                rxJavaQueries.getTimelinePosts(accessToken,
                BuildConfig.server_key, get_news_feed, "10", firstData);
    
        postListObserve.observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<postList>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
                
                    @Override
                    public void onNext (@NonNull postList postList) {
                        
                        if (firstData.equals("0")) {
        
                            if(postList.getApiStatus() == 200) {
                                Log.d(TAG, "onNext: LOAD FIRST DATA");
    
                                linearLayoutManager = new LinearLayoutManager(context);
                                timelinePostsList.setLayoutManager(linearLayoutManager);
                                timelinePostsList.setHasFixedSize(true);
            
                                timelinePosts = getAll(postList);
            
                                if (timelinePosts != null) {
                                    for (post firstPosts : timelinePosts) {
                                        Log.d(TAG, "onNext: POSTS ID : " + firstPosts.getPostId());
                                    }
    
                                    /*last item ID*/
                                    lastItemID = timelinePosts.get(8).getPostId();
                                    firstData = lastItemID;
                                    Log.d(TAG, "onNext: AFTER POST ID : " + lastItemID);
                                }
    
                                /*hide progress*/
                                new Handler().postDelayed(() -> progressBarTimeline.setVisibility(View.GONE), 500);
                                
                                /*initialize adapter*/
                                feedAdapter = new timelineFeedAdapter(timelinePosts);
                                feedAdapter.setAnimationEnable(true);
            
                                /*setting adapter*/
                                timelinePostsList.setAdapter(feedAdapter);
                                
                                /*.........*/
                                timelinePostsList.setVisibility(View.VISIBLE);
    
                                /*setting loadmore module*/
                                feedAdapter.getLoadMoreModule().setAutoLoadMore(true);
                                feedAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
                                    /*load more posts*/
                                    loadPosts(timelinePostsList, timelineErrorLayout, tryAgain, progressBarTimeline);
                                });
                                
                                feedAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                                    @Override
                                    public void onItemChildClick (@androidx.annotation.NonNull BaseQuickAdapter adapter,
                                                                  @androidx.annotation.NonNull View view, int position) {
                                        
                                        TextView no_likes = view.findViewById(R.id.no_likes);
                                        Chip likes = view.findViewById(R.id.like_btn);
                                        
                                        if (view.getId() == R.id.like_btn) {
                                            likePost(timelinePosts.get(position).getPostId(), likes, no_likes, position);
                                        }
                                    }
                                });
                                
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
                                Log.d(TAG, "onNext: LOAD MORE DATA");
                                
                                /*getting new posts*/
                                List<post> tobeAdded = getAll(postList);
    
                                if (tobeAdded != null) {
                                    
                                    for (post firstPosts : tobeAdded) {
                                        Log.d(TAG, "onNext: POSTS ID : " + firstPosts.getPostId());
                                    }
                                    
                                    feedAdapter.addData(tobeAdded);
    
                                    /*last item ID*/
                                    lastItemID = timelinePosts.get(8).getPostId();
                                    firstData = lastItemID;
                                    Log.d(TAG, "onNext: AFTER POST ID : " + lastItemID);
                                }
                                
                                feedAdapter.getLoadMoreModule().loadMoreComplete();
                                
                                Log.d(TAG, "ADAPTER ITEM COUNT : " + feedAdapter.getItemCount());
                            }
                            else {
                                apiErrors errors = postList.getErrors();
                                Log.d(TAG, "ERROR FROM API : " + errors.getErrorText());
                                
                                /*displaying load more failed*/
                                feedAdapter.getLoadMoreModule().loadMoreFail();
                            }
                        }
                    }
    
                    private void OnUpRefresh () {
                        
                        feedAdapter.getUpFetchModule().setUpFetchEnable(true);
                        feedAdapter.getUpFetchModule().setOnUpFetchListener(new OnUpFetchListener() {
                            @Override
                            public void onUpFetch () {
                                firstData = "0";
                                /*.........*/
                                timelineErrorLayout.setVisibility(View.GONE);
                                timelinePostsList.setVisibility(View.GONE);
                                progressBarTimeline.setVisibility(View.VISIBLE);
            
                                /*refresh data*/
                                loadPosts(timelinePostsList, timelineErrorLayout,
                                        tryAgain, progressBarTimeline);
                                /*finish refresh*/
                                /*hide progress*/
                                new Handler().postDelayed(() -> {
                                    progressBarTimeline.setVisibility(View.GONE);
                                    feedAdapter.getUpFetchModule().setUpFetching(false);
                                }, 500);
                            }
                        });
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
    private void likePost (String postId, Chip likes, TextView no_likes, int position) {
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
                            
                            if(timelinePosts.get(position).isLiked()) {
                                timelinePosts.get(position).setLiked(false);
                            } else {
                                timelinePosts.get(position).setLiked(true);
                            }
                            
//                            no_likes.setText(like_dislike.getLikesData().getCount());
                        
//                            if (like_dislike.getAction().equals("liked")) {
//                                likes.setChipIconResource(R.drawable.ic_liked);
//                                likes.setChipIconTintResource(R.color.colorPrimary);
//                                likes.setTextColor(context.getResources().getColor(R.color.colorPrimary));
//                            }
//                            else {
//                                likes.setChipIconResource(R.drawable.ic_like);
//                                likes.setChipIconTintResource(R.color.black);
//                                likes.setTextColor(context.getResources().getColor(R.color.black));
//                            }
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
                        likePost(postId, likes, no_likes, position);
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
                            Log.d(TAG, "onNext: " + errors.getErrorText());
    
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
    public void savePOST (String postId) {
    
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
                            /*TODO update UI on report*/
                            Toast.makeText(context, "Post saved",
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            apiErrors errors = postAction.getErrors();
                            Log.d(TAG, "onNext: " + errors.getErrorText());
                        
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
    
    /*hide post*/
    public static void hidePOST (String postId, int position) {
    
    }
}
