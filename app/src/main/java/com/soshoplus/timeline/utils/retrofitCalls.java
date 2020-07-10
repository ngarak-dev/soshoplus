/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.lxj.xpopup.XPopup;
import com.onurkagan.ksnack_lib.KSnack.KSnack;
import com.soshoplus.timeline.BuildConfig;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.adapters.friendsFollowersAdapter;
import com.soshoplus.timeline.adapters.friendsFollowingAdapter;
import com.soshoplus.timeline.adapters.joinedGroupsAdapter;
import com.soshoplus.timeline.adapters.suggestedFriendsAdapter;
import com.soshoplus.timeline.adapters.suggestedGroupsAdapter;
import com.soshoplus.timeline.adapters.timelineFeedAdapter;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.follow_unfollow;
import com.soshoplus.timeline.models.friends.followers;
import com.soshoplus.timeline.models.friends.following;
import com.soshoplus.timeline.models.friends.friends;
import com.soshoplus.timeline.models.friends.suggested.suggestedInfo;
import com.soshoplus.timeline.models.friends.suggested.suggestedList;
import com.soshoplus.timeline.models.groups.group;
import com.soshoplus.timeline.models.groups.groupInfo;
import com.soshoplus.timeline.models.groups.groupList;
import com.soshoplus.timeline.models.groups.join.join_unjoin;
import com.soshoplus.timeline.models.postsfeed.post;
import com.soshoplus.timeline.models.postsfeed.postList;
import com.soshoplus.timeline.models.postsfeed.reactions.like_dislike;
import com.soshoplus.timeline.models.postsfeed.sharepost.shareResponse;
import com.soshoplus.timeline.models.userprofile.userInfo;
import com.soshoplus.timeline.ui.groups.viewGroup;
import com.soshoplus.timeline.ui.user_profile.userProfile;
import com.soshoplus.timeline.utils.glide.glideImageLoader;
import com.soshoplus.timeline.utils.xpopup.previewProfilePopup;
import com.soshoplus.timeline.utils.xpopup.sharePopup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DefaultObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;


public class retrofitCalls {
    
    private Context context;
    private queries rxJavaQueries;
    private String accessToken, userId, timezone;
    private static String TAG = "Calls class";
    public static String serverKey = BuildConfig.server_key;
    private static String get_news_feed = "get_news_feed";
    private static String fetch_profile = "user_data,family,liked_pages,joined_groups";
    private static String fetch_recommended = "groups";
    private static String joined_groups = "joined_groups";
    private static String friends_followers = "followers";
    private static String friends_following = "following";
    private static String suggested_friends = "users";
    private static String share_post_on_timeline = "share_post_on_timeline";
    
    /*ADAPTERS*/
    private suggestedGroupsAdapter suggested_groups_adapter;

    /*CALLS*/
    private Observable<userInfo> userInfoObservable;
    private Observable<group> groupObservable;
    private Observable<groupList> groupListObservable;
    private Observable<friends> friendsObservable;
    private Observable<suggestedList> suggestedListObservable;

    /*GROUPS*/
    private List<groupInfo> groupInfoList = null;
    /*FRIENDS*/
    private List<followers> followersList = null;
    private List<following> followingList = null;
    private List<suggestedInfo> suggestedInfoList = null;

    /*TODO Check this later*/
    private static String group_id;

    /*TIMELINE*/
    private Observable<postList> postListObserve;
    private Call<postList> postListCall;
    private List<post> timelinePosts = null;
    private LinearLayoutManager linearLayoutManager;
    private String afterPostId = "0";
    private timelineFeedAdapter feedAdapter;
    
    /*POST LIKE_DISLIKE*/
    private Observable<like_dislike> like_dislikeObservable;
    
    /*SHARE POST ON OTHER APPS*/
    private static String postId, postUrl, postAuthor;
    
    /*SHARE ON TIMELINE*/
    private Observable<shareResponse> shareResponseObservable;
    private KSnack snack;
    
    /*PREVIEW PROFILE*/
    private static String previewUserId, followPrivacy;
    private Observable<follow_unfollow> followUnfollowObservable;
    
    /*JOIN GROUP*/
    private Observable<join_unjoin> joinUnjoinObservable;
    
    /*GLIDE OPTIONS*/
    RequestOptions options = new RequestOptions()
            .format(DecodeFormat.PREFER_RGB_565)
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_placeholder)
            .priority(Priority.LOW);
    
    public retrofitCalls (Context context){
        this.context = context;
        
        userId = SecurePreferences.getStringValue(context, "userId", "0");
        timezone = SecurePreferences.getStringValue(context, "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(context, "accessToken"
                , "0");
    
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);
    }
    
    /*get profile on main activity*/
    public void getProfile (ShapeableImageView profile_pic, TextView full_name, TextView username) {
        userInfoObservable = rxJavaQueries.getUserData(accessToken, serverKey,
                fetch_profile, userId);
        
        userInfoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<userInfo>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
    
                    @Override
                    public void onNext (@NonNull userInfo userInfo) {
                        if(userInfo.getApiStatus() == 200) {
                            full_name.setText(userInfo.getUserData().getName());
                            username.setText("@" + userInfo.getUserData().getUsername());
                            //profile pic
                            profile_pic.setShapeAppearanceModel(profile_pic
                                    .getShapeAppearanceModel()
                                    .toBuilder()
                                    .setAllCorners(CornerFamily.ROUNDED, 20)
                                    .build());
                            Glide.with(context).load(userInfo.getUserData().getAvatar()).placeholder(R.drawable.ic_image_placeholder).thumbnail(0.5f).into(profile_pic);
                        }
                        else {
                            profile_pic.setImageResource(R.drawable.ic_image_placeholder);
                            apiErrors apiErrors =userInfo.getErrors();
                            Log.d(TAG, "main activity profile: " + apiErrors.getErrorId());
                            Log.d(TAG, "main activity profile: " + apiErrors.getErrorText());
                        }
                    }
    
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
    
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    /*get recommended groups*/
    public void getRecommends (RecyclerView suggestedGroupsList,
                               ImageView allSetUpImg, TextView allSetUpText
            , ProgressBar progressBarSuggested) {
        groupListObservable = rxJavaQueries.getRecommended(accessToken,
                serverKey, fetch_recommended, "5");
        groupListObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<groupList>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
    
                    @Override
                    public void onNext (@NonNull groupList groupList) {
                        if (groupList.getApiStatus() == 200) {
    
                            /*initializing list*/
                            groupInfoList = groupList.getInfo();
    
                            /*initializing adapter*/
                            suggested_groups_adapter =
                                    new suggestedGroupsAdapter(context,
                                            groupInfoList, new suggestedGroupsAdapter.onGroupClickListener() {
                                        @Override
                                        public void onGroupClick (groupInfo groupInfo) {
                                            Log.d(TAG, "onGroupClick: " + groupInfo.getGroupName());
                    
                                            /*TODO implement group view or preview*/
                                            /*setting group id*/
                                            group_id = groupInfo.getGroupId();
                    
                                            Intent intent = new Intent();
                                            intent.setClass(context, viewGroup.class);
                                            context.startActivity(intent);
                                        }
                
                                        @Override
                                        public void onJoinClick (groupInfo groupInfo, MaterialButton is_joined,
                                                                 int position, ProgressBar progressBar) {
                                            is_joined.setText(null);
                                            progressBar.setVisibility(View.VISIBLE);
                                            
                                            joinUnjoinObservable =
                                                    rxJavaQueries.joinGroup(accessToken, serverKey, groupInfo.getGroupId());
                                            
                                            joinUnjoinObservable.subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new Observer<join_unjoin>() {
                                                        @Override
                                                        public void onSubscribe (@NonNull Disposable d) {
                                                            Log.d(TAG, "onSubscribe: ");
                                                        }
    
                                                        @Override
                                                        public void onNext (@NonNull join_unjoin join_unjoin) {
                                                            if (join_unjoin.getApiStatus() == 200) {
                                                                
                                                                progressBar.setVisibility(View.GONE);
    
                                                               if (join_unjoin.getJoinStatus().equals("left")) {
                                                                   snack = new KSnack((FragmentActivity) context);
                                                                   snack.setMessage("You have left " + groupInfo.getName());
                                                                   snack.show();
                                                                   snack.setDuration(3000);
                                                                   
                                                               } else {
                                                                   /*remove
                                                                   from list
                                                                   and update
                                                                    adapter*/
                                                                   groupInfoList.remove(position);
                                                                   suggested_groups_adapter.notifyDataSetChanged();
                                                                   
                                                                   /*.....*/
                                                                   updateUI();
                                                                   
                                                                   /*TODO Add
                                                                       group
                                                                       to
                                                                       joined
                                                                        groups*/
                                                                   /*show
                                                                   snack*/
                                                                   snack = new KSnack((FragmentActivity) context);
                                                                   snack.setMessage("You have joined " + groupInfo.getName());
                                                                   snack.show();
                                                                   snack.setDuration(3000);
                                                               }
                                                            }
                                                            else {
                                                                apiErrors errors = join_unjoin.getErrors();
                                                                Log.d(TAG, "onNext: " + errors.getErrorText());
    
                                                                snack = new KSnack((FragmentActivity) context);
                                                                snack.setMessage("Oops !\nSomething went " +
                                                                        "wrong");
                                                                snack.setAction("DISMISS", view -> {
                                                                    snack.dismiss();
                                                                });
                                                                snack.show();
                                                                snack.setDuration(3000);
                                                            }
                                                        }
    
                                                        @Override
                                                        public void onError (@NonNull Throwable e) {
                                                            Log.d(TAG,
                                                                    "onError:" +
                                                                            " " + e.getMessage());
    
                                                            snack = new KSnack((FragmentActivity) context);
                                                            snack.setMessage("Oops !\nSomething went " +
                                                                    "wrong");
                                                            snack.show();
                                                            snack.setDuration(3000);
                                                        }
    
                                                        @Override
                                                        public void onComplete () {
                                                            Log.d(TAG, "onComplete: ");
                                                        }
                                                    });
                                        }
                                    });
                            
    
                            /*Setting Layout*/
                            suggestedGroupsList.setLayoutManager(new LinearLayoutManager(context));
                            suggestedGroupsList.setItemAnimator(new DefaultItemAnimator());
    
                            /*Setting Adapter*/
                            suggestedGroupsList.setAdapter(suggested_groups_adapter);
                            
                            /*......*/
                            /*After fetching Data*/
                            updateUI();
                        }
                        else {
                            apiErrors apiErrors = groupList.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                        }
                    }
    
                    private void updateUI () {
                        if (groupInfoList.size() == 0) {
                            /*Show all set*/
                            suggestedGroupsList.setVisibility(View.GONE);
                            progressBarSuggested.setVisibility(View.GONE);
    
                            allSetUpImg.setVisibility(View.VISIBLE);
                            allSetUpText.setVisibility(View.VISIBLE);
        
                        } else {
                            /*Show recyclerview*/
                            allSetUpImg.setVisibility(View.GONE);
                            allSetUpText.setVisibility(View.GONE);
                            
                            progressBarSuggested.setVisibility(View.GONE);
                            suggestedGroupsList.setVisibility(View.VISIBLE);
                        }
                    }
    
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " +e.getMessage());
                    }
    
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

//    /*get group info*/
//    public void getGroupInfo (ShapeableImageView profile_pic, ImageView cover_pic, Chip members, Chip privacy, Chip category) {
//        groupCall = queries.getGroupInfo(accessToken, serverKey, group_id);
//        groupCall.enqueue(new Callback<group>() {
//            @Override
//            public void onResponse (@NotNull Call<group> call, @NotNull like_dislike<group> response) {
//                if (response.body() != null) {
//                    if (response.body().getApiStatus() == 200) {
//
//                        if (groupInfo == null) {
//                            groupInfo = response.body().getGroupInfo();
//                        }
//
//                        Log.d(TAG, "onResponse: " + groupInfo.getMembers() + groupInfo.getType() +
//                                groupInfo.getName() + groupInfo.getJoinPrivacy() + groupInfo.getPrivacy());
//
//                        /*JoinPrivacy
//                            1: Join moja kwa moja
//                            2: Hapa ni request
//                        * Privacy
//                            1: Public
//                            2: Private
//                        * */
//
//                        /*load group info*/
//                        members.setText(groupInfo.getMembers() + " Members");
//                        /*setting group privacy*/
//                        if (groupInfo.getPrivacy().equals("1")) {
//                            privacy.setText("Public");
//                        } else {
//                            privacy.setText("Private");
//                        }
//                        /*setting category*/
//                        category.setText(groupInfo.getCategory());
//                        /*group cover*/
//                        Picasso.get().load(groupInfo.getCover()).fit().centerCrop().placeholder(R.drawable.ic_image_placeholder).into(cover_pic, new com.squareup.picasso.Callback() {
//                            @Override
//                            public void onSuccess () {
//                                Log.d(TAG, "onSuccess: " + "Image loaded");
//                            }
//
//                            @Override
//                            public void onError (Exception e) {
//                                Log.d(TAG, "onError: " + e.getMessage());
//                                cover_pic.setImageResource(R.drawable.ic_image_placeholder);
//                                /*TODO reload icon*/
//                            }
//                        });
//                        /*group profile pic*/
//                        profile_pic.setShapeAppearanceModel(profile_pic
//                                .getShapeAppearanceModel()
//                                .toBuilder()
//                                .setAllCorners(CornerFamily.ROUNDED, 20)
//                                .build());
//                        Picasso.get().load(groupInfo.getAvatar()).placeholder(R.drawable.ic_image_placeholder).into(profile_pic, new com.squareup.picasso.Callback() {
//                            @Override
//                            public void onSuccess () {
//                                Log.d(TAG, "onSuccess: " + "Image loaded");
//                            }
//
//                            @Override
//                            public void onError (Exception e) {
//                                Log.d(TAG, "onError: " + e.getMessage());
//                                profile_pic.setImageResource(R.drawable.ic_image_placeholder);
//                                /*TODO reload icon*/
//                            }
//                        });
//
//                    }
//                    else {
//                        /*TODO Error from API itself*/
//                        apiErrors apiErrors = response.body().getErrors();
//                        Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
//                        Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
//                    }
//                }
//                else {
//                    Log.i(TAG, "onResponse: " + "is null");
//                    /*TODO response is null*/
//                }
//            }
//
//            @Override
//            public void onFailure (@NotNull Call<group> call, @NotNull Throwable t) {
//                Log.i(TAG, "onFailure: " + t.getMessage());
//                /*TODO failed to get group info*/
//            }
//        });
//    }
//
    /*get joined groups*/
    public void getJoined (RecyclerView joinedGroupsList, ProgressBar progressBarJoined,
                           ImageView joinedGroupsShowHereImg, TextView joinedGroupsShowHereTxt) {
        groupListObservable = rxJavaQueries.getJoinedGroups(accessToken,
                serverKey, joined_groups, userId);
        groupListObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<groupList>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
    
                    @Override
                    public void onNext (@NonNull groupList groupList) {
                        if (groupList.getApiStatus() == 200) {
    
                            /*initializing list*/
                            groupInfoList = groupList.getInfo();
    
                            /*initializing adapter*/
                            joinedGroupsAdapter listAdapter =
                                    new joinedGroupsAdapter(context,
                                            groupInfoList, groupInfo -> {
                                                Log.d(TAG, "onGroupClick: " + groupInfo.getGroupName());
        
                                                /*TODO implement group view or preview*/
                                                /*setting group id*/
                                                group_id = groupInfo.getGroupId();
        
                                                Intent intent = new Intent();
                                                intent.setClass(context, viewGroup.class);
                                                context.startActivity(intent);
                                            });
    
                            /*Setting Layout*/
                            joinedGroupsList.setItemAnimator(new DefaultItemAnimator());
    
                            /*Setting Adapter*/
                            joinedGroupsList.setAdapter(listAdapter);
                            
                            /*......*/
                            /*After fetching Data*/
                            updateUI();
                            
                        }
                        else {
                            apiErrors apiErrors = groupList.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                        }
                    }
    
                    private void updateUI () {
                        if (groupInfoList.size() == 0) {
                            /*Show all set*/
                            joinedGroupsList.setVisibility(View.GONE);
                            progressBarJoined.setVisibility(View.GONE);
            
                            joinedGroupsShowHereImg.setVisibility(View.VISIBLE);
                            joinedGroupsShowHereTxt.setVisibility(View.VISIBLE);
            
                        } else {
                            /*Show recyclerview*/
                            joinedGroupsShowHereImg.setVisibility(View.GONE);
                            joinedGroupsShowHereTxt.setVisibility(View.GONE);
            
                            progressBarJoined.setVisibility(View.GONE);
                            joinedGroupsList.setVisibility(View.VISIBLE);
                        }
                    }
    
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
    
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    /*get friends followers*/
    
    public void getFollowers (RecyclerView friendsFollowersList, TextView followersTitle,
                              ProgressBar progressBarFollowers, ImageButton refreshFollowers) {
    
        /*show progressbar*/
        progressBarFollowers.setVisibility(View.VISIBLE);
    
        friendsObservable = rxJavaQueries.getFriendsFollowers(accessToken,
                serverKey, friends_followers, userId, "8");
        friendsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<friends>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
                
                    @Override
                    public void onNext (@NonNull friends friends) {
                        if (friends.getApiStatus() == 200) {
                        
                            /*initializing list*/
                            followersList = friends.getFriendsList().getFollowers();
                        
                            /*initializing adapter*/
                            friendsFollowersAdapter listAdapter =
                                    new friendsFollowersAdapter(context,
                                            followersList, followers -> {
                                        /*start new user
                                            profile Activity*/
                                        Intent intent =
                                                new Intent(context,
                                                        userProfile.class);
                                        intent.putExtra("user_id",
                                                followers.getUserId());
                                    
                                        context.startActivity(intent);
                                    });
                        
                            /*Setting Layout*/
                            friendsFollowersList.setLayoutManager(new GridLayoutManager(context, 3));
                            friendsFollowersList.setItemAnimator(new DefaultItemAnimator());
                        
                            /*Setting Adapter*/
                            friendsFollowersList.setAdapter(listAdapter);
    
                            /*show recycler view, refresh btn, hide progress*/
                            followersTitle.setText("Followers");
                            friendsFollowersList.setVisibility(View.VISIBLE);
                            progressBarFollowers.setVisibility(View.GONE);
                            refreshFollowers.setVisibility(View.VISIBLE);
                        }
                        else {
                            apiErrors apiErrors = friends.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                            /*.......*/
                            progressBarFollowers.setVisibility(View.GONE);
                            refreshFollowers.setVisibility(View.VISIBLE);
                            /*........*/
                            followersTitle.setText("Error getting users");
                        }
                    }
                
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        /*show refresh btn*/
                        progressBarFollowers.setVisibility(View.GONE);
                        refreshFollowers.setVisibility(View.VISIBLE);
    
                        /*.....*/
                        followersTitle.setText("Error getting users");
                    }
                
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    
        /*refresh btn*/
        refreshFollowers.setOnClickListener(view -> {
            getFollowers(friendsFollowersList, followersTitle,
                    progressBarFollowers, refreshFollowers);
        
            /*visibility*/
            friendsFollowersList.setVisibility(View.GONE);
            refreshFollowers.setVisibility(View.GONE);
            progressBarFollowers.setVisibility(View.VISIBLE);
        
        });
    }

    /*get friends followers*/
    
    public void getFollowing (RecyclerView friendsFollowingList, TextView followingTitle,
                              ProgressBar progressBarFollowing, ImageButton refreshFollowing) {
    
        /*show progressbar*/
        progressBarFollowing.setVisibility(View.VISIBLE);
    
        friendsObservable = rxJavaQueries.getFriendsFollowing(accessToken,
                serverKey, friends_following, userId, "8");
        friendsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<friends>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
                
                    @Override
                    public void onNext (@NonNull friends friends) {
                        if (friends.getApiStatus() == 200) {
                        
                            /*initializing list*/
                            followingList =
                                    friends.getFriendsList().getFollowing();
                        
                            /*initializing adapter*/
                            friendsFollowingAdapter listAdapter =
                                    new friendsFollowingAdapter(context,
                                            followingList, following -> {
                                                /*start new user
                                                profile Activity*/
                                        Intent intent =
                                                new Intent(context,
                                                        userProfile.class);
                                        intent.putExtra("user_id",
                                                following.getUserId());
                                    
                                        context.startActivity(intent);
                                    });
                        
                            /*Setting Layout*/
                            friendsFollowingList.setLayoutManager(new GridLayoutManager(context, 3));
                            friendsFollowingList.setItemAnimator(new DefaultItemAnimator());
                        
                            /*Setting Adapter*/
                            friendsFollowingList.setAdapter(listAdapter);
    
                            /*show recycler view, refresh btn, hide progress*/
                            followingTitle.setText("Following");
                            friendsFollowingList.setVisibility(View.VISIBLE);
                            progressBarFollowing.setVisibility(View.GONE);
                            refreshFollowing.setVisibility(View.VISIBLE);
                            
                        }
                        else {
                            apiErrors apiErrors = friends.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                            /*.......*/
                            progressBarFollowing.setVisibility(View.GONE);
                            refreshFollowing.setVisibility(View.VISIBLE);
                            /*........*/
                            followingTitle.setText("Error getting users");
                        }
                    }
                
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        /*show refresh btn*/
                        progressBarFollowing.setVisibility(View.GONE);
                        refreshFollowing.setVisibility(View.VISIBLE);
    
                        /*.....*/
                        followingTitle.setText("Error getting users");
                    }
                
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    
        /*refresh btn*/
        refreshFollowing.setOnClickListener(view -> {
            getFollowing(friendsFollowingList, followingTitle,
                    progressBarFollowing, refreshFollowing);
        
            /*visibility*/
            friendsFollowingList.setVisibility(View.GONE);
            refreshFollowing.setVisibility(View.GONE);
            progressBarFollowing.setVisibility(View.VISIBLE);
        
        });
    }

    /*get suggested friends*/
    public void getSuggestedFriends (RecyclerView suggestedFriendsList, TextView suggestedTitle,
                                     ProgressBar progressBarSuggested, ImageButton refreshSuggested) {
        /*show progressbar*/
        progressBarSuggested.setVisibility(View.VISIBLE);
        
        suggestedListObservable =
                rxJavaQueries.getPeopleYouMayKnow(accessToken, serverKey,
                        suggested_friends, "10");
    
        suggestedListObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<suggestedList>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
                
                    @Override
                    public void onNext (@NonNull suggestedList suggestedList) {
                        if (suggestedList.getApiStatus() == 200) {
                            /*initializing list*/
                            suggestedInfoList =
                                    suggestedList.getSuggestedInfo();
                        
                            /*initializing adapter*/
                            suggestedFriendsAdapter listAdapter = new suggestedFriendsAdapter(context, suggestedInfoList,
                                    new suggestedFriendsAdapter.onSuggestedClickListener() {
                                        @Override
                                        public void onClick (suggestedInfo suggestedInfo) {
                                            /*preview profile*/
                                            previewUserId = suggestedInfo.getUserId();
                                            new XPopup.Builder(context).asCustom(new previewProfilePopup(context)).show();
                                        }
                                    });
                        
                            /*Setting Layout*/
                            suggestedFriendsList.setLayoutManager(new LinearLayoutManager(context));
                            suggestedFriendsList.setItemAnimator(new DefaultItemAnimator());
                        
                            /*Setting Adapter*/
                            suggestedFriendsList.setAdapter(listAdapter);
                            
                            /*show recycler view, refresh btn, hide progress*/
                            suggestedTitle.setText("People you may know");
                            suggestedFriendsList.setVisibility(View.VISIBLE);
                            progressBarSuggested.setVisibility(View.GONE);
                            refreshSuggested.setVisibility(View.VISIBLE);
                        }
                        else {
                            apiErrors apiErrors = suggestedList.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                            /*.......*/
                            progressBarSuggested.setVisibility(View.GONE);
                            refreshSuggested.setVisibility(View.VISIBLE);
                            /*........*/
                            suggestedTitle.setText("Error getting users");
                        }
                    }
                
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        
                        /*show refresh btn*/
                        progressBarSuggested.setVisibility(View.GONE);
                        refreshSuggested.setVisibility(View.VISIBLE);
                        
                        /*.....*/
                        suggestedTitle.setText("Error getting users");
                    }
                
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
        
        /*refresh btn*/
        refreshSuggested.setOnClickListener(view -> {
            getSuggestedFriends(suggestedFriendsList, suggestedTitle,
                    progressBarSuggested, refreshSuggested);
            
            /*visibility*/
            suggestedFriendsList.setVisibility(View.GONE);
            refreshSuggested.setVisibility(View.GONE);
            progressBarSuggested.setVisibility(View.VISIBLE);
            
        });
    }
    
    /*Get timelineFeed*/
    public void getTimelineFeed (RecyclerView timelinePostsList) {
        
       linearLayoutManager = new LinearLayoutManager(context);
       timelinePostsList.setLayoutManager(linearLayoutManager);
        
        /*load posts*/
        Log.d(TAG, "LOADING : " + afterPostId);
        loadPosts(timelinePostsList, afterPostId);
    }
    
    private void loadPosts (RecyclerView timelinePostsList, String afterPostId) {
        postListObserve = rxJavaQueries.getTimelinePosts(accessToken,
                serverKey, get_news_feed, "10", afterPostId);
        
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
                                }
                                
                                /*initialize adapter*/
                                feedAdapter =
                                        new timelineFeedAdapter(timelinePosts
                                                ,
                                                context, new timelineFeedAdapter.onClickListener() {
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
                                                /*setting extra bundle string*/
                                                postId = post_Id;
                                                postUrl = url;
                                                postAuthor =
                                                        name;
                                                new XPopup.Builder(context).asCustom(new sharePopup(context)).show();
                                            }
    
                                            @Override
                                            public void onProfilePicClicked (String userId) {
                                                previewUserId = userId;
                                                new XPopup.Builder(context).asCustom(new previewProfilePopup(context)).show();
                                            }
                                        });
                                
                                /*setting adapter*/
                                timelinePostsList.setAdapter(feedAdapter);
    
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
                                    loadPosts(timelinePostsList, afterPostId);
                                });
                                snack.show();
                            }
                        }
                        else {
                            if (postList.getApiStatus() == 200) {
                                Log.d(TAG, "onNext: LOAD MORE DATA");
                                List<post> test = new ArrayList<>();
                                
                                List<post> tobeAdded = getAll(postList);
                                if (tobeAdded != null) {
                                    for (post newPosts : tobeAdded) {
                                        Log.d(TAG,
                                                "onNext: NEW POSTS ID : " + newPosts.getPostId());
                                        test.add(newPosts);
                                    }
                                }
                                
                                addData(test);
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
                                    loadPosts(timelinePostsList, afterPostId);
                                });
                                snack.show();
                            }
                        }
                    }
    
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
    
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
                            loadPosts(timelinePostsList, afterPostId);
                        });
                        snack.show();
                    }
    
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
    
    private void likePost (String postId, MaterialButton likes, TextView no_likes) {
        like_dislikeObservable = rxJavaQueries.like_dislikePost(accessToken,
                serverKey, postId, "like");
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
    
    private void addData (List<post> test) {
        int initialSize = timelinePosts.size();
        timelinePosts.addAll(test);
        feedAdapter.notifyItemRangeInserted(initialSize,
                timelinePosts.size()-1);
    }
    
    private List<post> getAll (postList postList) {
        timelinePosts=  postList.getPostList();
        return timelinePosts != null ? postList.getPostList(): null;
    }
    
    /*share post on other apps*/
    public void shareOnOtherApps () {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, postAuthor);
        intent.putExtra(Intent.EXTRA_TEXT, postUrl);
        context.startActivity(Intent.createChooser(intent, "choose " +
                "one"));
    }
    
    /*share post direct on timeline*/
    public void shareOnTimeline () {
        
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run () {
                snack = new KSnack((FragmentActivity) context);
                snack.setMessage("Sharing to your timeline ...");
                snack.show();
            }
        });
        
        shareResponseObservable =
                rxJavaQueries.sharePostInTimeline(accessToken, serverKey, share_post_on_timeline,
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
                                shareOnTimeline();
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
                            shareOnTimeline();
                        });
                    }
    
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
    
    /*preview user profile*/
    public void previewProfile (ImageView cover_photo, ProgressBar progressBar_cover, ImageView profile_pic,
                                TextView name, ImageView verified_badge,
                                ImageView level_badge,
                                TextView no_followers, TextView no_following, MaterialButton follow,
                                TextView about,
                                ProgressBar progressBar_follow) {
    
        userInfoObservable = rxJavaQueries.getUserData(accessToken, serverKey,
                fetch_profile, previewUserId);
    
        userInfoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<userInfo>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
                
                    @Override
                    public void onNext (@NonNull userInfo userInfo) {
                    
                        if(userInfo.getApiStatus() == 200) {
    
                            /*follow privacy
                            0 - moja kwa moja
                            1 - conform first*/
                        
                            followPrivacy =
                                    userInfo.getUserData().getConfirmFollowers();
                        
                            name.setText(userInfo.getUserData().getName());
                            no_followers.setText(userInfo.getUserData().getDetails().getFollowersCount() + " followers");
                            no_following.setText(userInfo.getUserData().getDetails().getFollowingCount() + " following");
                        
                            if (userInfo.getUserData().getAbout() != null) {
                                about.setText(Html.fromHtml(userInfo.getUserData().getAbout()));
                            }
                            else {
                                about.setText("Hey there I am using soshoplus");
                            }
                        
                            if (userInfo.getUserData().getVerified().equals("1")) {
                                verified_badge.setVisibility(View.VISIBLE);
                            }
                        
                            switch (userInfo.getUserData().getProType()) {
                                case "1":
                                    level_badge.setImageResource(R.drawable.ic_star_badge);
                                    level_badge.setVisibility(View.VISIBLE);
                                    break;
                                case "2":
                                    level_badge.setImageResource(R.drawable.ic_hot_badge);
                                    level_badge.setVisibility(View.VISIBLE);
                                    break;
                                case "3":
                                    level_badge.setImageResource(R.drawable.ic_ultima_badge);
                                    level_badge.setVisibility(View.VISIBLE);
                                    break;
                                case "4":
                                    level_badge.setImageResource(R.drawable.ic_pro_badge);
                                    level_badge.setVisibility(View.VISIBLE);
                                    break;
                                case "0":
                                default:
                                    level_badge.setVisibility(View.GONE);
                            }
                            
                            /*Follow privacy
                              is_following
                            * 0 = not following
                            * 1 = following
                            * 2 = requested*/
                        
                            if (userInfo.getUserData().getCanFollow() == 0 && userInfo.getUserData().getIsFollowing() == 0) {
                                follow.setVisibility(View.GONE);
                            } else if (userInfo.getUserData().getIsFollowing() == 0) {
                                follow.setVisibility(View.VISIBLE);
                                follow.setText("Follow");
                            }
                        
                            else if (userInfo.getUserData().getIsFollowing() == 2) {
                                follow.setText("Requested");
                            }
                            else {  /*(is_following == 1) */
                                follow.setText("Following");
                            }
                        
                            /*level badge*/
                            new Handler().postDelayed(() -> {
                                new glideImageLoader(cover_photo,
                                        progressBar_cover).load(userInfo.getUserData().getCover(),
                                        options);
                            
                                Glide.with(context).load(userInfo.getUserData().getAvatar())
                                        .apply(RequestOptions.circleCropTransform()).into(profile_pic);
                            
                            }, 500);
                        
                        }
                        else {
                            profile_pic.setImageResource(R.drawable.ic_image_placeholder);
                            apiErrors apiErrors =userInfo.getErrors();
                            Log.d(TAG, "main activity profile: " + apiErrors.getErrorText());
                        
                            snack = new KSnack((FragmentActivity) context);
                            snack.setMessage("Oops !\nSomething went " +
                                    "wrong");
                            snack.setAction("DISMISS", view -> {
                                snack.dismiss();
                            });
                            snack.show();
                        }
                    }
                
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    
                        snack = new KSnack((FragmentActivity) context);
                        snack.setMessage("Oops !\nSomething went " +
                                "wrong");
                        snack.setAction("DISMISS", view -> {
                            snack.dismiss();
                        });
                        snack.show();
                    }
                
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    
        follow.setOnClickListener(view -> {
            /*follow user*/
        
            followUnfollowObservable = rxJavaQueries.followUser(accessToken,
                    serverKey, previewUserId);
        
            followUser(follow, progressBar_follow);
        });
    }
    /*follow user*/
    private void followUser (MaterialButton follow, ProgressBar progressBar_follow) {
    
        /*set text null
         * show progress*/
        follow.setText(null);
        progressBar_follow.setVisibility(View.VISIBLE);
    
        followUnfollowObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<follow_unfollow>() {
                    @Override
                    public void onNext (@NonNull follow_unfollow follow_unfollow) {
                        if (follow_unfollow.getApiStatus() == 200) {
                        
                            Log.d(TAG, "onNext: " + follow_unfollow.getFollowStatus());
                        
                            if (follow_unfollow.getFollowStatus().equals(
                                    "unfollowed")) {
                            
                                progressBar_follow.setVisibility(View.GONE);
                                follow.setText("Follow");
                            }
                            else {
                                if (followPrivacy.equals("1")) {
                                    progressBar_follow.setVisibility(View.GONE);
                                    follow.setText("Requested");
                                }
                                else {
                                    progressBar_follow.setVisibility(View.GONE);
                                    follow.setText("Following");
                                }
                            }
                        }
                        else {
                            apiErrors errors = follow_unfollow.getErrors();
                            Log.d(TAG, "onNext: " + errors.getErrorText());
                            
                            snack = new KSnack((FragmentActivity) context);
                            snack.setMessage("Oops !\nSomething went " +
                                    "wrong");
                            snack.setAction("DISMISS", view -> {
                                snack.dismiss();
                            });
                            snack.show();
                        }
                    }
                
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    
                        snack = new KSnack((FragmentActivity) context);
                        snack.setMessage("Oops !\nSomething went " +
                                "wrong");
                        snack.setAction("DISMISS", view -> {
                            snack.dismiss();
                        });
                        snack.show();
                    }
                
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
