/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.adapters.friendsFollowersAdapter;
import com.soshoplus.timeline.adapters.friendsFollowingAdapter;
import com.soshoplus.timeline.adapters.joinedGroupsAdapter;
import com.soshoplus.timeline.adapters.suggestedFriendsAdapter;
import com.soshoplus.timeline.adapters.suggestedGroupsAdapter;
import com.soshoplus.timeline.adapters.timelineFeedAdapter;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.friends.followers;
import com.soshoplus.timeline.models.friends.following;
import com.soshoplus.timeline.models.friends.friends;
import com.soshoplus.timeline.models.friends.suggested.suggestedInfo;
import com.soshoplus.timeline.models.friends.suggested.suggestedList;
import com.soshoplus.timeline.models.groups.group;
import com.soshoplus.timeline.models.groups.groupInfo;
import com.soshoplus.timeline.models.groups.groupList;
import com.soshoplus.timeline.models.postsfeed.post;
import com.soshoplus.timeline.models.postsfeed.postList;
import com.soshoplus.timeline.models.userprofile.userInfo;
import com.soshoplus.timeline.ui.groups.viewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;


public class retrofitCalls {
    
    private Context context;
    private queries rxJavaQueries;
    private String accessToken, userId, timezone;
    private static String TAG = "Calls class";
    public static String serverKey = "a41ab77c99ab5c9f46b66a894d97cce9";
    private static String get_news_feed = "get_news_feed";
    private static String fetch_profile = "user_data,family,liked_pages,joined_groups";
    private static String fetch_recommended = "groups";
    private static String joined_groups = "joined_groups";
    private static String friends_followers = "followers";
    private static String friends_following = "following";
    private static String suggested_friends = "users";

//    /*CALLS*/
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
    
    public retrofitCalls (Context context){
        this.context = context;
        
        SharedPreferences pref = context.getSharedPreferences("userCred", 0); // 0 - for
        // private
        // mode
        if (pref.contains("userId")) {
            userId = pref.getString("userId", "0");
            timezone = pref.getString("timezone", "UTC");
            accessToken = pref.getString("accessToken", "0");
        } else {
            Log.i(TAG, "retrofitCalls: " + "Pref is Empty");
        }
    
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
    public void getRecommends (RecyclerView suggestedGroupsList) {
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
                            suggestedGroupsAdapter listAdapter =
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
                                        public void onJoinClick (groupInfo groupInfo) {
                                            Log.d(TAG, "onJoinClick: " + groupInfo.getGroupId());
                                            /*TODO implement group join or request or remove*/
                                        }
                                    });
    
                            /*Setting Layout*/
                            suggestedGroupsList.setLayoutManager(new LinearLayoutManager(context));
                            suggestedGroupsList.setItemAnimator(new DefaultItemAnimator());
    
                            /*Setting Adapter*/
                            suggestedGroupsList.setAdapter(listAdapter);
                        }
                        else {
                            apiErrors apiErrors = groupList.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
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
//            public void onResponse (@NotNull Call<group> call, @NotNull Response<group> response) {
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
    public void getJoined (RecyclerView joinedGroupsList) {
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
                                            groupInfoList, new joinedGroupsAdapter.onGroupClickListener() {
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
                                    });
    
                            /*Setting Layout*/
                            joinedGroupsList.setItemAnimator(new DefaultItemAnimator());
    
                            /*Setting Adapter*/
                            joinedGroupsList.setAdapter(listAdapter);
                            
                        }
                        else {
                            apiErrors apiErrors = groupList.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
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
    public void getFollowers (RecyclerView friendsFollowersList) {
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
                                            followersList,
                                            new friendsFollowersAdapter.onFriendClickListener() {
                                                @Override
                                                public void  onFriendClick (followers followers) {
                                                    Toast.makeText(context, followers.getName(),
                                                            Toast.LENGTH_SHORT).show();
                                                    /*TODO show user profile onclick*/
                                                }
                                            });
    
                            /*Setting Layout*/
                            friendsFollowersList.setLayoutManager(new GridLayoutManager(context, 3));
                            friendsFollowersList.setItemAnimator(new DefaultItemAnimator());
    
                            /*Setting Adapter*/
                            friendsFollowersList.setAdapter(listAdapter);
                        }
                        else {
                            apiErrors apiErrors = friends.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
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
    public void getFollowing (RecyclerView friendsFollowingList) {
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
                                            followingList,
                                            new friendsFollowingAdapter.onFriendClickListener() {
                                                @Override
                                                public void onFriendClick (following following) {
                                                    Toast.makeText(context, following.getName(),
                                                            Toast.LENGTH_SHORT).show();
                                                    /*TODO show user profile onclick*/
                                                }
                                            });
    
                            /*Setting Layout*/
                            friendsFollowingList.setLayoutManager(new GridLayoutManager(context, 3));
                            friendsFollowingList.setItemAnimator(new DefaultItemAnimator());
    
                            /*Setting Adapter*/
                            friendsFollowingList.setAdapter(listAdapter);
                        }
                        else {
                            apiErrors apiErrors = friends.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
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

    /*get suggested friends*/
    public void getSuggestedFriends (RecyclerView suggestedFriendsList, ExecutorService executorService) {
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
                            suggestedFriendsAdapter listAdapter = new suggestedFriendsAdapter(context, suggestedInfoList, new suggestedFriendsAdapter.onSuggestedClickListener() {
                                @Override
                                public void onClick (suggestedInfo suggestedInfo) {
                                    Toast.makeText(context, suggestedInfo.getName(), Toast.LENGTH_SHORT).show();
                                    /*TODO show user profile onclick*/
                                }
                            });
    
                            /*Setting Layout*/
                            suggestedFriendsList.setLayoutManager(new LinearLayoutManager(context));
                            suggestedFriendsList.setItemAnimator(new DefaultItemAnimator());
    
                            /*Setting Adapter*/
                            suggestedFriendsList.setAdapter(listAdapter);
                            
                            if (!executorService.isShutdown()) {
                                executorService.shutdown();
                            }
                        }
                        else {
                            apiErrors apiErrors = suggestedList.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
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
                                        });
                                
                                /*setting adapter*/
                                timelinePostsList.setAdapter(feedAdapter);
    
                            }
                            else {
                                apiErrors errors = postList.getErrors();
                                Log.d(TAG, "ERROR FROM API : " + errors.getErrorText());
    
                                /*displaying a dialog*/
                                CFAlertDialog.Builder builder =
                                        new CFAlertDialog.Builder(context)
                                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                                .setCancelable(false)
                                                .setTitle("Oops !")
                                                .setMessage(errors.getErrorText())
                                                .addButton("TRY AGAIN", -1 ,-1,
                                                        CFAlertDialog.CFAlertActionStyle.DEFAULT,
                                                        CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (
                                                                (dialogInterface, i) -> {
                                                                    dialogInterface.dismiss();
                                                                    loadPosts(timelinePostsList, afterPostId);
                                                                }))
                                                .addButton("DISMISS", -1, -1,
                                                        CFAlertDialog.CFAlertActionStyle.DEFAULT,
                                                        CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (
                                                                dialog, which) -> dialog.dismiss());
                                builder.show();
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
    
                                /*displaying a dialog*/
                                CFAlertDialog.Builder builder =
                                        new CFAlertDialog.Builder(context)
                                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                                .setCancelable(false)
                                                .setTitle("Oops !")
                                                .setMessage(errors.getErrorText())
                                                .addButton("TRY AGAIN", -1 ,-1,
                                                        CFAlertDialog.CFAlertActionStyle.DEFAULT,
                                                        CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (
                                                                (dialogInterface, i) -> {
                                                                    dialogInterface.dismiss();
                                                                    loadPosts(timelinePostsList, afterPostId);
                                                                }))
                                                .addButton("DISMISS", -1, -1,
                                                        CFAlertDialog.CFAlertActionStyle.DEFAULT,
                                                        CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (
                                                                dialog, which) -> dialog.dismiss());
                                builder.show();
                            }
                        }
                    }
    
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
    
                        /*displaying a dialog*/
                        CFAlertDialog.Builder builder =
                                new CFAlertDialog.Builder(context)
                                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                        .setCancelable(false)
                                        .setTitle("Oops !")
                                        .setMessage("Something went " +
                                                "wrong\nPlease check your " +
                                                "internet connection")
                                        .addButton("TRY AGAIN", -1 ,-1,
                                                CFAlertDialog.CFAlertActionStyle.DEFAULT,
                                                CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (
                                                        (dialogInterface, i) -> {
                                                            dialogInterface.dismiss();
                                                            loadPosts(timelinePostsList, afterPostId);
                                                        }))
                                        .addButton("DISMISS", -1, -1,
                                                CFAlertDialog.CFAlertActionStyle.DEFAULT,
                                                CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (
                                                        dialog, which) -> dialog.dismiss());
                        builder.show();
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
}
