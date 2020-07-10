/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.calls;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.soshoplus.timeline.BuildConfig;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.userprofile.userInfo;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class simpleProfileCalls {
    
    private final static String TAG = "Simple Calls";
    /*context*/
    private Context context;
    /*......*/
    private queries rxJavaQueries;
    private String accessToken, userId, timezone;
    
    /*........*/
    private Observable<userInfo> userInfoObservable;
    private static String fetch_profile = "user_data";
    
    public simpleProfileCalls (Context context) {
        this.context = context;
    
        userId = SecurePreferences.getStringValue(context, "userId", "0");
        timezone = SecurePreferences.getStringValue(context, "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(context, "accessToken"
                , "0");
    
        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);
    }
    
    public void getProfile (ShapeableImageView profile_pic, TextView full_name,
                            TextView username) {
    
        userInfoObservable = rxJavaQueries.getUserData(accessToken,
                BuildConfig.server_key,
                fetch_profile, userId);
    
        userInfoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<userInfo>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
                
                    @Override
                    public void onNext (@NonNull userInfo userInfo) {
                        if(userInfo.getApiStatus() == 200) {
                            full_name.setText(userInfo.getUserData().getName());
                            username.setText("@" + userInfo.getUserData().getUsername());
                            //profile pic
                            profile_pic.setShapeAppearanceModel(profile_pic
                                    .getShapeAppearanceModel()
                                    .toBuilder()
                                    .setAllCorners(CornerFamily.ROUNDED, 20)
                                    .build());
                            Glide.with(context).load(userInfo.getUserData().getAvatar()).placeholder(R.drawable.ic_image_placeholder).thumbnail(0.5f).into(profile_pic);
                        }
                        else {
                            profile_pic.setImageResource(R.drawable.ic_image_placeholder);
                            apiErrors apiErrors =userInfo.getErrors();
                            Log.d(TAG, "main activity profile: " + apiErrors.getErrorId());
                            Log.d(TAG, "main activity profile: " + apiErrors.getErrorText());
                        }
                    }
                
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
