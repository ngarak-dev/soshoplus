/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.user_profile;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.onurkagan.ksnack_lib.KSnack.KSnack;
import com.soshoplus.timeline.BuildConfig;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.databinding.ActivityUserProfileBinding;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.follow_unfollow;
import com.soshoplus.timeline.models.userprofile.userInfo;
import com.soshoplus.timeline.utils.glide.glideImageLoader;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DefaultObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class userProfile extends AppCompatActivity {
    
    private ActivityUserProfileBinding userProfileBinding;
    private queries rxJavaQueries;
    private String accessToken, userId, timezone;
    private static String user_id, followPrivacy;
    private Observable<userInfo> userInfoObservable;
    private static String fetch_profile = "user_data,family,liked_pages,joined_groups";
    private final static String TAG = "user Profile Calls";
    
    /*GLIDE OPTIONS*/
    RequestOptions options = new RequestOptions()
            .format(DecodeFormat.PREFER_RGB_565)
            .error(R.drawable.ic_image_placeholder)
            .priority(Priority.LOW);
    
    /*......*/
    private KSnack snack;
    
    /*......*/
    private Observable<follow_unfollow> followUnFollowObservable;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userProfileBinding =
                ActivityUserProfileBinding.inflate(getLayoutInflater());
        View view = userProfileBinding.getRoot();
        setContentView(view);
        
        /*setting up actionbar*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        /*initializing ksnack*/
        snack = new KSnack(userProfile.this);

        /*getting user id & access token*/
        userId = SecurePreferences.getStringValue(userProfile.this, "userId",
                "0");
        timezone = SecurePreferences.getStringValue(userProfile.this,
                "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(userProfile.this,
                "accessToken"
                , "0");

        /*get user id to view profile*/
        Bundle bundle = getIntent().getExtras();
        user_id  = bundle.getString("user_id");

        /*getting user profile data */
        getUserProfile();
    }
    
    private void getUserProfile () {
    
        /*initializing retrofit instance*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);
    
        userInfoObservable = rxJavaQueries.getUserData(accessToken,
                BuildConfig.server_key,
                fetch_profile, user_id);
    
        /*making a call to network*/
        userInfoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<userInfo>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
                
                    @Override
                    public void onNext (@NonNull userInfo userInfo) {
                        /*binding user data*/
                        bindUserInfo(userInfo);
                    }
                
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        
                        snack.setMessage("Oops !\nSomething went " +
                                "wrong");
                        snack.setAction("DISMISS", view -> {
                            snack.dismiss();
                        });
                        snack.setDuration(3500);
                        snack.show();
                    }
                
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
    
    private void bindUserInfo (userInfo userInfo) {
        /*profile _ cover image*/
        /*profile image*/
        new glideImageLoader(userProfileBinding.profilePic,
                userProfileBinding.progressBarProfilePic).load(userInfo.getUserData().getAvatar(),
                RequestOptions.circleCropTransform().apply(options));

        /*cover image*/
        new glideImageLoader(userProfileBinding.coverPhoto, userProfileBinding.progressBarCoverPhoto)
                .load(userInfo.getUserData().getCover(), options);

        /*name*/
        userProfileBinding.fullName.setText(userInfo.getUserData().getName());

        /*verification status*/
        if (userInfo.getUserData().getVerified().equals("1")) {
            userProfileBinding.verifiedBadge.setVisibility(View.VISIBLE);
        }

        /*country / state*/
        if (!userInfo.getUserData().getAddress().isEmpty()) {
            userProfileBinding.country.setText(userInfo.getUserData().getAddress());
        }

        /*counts*/
        userProfileBinding.noOfPosts.setText(userInfo.getUserData().getDetails().getPostCount());
        userProfileBinding.numberOfFollowers.setText(userInfo.getUserData().getDetails().getFollowersCount());
        userProfileBinding.numberOfFollowing.setText(userInfo.getUserData().getDetails().getFollowingCount());

        /*Follow privacy is_following
        * 0 = not following
        * 1 = following
        * 2 = requested*/
    
        followPrivacy =
                userInfo.getUserData().getConfirmFollowers();

        if (userInfo.getUserData().getCanFollow() == 0 && userInfo.getUserData().getIsFollowing() == 0) {
            userProfileBinding.followBtn.setVisibility(View.GONE);
        } else if (userInfo.getUserData().getIsFollowing() == 0) {
            userProfileBinding.followBtn.setVisibility(View.VISIBLE);
            userProfileBinding.followBtn.setText("Follow");
        }

        else if (userInfo.getUserData().getIsFollowing() == 2) {
            userProfileBinding.followBtn.setText("Requested");
        }
        else {  /*(is_following == 1) */
            userProfileBinding.followBtn.setText("Following");
        }

        /*about info*/
        if (userInfo.getUserData().getAbout() != null) {
            userProfileBinding.aboutMe.setText(Html.fromHtml(userInfo.getUserData().getAbout()));
        } else {
            userProfileBinding.aboutMe.setText("Hello there I am using soshoplus");
        }

        /*general info*/
        userProfileBinding.gender.setText(userInfo.getUserData().getGenderText());
        userProfileBinding.birthday.setText(userInfo.getUserData().getBirthday());
        userProfileBinding.workingAt.append(userInfo.getUserData().getWorking());
        userProfileBinding.school.append(userInfo.getUserData().getSchool());
        userProfileBinding.living.append(userInfo.getUserData().getAddress());
        userProfileBinding.located.append(userInfo.getUserData().getCity());
        /*show above info*/
        userProfileBinding.moreAbout.setVisibility(View.VISIBLE);
        
        /*follow btn*/
        userProfileBinding.followBtn.setOnClickListener(view -> {
            /*follow user*/
            followUser(userProfileBinding.followBtn,
                    userProfileBinding.progressBarFollow);
        });
    }
    
    /*follow user*/
    private void followUser (MaterialButton follow,
                             ProgressBar progressBar_follow) {
    
        followUnFollowObservable = rxJavaQueries.followUser(accessToken,
                BuildConfig.server_key, user_id);
    
        follow.setText(null);
        progressBar_follow.setVisibility(View.VISIBLE);
    
        followUnFollowObservable.subscribeOn(Schedulers.io())
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