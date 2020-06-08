/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.gson.Gson;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.postsfeed.post;
import com.soshoplus.timeline.models.postsfeed.postList;
import com.soshoplus.timeline.models.userprofile.details;
import com.soshoplus.timeline.models.userprofile.userData;
import com.soshoplus.timeline.models.userprofile.userInfo;
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
    private String accessToken, userId, timezone;
    private static String TAG = "Calls class";
    public static String serverKey = "a41ab77c99ab5c9f46b66a894d97cce9";
    private String fetch_profile = "user_data,family,liked_pages,joined_groups";
    private static String get_news_feed = "get_news_feed";
    
    private userData userData;
    private userInfo userInfo;
    private details details;
    
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
    
    public void getProfile (ShapeableImageView profile_pic, TextView full_name, TextView username) {
        userInfoCall = queries.getUserData(accessToken, serverKey, fetch_profile, userId);
        userInfoCall.enqueue(new Callback<userInfo>() {
            @Override
            public void onResponse (@NonNull Call<userInfo> call,
                                    @NonNull Response<userInfo> response) {
                if (response.body() != null) {

                    if (response.body().getApiStatus() == 200) {
                        userData = response.body().getUserData();

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
    
    /*Get timeline Feed*/
    public void getTimelineFeed (RecyclerView timelinePostsList) {
        postListCall = queries.getTimelinePosts(accessToken, serverKey, get_news_feed, "20");
        postListCall.enqueue(new Callback<postList>() {
            @Override
            public void onResponse (@NotNull Call<postList> call, @NotNull Response<postList> response) {
                if (response.body() != null) {
                    if (response.body().getApiStatus() == 200) {
                        
                        postList = new ArrayList<>();
                        postList = response.body().getPostList();
    
                        Gson gson = new Gson();
                        String gfgf = gson.toJson(postList);
    
                        Log.d(TAG, "onResponse: " + gfgf);
                        
                        /*initializing adapter*/
                        timelineFeedAdapter listAdapter =
                                new timelineFeedAdapter(postList,
                                        context);
    
                        /*Setting Layout*/
                        timelinePostsList.setLayoutManager(new LinearLayoutManager(context));
                        timelinePostsList.setItemAnimator(new DefaultItemAnimator());
    
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
