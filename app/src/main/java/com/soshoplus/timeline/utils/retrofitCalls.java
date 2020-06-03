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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.soshoplus.timeline.adapters.groupsListAdapter;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.groups.group;
import com.soshoplus.timeline.models.groups.groupInfo;
import com.soshoplus.timeline.models.groups.groupList;
import com.soshoplus.timeline.models.userprofile.details;
import com.soshoplus.timeline.models.userprofile.userData;
import com.soshoplus.timeline.models.userprofile.userInfo;
import com.soshoplus.timeline.ui.groups.viewGroup;
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
    private Call<userInfo> userInfoCall;
    private Call<group> groupCall;
    private Call<groupList> groupListCall;
    private String accessToken, userId, timezone;
    private static String TAG = "Calls class";
    public static String serverKey = "a41ab77c99ab5c9f46b66a894d97cce9";
    private static String fetch_profile = "user_data,family,liked_pages,joined_groups";
    private static String fetch_recommended = "groups";
    
    private userData userData = null;
    private userInfo userInfo = null;
    private details details = null;
    
    private List<groupInfo> groupInfoList = null;
    
    /*TODO Check this later*/
    private static String group_id;
    private groupInfo groupInfo;
    
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
                        Picasso.get().load(userData.getAvatar()).into(profile_pic);

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
                        groupsListAdapter listAdapter =
                                new groupsListAdapter(context,
                                groupInfoList, new groupsListAdapter.onGroupClickListener() {
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
                        Picasso.get().load(groupInfo.getCover()).fit().centerCrop().into(cover_pic);
                        /*group profile pic*/
                        profile_pic.setShapeAppearanceModel(profile_pic
                                .getShapeAppearanceModel()
                                .toBuilder()
                                .setAllCorners(CornerFamily.ROUNDED, 20)
                                .build());
                        Picasso.get().load(groupInfo.getAvatar()).into(profile_pic);
                        
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
}
