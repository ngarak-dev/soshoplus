/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.lxj.xpopup.XPopup;
import com.onurkagan.ksnack_lib.KSnack.KSnack;
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
import com.soshoplus.timeline.models.postsfeed.reactions.like_dislike;
import com.soshoplus.timeline.models.postsfeed.sharepost.shareResponse;
import com.soshoplus.timeline.models.userprofile.userInfo;
import com.soshoplus.timeline.ui.groups.viewGroup;
import com.soshoplus.timeline.utils.glide.glideImageLoader;
import com.soshoplus.timeline.utils.xpopup.previewProfilePopup;
import com.soshoplus.timeline.utils.xpopup.sharePopup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
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
    private static String share_post_on_timeline = "share_post_on_timeline";

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
    private static String profile_image, username, about_me, verified_status,
            pro_type,
    cover_image, post_Count, followers_Count, following_Count,
    /**/
    gender, birthday, working, school, city, address;
    
    /*GLIDE OPTIONS*/
    RequestOptions options = new RequestOptions()
            .format(DecodeFormat.PREFER_RGB_565)
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_placeholder)
            .priority(Priority.LOW);
    
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
                serverKey, get_news_feed, "3", afterPostId);
        
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
                                            public void onProfilePicClicked (String avatar, String name, String verified, String about, String proType,
                                                                             String cover, String postCount, String followersCount, String followingCount,
                                                                             String _gender, String _birthday, String _working, String _school, String _city, String _address) {
    
                                                profile_image = avatar;
                                                username = name;
                                                verified_status = verified;
                                                about_me = about;
                                                pro_type = proType;
                                                cover_image = cover;
                                                post_Count = postCount;
                                                followers_Count =
                                                        followersCount;
                                                following_Count =
                                                        followingCount;
                                                /*.......*/
                                                gender = _gender;
                                                birthday = _birthday;
                                                working = _working;
                                                school = _school;
                                                city = _city;
                                                address = _address;
    
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
    
    public void previewProfile (ImageView cover_photo, ProgressBar progressBar_cover,
                                ImageView profile_pic, TextView name,
                                ImageView verified_badge,
                                ImageView level_badge, TextView no_posts, TextView no_followers, TextView no_following,
                                MaterialButton follow,
                                TextView about, TextView _gender,
                                TextView _birthday, TextView _working,
                                TextView _school, TextView _living,
                                TextView _located) {
    
        name.setText(username);
        no_posts.setText(post_Count);
        no_followers.setText(followers_Count);
        no_following.setText(following_Count);
    
        if (about_me != null) {
            about.setText(Html.fromHtml(about_me));
        }
    
        if (!verified_status.equals("1")) {
            verified_badge.setVisibility(View.GONE);
        }
        
        switch (pro_type) {
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
        
        _gender.setText(gender);
        _birthday.setText(birthday);
        _working.append(working);
        _school.append(school);
        _living.append(address);
        _located.append(city);
        
        /*level badge*/
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run () {
                new glideImageLoader(cover_photo, progressBar_cover).load(cover_image,
                        options);
                
                Glide.with(context).load(profile_image)
                        .transform(new CropCircleWithBorderTransformation())
                        .into(profile_pic);
            
            }
        }, 500);
    }
}
