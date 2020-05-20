/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.userprofile.details;
import com.soshoplus.timeline.models.userprofile.userData;
import com.soshoplus.timeline.models.userprofile.userInfo;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class myProfile extends AppCompatActivity {
    
    private static String server_key = "a41ab77c99ab5c9f46b66a894d97cce9";
    private static   String fetch = "user_data,family,liked_pages,joined_groups";
    private static String TAG = "profile Activity ";
    private queries profileQueries;
    private Call<userInfo> userInfoCall;
    private String access_token, user_id;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            access_token = bundle.getString("access_token");
            user_id = bundle.getString("user_id");
        }
    
        Log.d(TAG, "onCreate: " + access_token);
        Log.d(TAG, "onCreate: " + user_id);
    
        //Initializing Retrofit Instance for Login
        profileQueries = retrofitInstance.getRetrofitInst().create(queries.class);
        userInfoCall = profileQueries.getUserData(access_token, server_key, fetch, user_id);
        
        userInfoCall.enqueue(new Callback<userInfo>() {
            @Override
            public void onResponse (@NonNull Call<userInfo> call, @NonNull Response<userInfo> response) {
                if (response.body() != null) {
                    
                    if (response.body().getApiStatus() == 200) {
                        userData userData = response.body().getUserData();
                        details details = response.body().getUserData().getDetails();
                        userInfo userInfo = response.body();
                        
                        Gson gson = new Gson();
                        String userdata = gson.toJson(userData);
                        String userdetails = gson.toJson(details);
                        String userinfo = gson.toJson(userInfo);
    
                        Log.d(TAG, "onResponse: " + userinfo);
                        Log.d(TAG, "onResponse: " + userdata);
                        Log.d(TAG, "onResponse: " + userdetails);
                    }
                    else {
                        apiErrors apiErrors = response.body().getErrors();
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                    }
                }
                else {
                    //response is null
                    Log.d(TAG, "onResponse: " + "Response is Null");
                }
            }
    
            @Override
            public void onFailure (@NonNull Call<userInfo> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
