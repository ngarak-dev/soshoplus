/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
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
import com.soshoplus.timeline.models.postsfeed.post;
import com.soshoplus.timeline.models.postsfeed.postList;
import com.soshoplus.timeline.models.userprofile.details;
import com.soshoplus.timeline.models.userprofile.userData;
import com.soshoplus.timeline.models.userprofile.userInfo;
import com.soshoplus.timeline.ui.groups.viewGroup;
import com.soshoplus.timeline.ui.viewVideo;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class retrofitCalls {
    
    private Context context;
    private queries queries;
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
    
    private userData userData = null;
    private userInfo userInfo = null;
    private details details = null;
    
    /*CALLS*/
    private Call<userInfo> userInfoCall;
    private Call<group> groupCall;
    private Call<groupList> groupListCall;
    private Call<friends> friendsListCall;
    private Call<suggestedList> suggestedListCall;
    
    /*GROUPS*/
    private List<groupInfo> groupInfoList = null;
    /*FRIENDS*/
    private List<followers> followersList = null;
    private List<following> followingList = null;
    private List<suggestedInfo> suggestedInfoList = null;
    
    /*TODO Check this later*/
    private static String group_id;
    private groupInfo groupInfo;
    
    /*TIMELINE*/
    private Call<postList> postListCall;
    private List<post> postList = null;
    
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
        
        queries = retrofitInstance.getRetrofitInst().create(queries.class);
    }
    
    /*TODO NULL exception zipo AVOID AVOID*/
    
    /*get profile on main activity*/
    public void getProfile (ShapeableImageView profile_pic, TextView full_name, TextView username) {
        userInfoCall = queries.getUserData(accessToken, serverKey, fetch_profile, userId);
        userInfoCall.enqueue(new Callback<userInfo>() {
            @Override
            public void onResponse (@NonNull Call<userInfo> call,
                                    @NonNull Response<userInfo> response) {
                if (response.body() != null) {

                    if (response.body().getApiStatus() == 200) {

                        if (userData == null) {
                            userData = new userData();
                            userData = response.body().getUserData();
                        }
                        
                        full_name.setText(userData.getName());
                        username.setText("@" + userData.getUsername());

                        //profile pic
                        profile_pic.setShapeAppearanceModel(profile_pic
                                .getShapeAppearanceModel()
                                .toBuilder()
                                .setAllCorners(CornerFamily.ROUNDED, 20)
                                .build());
                        Picasso.get().load(userData.getAvatar()).placeholder(R.drawable.ic_image_placeholder).into(profile_pic, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess () {
                                Log.d(TAG, "onSuccess: " + "Image loaded");
                            }
    
                            @Override
                            public void onError (Exception e) {
                                Log.d(TAG, "onError: " + e.getMessage());
                                profile_pic.setImageResource(R.drawable.ic_image_placeholder);
                                /*TODO reload icon*/
                            }
                        });

                    } else {
                        apiErrors apiErrors = response.body().getErrors();
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                    }

                } else {
                    Log.i(TAG, "onResponse: " + "is null");
                }
            }

            @Override
            public void onFailure (@NonNull Call<userInfo> call, @NonNull Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
    
    /*get recommended groups*/
    public void getRecommends (RecyclerView suggestedGroupsList) {
        groupListCall = queries.getRecommended(accessToken, serverKey, fetch_recommended, "5");
        groupListCall.enqueue(new Callback<groupList>() {
            @Override
            public void onResponse (@NotNull Call<groupList> call, @NotNull Response<groupList> response) {
                if (response.body() != null) {
                    if (response.body().getApiStatus() == 200) {
                        /*initializing list*/
                        groupInfoList = new ArrayList<>();
                        groupInfoList = response.body().getInfo();
                        
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
                        /*TODO Error from API itself*/
                        apiErrors apiErrors = response.body().getErrors();
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                    }
                }
                else {
                    Log.i(TAG, "onResponse: " + "is null");
                    /*TODO response is null*/
                }
            }
    
            @Override
            public void onFailure (@NotNull Call<groupList> call, @NotNull Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                /*TODO failed to get recommends*/
            }
        });
    }
    
    /*get group info*/
    public void getGroupInfo (ShapeableImageView profile_pic, ImageView cover_pic, Chip members, Chip privacy, Chip category) {
        groupCall = queries.getGroupInfo(accessToken, serverKey, group_id);
        groupCall.enqueue(new Callback<group>() {
            @Override
            public void onResponse (@NotNull Call<group> call, @NotNull Response<group> response) {
                if (response.body() != null) {
                    if (response.body().getApiStatus() == 200) {
                        
                        if (groupInfo == null) {
                            groupInfo = response.body().getGroupInfo();
                        }
    
                        Log.d(TAG, "onResponse: " + groupInfo.getMembers() + groupInfo.getType() +
                                groupInfo.getName() + groupInfo.getJoinPrivacy() + groupInfo.getPrivacy());
                        
                        /*JoinPrivacy
                            1: Join moja kwa moja
                            2: Hapa ni request
                        * Privacy
                            1: Public
                            2: Private
                        * */
                        
                        /*load group info*/
                        members.setText(groupInfo.getMembers() + " Members");
                        /*setting group privacy*/
                        if (groupInfo.getPrivacy().equals("1")) {
                            privacy.setText("Public");
                        } else {
                            privacy.setText("Private");
                        }
                        /*setting category*/
                        category.setText(groupInfo.getCategory());
                        /*group cover*/
                        Picasso.get().load(groupInfo.getCover()).fit().centerCrop().placeholder(R.drawable.ic_image_placeholder).into(cover_pic, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess () {
                                Log.d(TAG, "onSuccess: " + "Image loaded");
                            }
    
                            @Override
                            public void onError (Exception e) {
                                Log.d(TAG, "onError: " + e.getMessage());
                                cover_pic.setImageResource(R.drawable.ic_image_placeholder);
                                /*TODO reload icon*/
                            }
                        });
                        /*group profile pic*/
                        profile_pic.setShapeAppearanceModel(profile_pic
                                .getShapeAppearanceModel()
                                .toBuilder()
                                .setAllCorners(CornerFamily.ROUNDED, 20)
                                .build());
                        Picasso.get().load(groupInfo.getAvatar()).placeholder(R.drawable.ic_image_placeholder).into(profile_pic, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess () {
                                Log.d(TAG, "onSuccess: " + "Image loaded");
                            }
    
                            @Override
                            public void onError (Exception e) {
                                Log.d(TAG, "onError: " + e.getMessage());
                                profile_pic.setImageResource(R.drawable.ic_image_placeholder);
                                /*TODO reload icon*/
                            }
                        });
                        
                    }
                    else {
                        /*TODO Error from API itself*/
                        apiErrors apiErrors = response.body().getErrors();
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                    }
                }
                else {
                    Log.i(TAG, "onResponse: " + "is null");
                    /*TODO response is null*/
                }
            }
    
            @Override
            public void onFailure (@NotNull Call<group> call, @NotNull Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                /*TODO failed to get group info*/
            }
        });
    }
    
    /*get joined groups*/
    public void getJoined (RecyclerView joinedGroupsList) {
        groupListCall = queries.getJoinedGroups(accessToken, serverKey, joined_groups, userId);
        groupListCall.enqueue(new Callback<groupList>() {
            @Override
            public void onResponse (@NotNull Call<groupList> call, @NotNull Response<groupList> response) {
                if (response.body() != null) {
                    if (response.body().getApiStatus() == 200) {
                        /*initializing list*/
                        groupInfoList = new ArrayList<>();
                        groupInfoList = response.body().getInfo();
                    
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
                        /*TODO Error from API itself*/
                        apiErrors apiErrors = response.body().getErrors();
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                    }
                }
                else {
                    Log.i(TAG, "onResponse: " + "is null");
                    /*TODO response is null*/
                }
            }
        
            @Override
            public void onFailure (@NotNull Call<groupList> call, @NotNull Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                /*TODO failed to get recommends*/
            }
        });
    }
    
    /*get friends followers*/
    public void getFollowers (RecyclerView friendsFollowersList) {
        friendsListCall = queries.getFriendsFollowers(accessToken, serverKey, friends_followers,
                userId, "6");
        friendsListCall.enqueue(new Callback<friends>() {
            @Override
            public void onResponse (@NotNull Call<friends> call, @NotNull Response<friends> response) {
                if (response.body() != null) {
                    if (response.body().getApiStatus() == 200) {
                        /*initializing list*/
                        followersList = new ArrayList<>();
                        followersList = response.body().getFriendsList().getFollowers();
            
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
                        /*TODO Error from API itself*/
                        apiErrors apiErrors = response.body().getErrors();
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                    }
                }
                else {
                    Log.i(TAG, "onResponse: " + "is null");
                    /*TODO response is null*/
                }
            }
    
            @Override
            public void onFailure (@NotNull Call<friends> call, @NotNull Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                /*TODO failed to get recommends*/
            }
        });
    }
    
    /*get friends followers*/
    public void getFollowing (RecyclerView friendsFollowingList) {
        friendsListCall = queries.getFriendsFollowing(accessToken, serverKey, friends_following,
                userId, "6");
        friendsListCall.enqueue(new Callback<friends>() {
            @Override
            public void onResponse (@NotNull Call<friends> call, @NotNull Response<friends> response) {
                if (response.body() != null) {
                    if (response.body().getApiStatus() == 200) {
                        /*initializing list*/
                        followingList = new ArrayList<>();
                        followingList = response.body().getFriendsList().getFollowing();
                    
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
                        /*TODO Error from API itself*/
                        apiErrors apiErrors = response.body().getErrors();
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                    }
                }
                else {
                    Log.i(TAG, "onResponse: " + "is null");
                    /*TODO response is null*/
                }
            }
        
            @Override
            public void onFailure (@NotNull Call<friends> call, @NotNull Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                /*TODO failed to get recommends*/
            }
        });
    }
    
    /*get suggested friends*/
    public void getSuggestedFriends (RecyclerView suggestedFriendsList) {
        suggestedListCall = queries.getPeopleYouMayKnow(accessToken, serverKey, suggested_friends
                , "8");
        suggestedListCall.enqueue(new Callback<suggestedList>() {
            @Override
            public void onResponse (@NotNull Call<suggestedList> call, @NotNull Response<suggestedList> response) {
    
                if (response.body() != null) {
                    if (response.body().getApiStatus() == 200) {
                        /*initializing list*/
                        suggestedInfoList = new ArrayList<>();
                        suggestedInfoList = response.body().getSuggestedInfo();
            
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
                    }
                    else {
                        /*TODO Error from API itself*/
                        apiErrors apiErrors = response.body().getErrors();
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                    }
                }
                else {
                    Log.i(TAG, "onResponse: " + "is null");
                    /*TODO response is null*/
                }
                
            }
    
            @Override
            public void onFailure (@NotNull Call<suggestedList> call, @NotNull Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                /*TODO failed to get recommends*/
            }
        });
    }
    
    /*Get timeline Feed*/
    public void getTimelineFeed (RecyclerView timelinePostsList) {
        postListCall = queries.getTimelinePosts(accessToken, serverKey, get_news_feed, "9");
        postListCall.enqueue(new Callback<postList>() {
            @Override
            public void onResponse (@NotNull Call<postList> call, @NotNull Response<postList> response) {
                if (response.body() != null) {
                    if (response.body().getApiStatus() == 200) {
                        
                        postList = new ArrayList<>();
                        postList = response.body().getPostList();
                        
                        /*initializing adapter*/
                        timelineFeedAdapter listAdapter =
                                new timelineFeedAdapter(postList, context,
                                        new timelineFeedAdapter.onClickListener() {
                                    @Override
                                    public void onClickPlay (String postFile) {
                                        
                                        Intent intent = new Intent();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("link", postFile);
                                        intent.putExtras(bundle);
                                        intent.setClass(context, viewVideo.class);
                                        context.startActivity(intent);
                                    }
                                });
    
                        /*Setting Layout*/
                        timelinePostsList.setLayoutManager(new LinearLayoutManager(retrofitCalls.this.context));
                        timelinePostsList.setItemAnimator(new DefaultItemAnimator());
                        timelinePostsList.addItemDecoration(new DividerItemDecoration(retrofitCalls.this.context,
                                DividerItemDecoration.VERTICAL));
    
                        /*Setting Adapter*/
                        timelinePostsList.setAdapter(listAdapter);

                    }
                    else {
                        apiErrors apiErrors = response.body().getErrors();
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                    }
                }
                else {
                    /*response is null*/
                    Log.i(TAG, "onResponse: " + "is null");
                }
            }
    
            @Override
            public void onFailure (@NotNull Call<postList> call, @NotNull Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
