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
import com.soshoplus.lite.adapters.friendsFollowersAdapter;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.models.friends.followers;
import com.soshoplus.lite.models.friends.friends;
import com.soshoplus.lite.ui.user_profile.userProfile;
import com.soshoplus.lite.utils.queries;
import com.soshoplus.lite.utils.retrofitInstance;

import java.util.List;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class followersUsersCalls {

    private final static String TAG = "Followers Calls";
    private static String friends_followers = "followers";
    private Context context;
    private Observable<friends> friendsObservable;
    private List<followers> followersList = null;

    private static String userId, timezone, accessToken;
    private queries rxJavaQueries;

    public followersUsersCalls(Context context) {
        this.context = context;

        userId = SecurePreferences.getStringValue(context, "userId", "0");
        timezone = SecurePreferences.getStringValue(context, "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(context, "accessToken", "0");
        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);
    }


    public void getFollowers(RecyclerView friendsFollowersList, TextView followersTitle,
                             ProgressBar progressBarFollowers, ImageButton refreshFollowers) {

        /*show progressbar*/
        progressBarFollowers.setVisibility(View.VISIBLE);

        friendsObservable = rxJavaQueries.getFriendsFollowers(accessToken,
                BuildConfig.server_key, friends_followers, userId, "8");
        friendsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<friends>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull friends friends) {
                        if (friends.getApiStatus() == 200) {

                            /*initializing list*/
                            followersList = friends.getFriendsList().getFollowers();

                            /*initializing adapter*/
                            friendsFollowersAdapter followersAdapter =
                                    new friendsFollowersAdapter(R.layout.friends_list_row, followersList);

                            /*Setting Layout*/
                            friendsFollowersList.setLayoutManager(new GridLayoutManager(context, 3));

                            /*Setting Adapter*/
                            friendsFollowersList.setAdapter(followersAdapter);

                            /*onclick*/
                            followersAdapter.setOnItemClickListener((adapter, view,
                                                                     position) -> {

                                /*start new user profile Activity*/
                                Intent intent = new Intent(context, userProfile.class);
                                intent.putExtra("user_id", followersList.get(position).getUserId());
                                context.startActivity(intent);

                            });

                            /*show recycler view, refresh btn, hide progress*/
                            followersTitle.setText("Followers");
                            friendsFollowersList.setVisibility(View.VISIBLE);
                            progressBarFollowers.setVisibility(View.GONE);
                            refreshFollowers.setVisibility(View.VISIBLE);
                        } else {
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
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        /*show refresh btn*/
                        progressBarFollowers.setVisibility(View.GONE);
                        refreshFollowers.setVisibility(View.VISIBLE);

                        /*.....*/
                        followersTitle.setText("Error getting users");
                    }

                    @Override
                    public void onComplete() {
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
}
