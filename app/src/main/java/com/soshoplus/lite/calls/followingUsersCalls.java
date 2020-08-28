/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.calls;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.R;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.utils.queries;
import com.soshoplus.lite.utils.retrofitInstance;
import com.soshoplus.lite.adapters.friendsFollowingAdapter;
import com.soshoplus.lite.models.friends.following;
import com.soshoplus.lite.models.friends.friends;
import com.soshoplus.lite.ui.user_profile.userProfile;

import java.util.List;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class followingUsersCalls {
    
    private final static String TAG = "Following Calls";
    /*context*/
    private Context context;
    /*......*/
    private queries rxJavaQueries;
    private String accessToken, userId, timezone;
    /*.......*/
    private static String friends_following = "following";
    private Observable<friends> friendsObservable;
    private List<following> followingList = null;
    
    public followingUsersCalls (Context context) {
        this.context = context;
    
        userId = SecurePreferences.getStringValue(context, "userId", "0");
        timezone = SecurePreferences.getStringValue(context, "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(context, "accessToken"
                , "0");
    
        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);
    }
    
    public void getFollowing (RecyclerView friendsFollowingList, TextView followingTitle,
                              ProgressBar progressBarFollowing, ImageButton refreshFollowing) {
    
        /*show progressbar*/
        progressBarFollowing.setVisibility(View.VISIBLE);
    
        friendsObservable = rxJavaQueries.getFriendsFollowing(accessToken,
                BuildConfig.server_key, friends_following, userId, "8");
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
                            friendsFollowingAdapter followingsAdapter =
                                    new friendsFollowingAdapter(R.layout.friends_list_row, followingList);
                        
                            /*Setting Layout*/
                            friendsFollowingList.setLayoutManager(new GridLayoutManager(context, 3));
                        
                            /*Setting Adapter*/
                            friendsFollowingList.setAdapter(followingsAdapter);
                            
                            /*on click*/
                            followingsAdapter.setOnItemClickListener((adapter
                                    , view, position) -> {
                                
                                /*start new user profile Activity*/
                                Intent intent = new Intent(context, userProfile.class);
                                intent.putExtra("user_id", followingList.get(position).getUserId());
                                context.startActivity(intent);
                            });
                        
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
