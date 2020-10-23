/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui.user_profile;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.onurkagan.ksnack_lib.KSnack.KSnack;
import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.R;
import com.soshoplus.lite.adapters.userPhotosAdapter;
import com.soshoplus.lite.databinding.ActivityUserProfileBinding;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.models.block_unblock;
import com.soshoplus.lite.models.follow_unfollow;
import com.soshoplus.lite.models.postsfeed.post;
import com.soshoplus.lite.models.postsfeed.postList;
import com.soshoplus.lite.models.userprofile.userData;
import com.soshoplus.lite.models.userprofile.userInfo;
import com.soshoplus.lite.utils.queries;
import com.soshoplus.lite.utils.retrofitInstance;
import com.soshoplus.lite.utils.xpopup.adFullImageViewPopup;
import com.soshoplus.lite.utils.xpopup.fullImageViewPopup;

import java.io.File;
import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.transform.CircleCropTransformation;
import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DefaultObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class userProfile extends AppCompatActivity {

    private final static String TAG = "user Profile Calls";
    private static String user_id, followPrivacy, fullName, username;
    private static String fetch_profile = "user_data,family,liked_pages,joined_groups";
    private static String block_action;
    private static String[] normal_menu = {"Poke", "Block", "Add to Family"};
    private static String[] blocked_menu = {"Poke", "Unblock", "Add to Family"};
    /*........*/
    private static String timeAgo, noLikes, noComments;
    private static boolean isLiked;
    /*......*/
    private static String adFullName, adLocation, adDescription, adHeadline;
    private static String userId, timezone, accessToken;
    private static String fetch_profile_user_data = "user_data";
    private ActivityUserProfileBinding userProfileBinding;
    private Observable<userInfo> userInfoObservable;
    /*......*/
    private KSnack snack;
    /*......*/
    private Observable<follow_unfollow> followUnFollowObservable;
    /*.....*/
    private boolean blocked_user;
    private Observable<block_unblock> blockUnblockObservable;
    /*.....*/
    private Observable<postList> postListObservable;
    private List<post> photosList;
    private userPhotosAdapter photosAdapter;
    private ImageLoader imageLoader;
    private queries rxJavaQueries;

    /*.....*/
    public static void getInfo(TextView full_name, TextView time_ago, TextView no_likes,
                               TextView no_comments, MaterialButton like, MaterialButton comment) {
        /*setting up*/
        full_name.setText(fullName);
        time_ago.setText(timeAgo);
        no_likes.setText(noLikes + " likes");
        no_comments.setText(noComments + " comments");

        /*setting like btn*/
        if (isLiked) {
            like.setIconResource(R.drawable.ic_liked);
            like.setText("Liked");
        }
    }

    /*......*/
    public static void getADInfo(TextView full_name, TextView location,
                                 TextView description, TextView headline) {
        /*setting up*/
        full_name.setText(adFullName);
        location.setText(adLocation);
        description.setText(Html.fromHtml(adDescription));
        headline.setText(adHeadline);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userProfileBinding =
                ActivityUserProfileBinding.inflate(getLayoutInflater());
        View view = userProfileBinding.getRoot();
        setContentView(view);


        userId = SecurePreferences.getStringValue(userProfile.this, "userId", "0");
        timezone = SecurePreferences.getStringValue(userProfile.this, "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(userProfile.this, "accessToken", "0");
        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);

        /*setting up actionbar*/
        setSupportActionBar(userProfileBinding.transToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageLoader = Coil.imageLoader(userProfile.this);

        /*initializing ksnack*/
        snack = new KSnack(userProfile.this);

        /*get user id to view profile*/
        Bundle bundle = getIntent().getExtras();
        user_id = bundle.getString("user_id");
        username = bundle.getString("username");

        /*getting user profile data */
        HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed(this::getUserProfile, 500);

        /*refreshing groups*/
        userProfileBinding.profileRefreshLayout.setOnRefreshListener(() -> {
            HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed(this::getUserProfile, 500);
            HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed(this::getUserPhotos, 500);

            userProfileBinding.profileRefreshLayout.setRefreshing(false);
        });
    }

    private void getUserProfile() {

        if (username == null) {
            userInfoObservable = rxJavaQueries.getUserData(accessToken,
                    BuildConfig.server_key,
                    fetch_profile, user_id);
        } else {
            userInfoObservable = rxJavaQueries.getUserDataByUsername(accessToken,
                    BuildConfig.server_key,
                    fetch_profile, username);
        }

        /*making a call to network*/
        userInfoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<userInfo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull userInfo userInfo) {
                        if (userInfo.getApiStatus() == 200) {
                            /*binding user data*/
                            user_id = userInfo.getUserData().getUserId();
                            bindUserInfo(userInfo);

                            /*getting user photos*/
                            HandlerCompat.createAsync(Looper.getMainLooper())
                                    .postDelayed(userProfile.this::getUserPhotos, 500);
                        } else {
                            apiErrors apiErrors = userInfo.getErrors();

                            if (apiErrors.getErrorId().equals("6")) {

                                snack.setMessage("User profile not found");

                                Toast toast = Toast.makeText(userProfile.this, "User profile not found ... ", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();

                                new Handler().postDelayed(() -> onBackPressed(), 2500);
                            } else {
                                snack.setMessage("Oops !\nSomething went " +
                                        "wrong");
                            }
                            snack.setAction("DISMISS", view -> {
                                snack.dismiss();
                            });
                            snack.setDuration(3500);
                            snack.show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
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
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });

    }

    private void bindUserInfo(userInfo userInfo) {
        /*profile _ cover image*/
        /*profile image*/
        ImageRequest imageRequest = new ImageRequest.Builder(userProfile.this)
                .data(userInfo.getUserData().getAvatar())
                .placeholder(R.color.light_grey)
                .crossfade(true)
                .transformations(new CircleCropTransformation())
                .target(userProfileBinding.profilePic)
                .build();
        imageLoader.enqueue(imageRequest);

        /*cover image*/
        imageRequest = new ImageRequest.Builder(userProfile.this)
                .data(userInfo.getUserData().getCover())
                .crossfade(true)
                .placeholder(R.color.dim_gray)
                .target(userProfileBinding.coverPhoto)
                .build();
        imageLoader.enqueue(imageRequest);

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

        followPrivacy = userInfo.getUserData().getConfirmFollowers();

        if (userInfo.getUserData().getCanFollow() == 0 && userInfo.getUserData().getIsFollowing() == 0) {
            userProfileBinding.followBtn.setVisibility(View.GONE);
        } else if (userInfo.getUserData().getIsFollowing() == 0) {
            userProfileBinding.followBtn.setVisibility(View.VISIBLE);
            userProfileBinding.followBtn.setText("Follow");
        } else if (userInfo.getUserData().getIsFollowing() == 2) {
            userProfileBinding.followBtn.setText("Requested");
        } else {  /*(is_following == 1) */
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
    private void followUser(MaterialButton follow,
                            ProgressBar progressBar_follow) {

        followUnFollowObservable = rxJavaQueries.followUser(accessToken,
                BuildConfig.server_key, user_id);

        follow.setText(null);
        progressBar_follow.setVisibility(View.VISIBLE);

        followUnFollowObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<follow_unfollow>() {
                    @Override
                    public void onNext(@NonNull follow_unfollow follow_unfollow) {
                        if (follow_unfollow.getApiStatus() == 200) {

                            Log.d(TAG, "onNext: " + follow_unfollow.getFollowStatus());

                            if (follow_unfollow.getFollowStatus().equals(
                                    "unfollowed")) {

                                progressBar_follow.setVisibility(View.GONE);
                                follow.setText("Follow");
                            } else {
                                if (followPrivacy.equals("1")) {
                                    progressBar_follow.setVisibility(View.GONE);
                                    follow.setText("Requested");
                                } else {
                                    progressBar_follow.setVisibility(View.GONE);
                                    follow.setText("Following");
                                }
                            }
                        } else {
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
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());

                        snack.setMessage("Oops !\nSomething went " +
                                "wrong");
                        snack.setAction("DISMISS", view -> {
                            snack.dismiss();
                        });
                        snack.show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    /*option menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.horz_more_dots, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.options:
                if (blocked_user) {
                    new XPopup.Builder(userProfile.this).asCenterList(null, blocked_menu, (position, text) -> {
                        switch (position) {
                            case 0:
                                Toast toast = Toast.makeText(userProfile.this, "Poke ... ", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();
                                break;
                            case 1:
                                blockUser();
                                break;
                            case 2:
                                Toast _toast = Toast.makeText(userProfile.this, "Add to Family ... ", Toast.LENGTH_LONG);
                                _toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                _toast.show();
                                break;
                        }
                    }).show();
                } else {
                    new XPopup.Builder(userProfile.this).asCenterList(null, normal_menu, (position, text) -> {
                        switch (position) {
                            case 0:
                                Toast toast = Toast.makeText(userProfile.this, "Poke ... ", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();
                                break;
                            case 1:
                                blockUser();
                                break;
                            case 2:
                                Toast _toast = Toast.makeText(userProfile.this, "Add to Family ... ", Toast.LENGTH_LONG);
                                _toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                _toast.show();
                                break;
                        }
                    }).show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void blockUser() {
        blockUnblockObservable = rxJavaQueries.blockUser(accessToken,
                BuildConfig.server_key, user_id, block_action);

        blockUnblockObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<block_unblock>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull block_unblock block_unblock) {
                        if (block_unblock.getApiStatus() == 200) {
                            Log.d(TAG, "onNext: " + block_unblock.getBlockStatus());

                            if (block_unblock.getBlockStatus().equals("blocked")) {
                                blocked_user = true;
                                block_action = "un-block";
                                snack.setMessage("You have blocked " + fullName);

                                Toast toast = Toast.makeText(userProfile.this, "You have blocked " + fullName, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();
                            } else {
                                blocked_user = false;
                                block_action = "block";
                                snack.setMessage(fullName + " un blocked");

                                Toast toast = Toast.makeText(userProfile.this, fullName + " un blocked", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();
                            }
                        } else {
                            apiErrors apiErrors = block_unblock.getErrors();
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
                    public void onError(@NonNull Throwable e) {
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
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void getUserPhotos() {
        postListObservable = rxJavaQueries.getUserImages(accessToken,
                BuildConfig.server_key, user_id, "photos");

        postListObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<postList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull postList postList) {
                        if (postList.getApiStatus() == 200) {

                            Log.d(TAG,
                                    "Images list size: " + postList.getPostList().size());

                            if (postList.getPostList().size() == 0) {
                                /*user has no images*/
                                userProfileBinding.noPhotosImg.setVisibility(View.VISIBLE);
                                userProfileBinding.noPhotosText.setVisibility(View.VISIBLE);
                            } else {
                                photosList = getPhotos(postList);

                                /*wait one sec then bind*/
                                new Handler().postDelayed(() -> {
                                    bindUserImages(photosList);
                                }, 1000);
                            }
                        } else {

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
                    public void onError(@NonNull Throwable e) {
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
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void bindUserImages(List<post> photosList) {
        /*initializing adapter*/
        photosAdapter = new userPhotosAdapter(R.layout.image_layout, photosList);

        /*setting layout*/
        userProfileBinding.photosGrid.setLayoutManager(new StaggeredGridLayoutManager(3,
                1));
        /*set adapter*/
        userProfileBinding.photosGrid.setAdapter(photosAdapter);

        userProfileBinding.progressBarPhotos.setVisibility(View.GONE);
        userProfileBinding.photosGrid.setVisibility(View.VISIBLE);

        /*click listener*/
        photosAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.image) {

                ImageView imageView = view.findViewById(R.id.image);

                if (photosAdapter.getData().get(position).getPostType().equals("ad")) {
                    /*show ad popup*/
                    /*initializing popup*/
                    adFullImageViewPopup imageViewPopup = new adFullImageViewPopup(userProfile.this);
                    /*setting up*/
                    imageViewPopup.setSingleSrcView(imageView, photosAdapter.getData().get(position).getAdMedia());
                    imageViewPopup.isShowSaveButton(false);
                    imageViewPopup.setXPopupImageLoader(new XPopupImageLoader() {
                        @Override
                        public void loadImage(int position, @androidx.annotation.NonNull Object uri,
                                              @androidx.annotation.NonNull ImageView imageView) {

                            ImageRequest imageRequest = new ImageRequest.Builder(userProfile.this)
                                    .data(uri)
                                    .crossfade(true)
                                    .target(imageView)
                                    .build();
                            imageLoader.enqueue(imageRequest);
                        }

                        @Override
                        public File getImageFile(@androidx.annotation.NonNull Context context,
                                                 @androidx.annotation.NonNull Object uri) {
                            return null;
                        }
                    });
                    /*show popup*/
                    new XPopup.Builder(userProfile.this).asCustom(imageViewPopup).show();

                    /*......*/
                    /*Converting Object to json data*/
                    Gson gson = new Gson();
                    String toJson = gson.toJson(photosAdapter.getData().get(position).getUserData());
                    /*getting data from json string using pojo class*/
                    userData user_data = gson.fromJson(toJson, userData.class);

                    adFullName = user_data.getName();
                    adLocation = photosAdapter.getData().get(position).getLocation();
                    adDescription = photosAdapter.getData().get(position).getDescription();
                    adHeadline = photosAdapter.getData().get(position).getHeadline();
                } else {
                    /*initializing popup*/
                    fullImageViewPopup imageViewPopup = new fullImageViewPopup(userProfile.this);
                    /*setting up*/
                    imageViewPopup.setSingleSrcView(imageView, photosAdapter.getData().get(position).getPostFileFull());
                    imageViewPopup.isShowSaveButton(false);
                    imageViewPopup.setXPopupImageLoader(new XPopupImageLoader() {
                        @Override
                        public void loadImage(int position, @androidx.annotation.NonNull Object uri,
                                              @androidx.annotation.NonNull ImageView imageView) {

                            ImageRequest imageRequest = new ImageRequest.Builder(userProfile.this)
                                    .data(uri)
                                    .crossfade(true)
                                    .target(imageView)
                                    .build();
                            imageLoader.enqueue(imageRequest);
                        }

                        @Override
                        public File getImageFile(@androidx.annotation.NonNull Context context,
                                                 @androidx.annotation.NonNull Object uri) {
                            return null;
                        }
                    });
                    /*show popup*/
                    new XPopup.Builder(userProfile.this).asCustom(imageViewPopup).show();
                    /*.......*/
                    fullName = photosAdapter.getData().get(position).getName();
                    timeAgo = photosAdapter.getData().get(position).getPostTime();
                    noLikes = photosAdapter.getData().get(position).getPostLikes();
                    noComments = photosAdapter.getData().get(position).getPostComments();
                    isLiked = photosAdapter.getData().get(position).isLiked();
                }
            }
        });
    }

    /*getting photos*/
    private List<post> getPhotos(postList postList) {
        photosList = postList.getPostList();
        return photosList != null ? postList.getPostList() : null;
    }
}