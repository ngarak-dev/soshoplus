/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.user_profile;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.button.MaterialButton;
import com.onurkagan.ksnack_lib.KSnack.KSnack;
import com.soshoplus.timeline.BuildConfig;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.adapters.userPhotosAdapter;
import com.soshoplus.timeline.databinding.ActivityUserProfileBinding;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.block_unblock;
import com.soshoplus.timeline.models.follow_unfollow;
import com.soshoplus.timeline.models.postsfeed.post;
import com.soshoplus.timeline.models.postsfeed.postList;
import com.soshoplus.timeline.models.userprofile.userInfo;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;

import java.util.List;

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
    private static String accessToken, userId, timezone;
    private static String user_id, followPrivacy, fullName;
    private Observable<userInfo> userInfoObservable;
    private static String fetch_profile = "user_data,family,liked_pages,joined_groups";
    private final static String TAG = "user Profile Calls";
    
    /*......*/
    private KSnack snack;
    /*......*/
    private Observable<follow_unfollow> followUnFollowObservable;
    /*.....*/
    private boolean blocked_user;
    private static String block_action;
    private Observable<block_unblock> blockUnblockObservable;
    /*.....*/
    private Observable<postList> postListObservable;
    private List<post> photosList;
    private userPhotosAdapter photosAdapter;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userProfileBinding =
                ActivityUserProfileBinding.inflate(getLayoutInflater());
        View view = userProfileBinding.getRoot();
        setContentView(view);
        
        /*setting up actionbar*/
        setSupportActionBar(userProfileBinding.transToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
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
        HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed(this::getUserProfile, 500);

        /*getting user photos*/
        HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed(this::getUserPhotos, 500);

        /*refreshing groups*/
        userProfileBinding.profileRefreshLayout.setOnRefreshListener(() -> {
            HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed(this::getUserProfile, 500);
            HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed(this::getUserPhotos, 500);

            userProfileBinding.profileRefreshLayout.setRefreshing(false);
        });
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
                        if (userInfo.getApiStatus() == 200) {
                            /*binding user data*/
                            bindUserInfo(userInfo);
                        }
                        else {
                            apiErrors apiErrors =userInfo.getErrors();
                            Log.d(TAG, "onNext: " + apiErrors.getErrorText());
                        
                            snack.setMessage("Oops !\nSomething went " +
                                    "wrong");
                            snack.setAction("DISMISS", view -> {
                                snack.dismiss();
                            });
                            snack.setDuration(3500);
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
        userProfileBinding.profilePic.setImageURI(userInfo.getUserData().getAvatar());

        /*cover image*/
        userProfileBinding.coverPhoto.setImageURI(userInfo.getUserData().getCover());

        /*name*/
        userProfileBinding.fullName.setText(userInfo.getUserData().getName());

        /*verification status*/
        if (userInfo.getUserData().getVerified().equals("1")) {
            userProfileBinding.verifiedBadge.setVisibility(View.VISIBLE);
        }

        /*country / state*/
        if (userInfo.getUserData().getAddress().isEmpty()) {
            userProfileBinding.country.setVisibility(View.GONE);
        } else {
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
        
        if (userInfo.getUserData().getWorking().isEmpty()) {
            userProfileBinding.workingAt.setVisibility(View.GONE);
        } else {
            userProfileBinding.workingAt.append(userInfo.getUserData().getWorking());
        }
        
        if (userInfo.getUserData().getSchool().isEmpty()) {
            userProfileBinding.school.setVisibility(View.GONE);
        } else {
            userProfileBinding.school.append(userInfo.getUserData().getSchool());
        }
        
        if (userInfo.getUserData().getAddress().isEmpty()) {
            userProfileBinding.living.setVisibility(View.GONE);
        } else {
            userProfileBinding.living.append(userInfo.getUserData().getAddress());
        }
        
        if (userInfo.getUserData().getCity().isEmpty()) {
            userProfileBinding.located.setVisibility(View.GONE);
        } else {
            userProfileBinding.located.append(userInfo.getUserData().getCity());
        }
        /*show above info*/
        userProfileBinding.moreAbout.setVisibility(View.VISIBLE);
        
        /*follow btn*/
        userProfileBinding.followBtn.setOnClickListener(view -> {
            /*follow user*/
            followUser(userProfileBinding.followBtn,
                    userProfileBinding.progressBarFollow);
        });
        
        /*get blocking info*/
        blocked_user = userInfo.getUserData().isIsBlocked();
        
        /*set block action*/
        if (!blocked_user) {
            block_action = "block";
        } else {
            block_action = "un-block";
        }
        
        fullName = userInfo.getUserData().getName();
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
    
    /*option menu*/
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.user_profile, menu);
    
        Log.d(TAG, "onCreateOptionsMenu: " + blocked_user);
        
        if (blocked_user) {
            menu.findItem(R.id.block_user).setChecked(true);
            menu.findItem(R.id.block_user).setIcon(R.drawable.ic_blocked_user);
            menu.findItem(R.id.block_user).setTitle("Unblock");
        } else {
            menu.findItem(R.id.block_user).setChecked(true);
            menu.findItem(R.id.block_user).setIcon(R.drawable.ic_remove_user);
            menu.findItem(R.id.block_user).setTitle("Block");
        }
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected (@androidx.annotation.NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
                
            case R.id.poke_user:
                Toast.makeText(this, "Poke user", Toast.LENGTH_SHORT).show();
                return true;
                
            case R.id.add_to_family:
                Toast.makeText(this, "Add to Family", Toast.LENGTH_SHORT).show();
                return true;
                
            case R.id.block_user:
                blockUser(item);
                return true;
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private void blockUser (MenuItem item) {
        blockUnblockObservable = rxJavaQueries.blockUser(accessToken,
                BuildConfig.server_key, user_id, block_action);
        
        blockUnblockObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<block_unblock>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
    
                    @Override
                    public void onNext (@NonNull block_unblock block_unblock) {
                        if (block_unblock.getApiStatus() == 200) {
    
                            Log.d(TAG, "onNext: " + block_unblock.getBlockStatus());
                            
                            if (block_unblock.getBlockStatus().equals(
                                    "blocked")) {
                                item.setIcon(R.drawable.ic_blocked_user);
                                item.setTitle("Unblock");
                                
                                block_action = "un-block";
                                snack.setMessage(fullName + " blocked");
                            }
                            else {
                                item.setIcon(R.drawable.ic_remove_user);
                                item.setTitle("Block");
                                
                                block_action = "block";
                                snack.setMessage(fullName + " un blocked");
                            }
    
                        }
                        else {
                            apiErrors apiErrors =block_unblock.getErrors();
                            Log.d(TAG, "onNext: " + apiErrors.getErrorText());
    
                            snack.setMessage("Oops !\nSomething went " +
                                    "wrong");
                            snack.setAction("DISMISS", view -> {
                                snack.dismiss();
                            });
                        }
                        
                        snack.setDuration(3500);
                        snack.show();
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
    
    private void getUserPhotos () {
        postListObservable = rxJavaQueries.getUserImages(accessToken,
                BuildConfig.server_key, user_id, "photos");
        
        postListObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<postList>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
    
                    @Override
                    public void onNext (@NonNull postList postList) {
                        if (postList.getApiStatus() == 200) {
    
                            Log.d(TAG,
                                    "Images list size: " + postList.getPostList().size());
                            
                            if (postList.getPostList().size() == 0) {
                                /*user has no images*/
                                userProfileBinding.noPhotosImg.setVisibility(View.VISIBLE);
                                userProfileBinding.noPhotosText.setVisibility(View.VISIBLE);
                            }
                            else {
                                photosList = getPhotos(postList);
    
                                /*wait one sec then bind*/
                                new Handler().postDelayed(() -> {
                                    bindUserImages(photosList);
                                }, 1000);
                            }
                        }
                        else {
    
                            apiErrors errors = postList.getErrors();
                            Log.d(TAG, "ERROR FROM API : " + errors.getErrorText());

                            snack.setMessage("Oops !\nSomething went " +
                                    "wrong\nPlease check your internet " +
                                    "connection");
                            snack.setAction("TRY AGAIN", view -> {
                                snack.dismiss();
                                /*call photos api again*/
                                getUserPhotos();
                            });
                            snack.setDuration(3500);
                            snack.show();
                            
                        }
                    }
    
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        
                        snack.setMessage("Failed to load Images !\nCheck back" +
                                " later");
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
    
    private void bindUserImages (List<post> photosList) {
        /*initializing adapter*/
        photosAdapter = new userPhotosAdapter(R.layout.image_layout, photosList);
        
        /*setting layout*/
        userProfileBinding.photosGrid.setLayoutManager(new StaggeredGridLayoutManager(3,
                1));
        /*set adapter*/
        userProfileBinding.photosGrid.setAdapter(photosAdapter);
        
        userProfileBinding.progressBarPhotos.setVisibility(View.GONE);
        userProfileBinding.photosGrid.setVisibility(View.VISIBLE);
    }
    
    /*getting photos*/
    private List<post> getPhotos (postList postList) {
        photosList=  postList.getPostList();
        return photosList != null ? postList.getPostList(): null;
    }
    
//    /*.....*/
//    public static void getInfo (TextView full_name, TextView time_ago, TextView no_likes,
//                                TextView no_comments, MaterialButton like, MaterialButton comment) {
//
//        /*setting up*/
//        full_name.setText(fullName);
//        time_ago.setText(timeAgo);
//        no_likes.setText(noLikes + " likes");
//        no_comments.setText(noComments + " comments");
//
//        /*setting like btn*/
//        if (isLiked) {
//            like.setIconResource(R.drawable.ic_liked);
//            like.setText("Liked");
//        }
//    }
//
//    /*......*/
//    public static void getADInfo (TextView full_name, TextView location,
//                                  TextView description, TextView headline) {
//
//        /*setting up*/
//        full_name.setText(adFullName);
//        location.setText(adLocation);
//        description.setText(Html.fromHtml(adDescription));
//        headline.setText(adHeadline);
//    }
}