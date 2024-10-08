/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.calls;

import android.content.Context;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.os.HandlerCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.button.MaterialButton;
import com.onurkagan.ksnack_lib.KSnack.KSnack;
import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.R;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.models.follow_unfollow;
import com.soshoplus.lite.models.userprofile.userInfo;
import com.soshoplus.lite.utils.constants;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.transform.CircleCropTransformation;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DefaultObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class previewProfileCalls {

    private final static String TAG = "Preview profile Calls";
    private static String fetch_profile = "user_data,family,liked_pages,joined_groups";
    /*PREVIEW PROFILE*/
    private static String followPrivacy;
    private Context context;
    private Observable<userInfo> userInfoObservable;
    /*.......*/
    private KSnack snack;
    private Observable<follow_unfollow> followUnfollowObservable;

    /*constructor*/
    public previewProfileCalls(Context context) {
        this.context = context;
    }

    public void previewProfile(ImageView cover_photo, ImageView profile_pic,
                               TextView name, ImageView verified_badge,
                               ImageView level_badge, TextView no_followers, TextView no_following,
                               MaterialButton follow, TextView about, ProgressBar progressBar_follow,
                               String user_id, TextView follows_me) {

        userInfoObservable = constants.rxJavaQueries.getUserData(constants.accessToken,
                BuildConfig.server_key,
                fetch_profile, user_id);

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
    
                            /*follow privacy
                            0 - moja kwa moja
                            1 - conform first*/

                            followPrivacy = userInfo.getUserData().getConfirmFollowers();

                            HandlerCompat.createAsync(Looper.getMainLooper()).post(() -> {

                                name.setText(userInfo.getUserData().getName());
                                no_followers.setText(userInfo.getUserData().getDetails().getFollowersCount() + " followers");
                                no_following.setText(userInfo.getUserData().getDetails().getFollowingCount() + " following");

                                if (userInfo.getUserData().getAbout() != null) {
                                    about.setText(Html.fromHtml(userInfo.getUserData().getAbout()));
                                } else {
                                    about.setText("Hey there I am using soshoplus");
                                }

                                if (userInfo.getUserData().getVerified().equals("1")) {
                                    verified_badge.setVisibility(View.VISIBLE);
                                }

                                switch (userInfo.getUserData().getProType()) {
                                    case "1":
                                        level_badge.setImageResource(R.drawable.ic_star_badge);
                                        level_badge.setVisibility(View.VISIBLE);
                                        break;
                                    case "2":
                                        level_badge.setImageResource(R.drawable.ic_hot_badge);
                                        level_badge.setVisibility(View.VISIBLE);
                                        break;
                                    case "3":
                                        level_badge.setImageResource(R.drawable.ic_ultima_badge);
                                        level_badge.setVisibility(View.VISIBLE);
                                        break;
                                    case "4":
                                        level_badge.setImageResource(R.drawable.ic_pro_badge);
                                        level_badge.setVisibility(View.VISIBLE);
                                        break;
                                    case "0":
                                    default:
                                        level_badge.setVisibility(View.GONE);
                                }

                            });
                            
                            /*Follow privacy
                              is_following
                            * 0 = not following
                            * 1 = following
                            * 2 = requested*/

                            HandlerCompat.createAsync(Looper.getMainLooper()).post(() -> {
                                if (userInfo.getUserData().getCanFollow() == 0 && userInfo.getUserData().getIsFollowing() == 0) {
                                    follow.setVisibility(View.GONE);
                                } else if (userInfo.getUserData().getIsFollowing() == 0) {
                                    follow.setVisibility(View.VISIBLE);
                                    follow.setText("Follow");
                                } else if (userInfo.getUserData().getIsFollowing() == 2) {
                                    follow.setText("Requested");
                                } else {  /*(is_following == 1) */
                                    follow.setText("Following");
                                }

                                if (userInfo.getUserData().getIsFollowingMe() == 1) {
                                    follows_me.setText("Follows you");
                                }

                                /*profile*/
                                ImageLoader imageLoader = Coil.imageLoader(context);
                                ImageRequest imageRequest = new ImageRequest.Builder(context)
                                        .data(userInfo.getUserData().getAvatar())
                                        .crossfade(true)
                                        .transformations(new CircleCropTransformation())
                                        .target(profile_pic)
                                        .build();
                                imageLoader.enqueue(imageRequest);

                                /*cover*/
                                imageRequest = new ImageRequest.Builder(context)
                                        .data(userInfo.getUserData().getCover())
                                        .placeholder(R.color.light_grey)
                                        .crossfade(true)
                                        .target(cover_photo)
                                        .build();
                                imageLoader.enqueue(imageRequest);
                            });
                        } else {
                            apiErrors apiErrors = userInfo.getErrors();
                            Log.d(TAG, "main activity profile: " + apiErrors.getErrorText());

                            snack = new KSnack((FragmentActivity) context);
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

                        snack = new KSnack((FragmentActivity) context);
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

        follow.setOnClickListener(view -> {
            /*follow user*/
            HandlerCompat.createAsync(Looper.getMainLooper()).post(() -> {
                followUser(follow, progressBar_follow, user_id);
            });
        });

    }

    private void followUser(MaterialButton follow, ProgressBar progressBar_follow, String user_id) {

        followUnfollowObservable = constants.rxJavaQueries.followUser(constants.accessToken,
                BuildConfig.server_key, user_id);

        /*set text null
         * show progress*/
        follow.setText(null);
        progressBar_follow.setVisibility(View.VISIBLE);

        followUnfollowObservable.subscribeOn(Schedulers.io())
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

                            snack = new KSnack((FragmentActivity) context);
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

                        snack = new KSnack((FragmentActivity) context);
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
}
