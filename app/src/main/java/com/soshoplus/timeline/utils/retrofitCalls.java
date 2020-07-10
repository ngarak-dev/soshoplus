/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils;

import android.content.Context;
import android.content.Intent;
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
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.friends.followers;
import com.soshoplus.timeline.models.friends.following;
import com.soshoplus.timeline.models.friends.friends;
import com.soshoplus.timeline.models.friends.suggested.suggestedInfo;
import com.soshoplus.timeline.models.friends.suggested.suggestedList;
import com.soshoplus.timeline.models.groups.group;
import com.soshoplus.timeline.models.groups.groupInfo;
import com.soshoplus.timeline.models.groups.groupList;
import com.soshoplus.timeline.models.groups.join.join_unjoin;
import com.soshoplus.timeline.models.userprofile.userInfo;
import com.soshoplus.timeline.ui.groups.viewGroup;
import com.soshoplus.timeline.ui.user_profile.userProfile;
import com.soshoplus.timeline.utils.xpopup.previewProfilePopup;

import java.util.List;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class retrofitCalls {
    
    private Context context;
    private queries rxJavaQueries;
    private String accessToken, userId, timezone;
    private static String TAG = "Calls class";
    public static String serverKey = BuildConfig.server_key;
    private static String fetch_profile = "user_data,family,liked_pages,joined_groups";
    private static String fetch_recommended = "groups";
    private static String joined_groups = "joined_groups";
    private static String friends_followers = "followers";
    private static String friends_following = "following";
  
    
    /*ADAPTERS*/
    private suggestedGroupsAdapter suggested_groups_adapter;

    /*CALLS*/
    private Observable<userInfo> userInfoObservable;
    private Observable<group> groupObservable;
    private Observable<groupList> groupListObservable;
    private Observable<friends> friendsObservable;

    /*GROUPS*/
    private List<groupInfo> groupInfoList = null;
    /*FRIENDS*/
    private List<followers> followersList = null;
    private List<following> followingList = null;

    /*TODO Check this later*/
    private static String group_id;
    
    private KSnack snack;
    
    private static String previewUserId;
    
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
}
