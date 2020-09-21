/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.calls;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.R;
import com.soshoplus.lite.models.apiErrors;
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
import io.reactivex.rxjava3.schedulers.Schedulers;

public class simpleProfileCalls {

    private final static String TAG = "Simple Calls";
    private static String fetch_profile = "user_data";
    /*context*/
    private Context context;
    /*........*/
    private Observable<userInfo> userInfoObservable;

    public simpleProfileCalls(Context context) {
        this.context = context;
    }

    public void getProfile(ImageView profilePic, TextView fullName, TextView userEmail) {

        userInfoObservable = constants.rxJavaQueries.getUserData(constants.accessToken,
                BuildConfig.server_key,
                fetch_profile, constants.userId);

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
                            fullName.setText(userInfo.getUserData().getName());
                            userEmail.setText(userInfo.getUserData().getEmail());

                            ImageLoader imageLoader = Coil.imageLoader(context);
                            ImageRequest imageRequest = new ImageRequest.Builder(context)
                                    .data(userInfo.getUserData().getAvatar())
                                    .placeholder(R.color.light_grey)
                                    .crossfade(true)
                                    .transformations(new CircleCropTransformation())
                                    .target(profilePic)
                                    .build();
                            imageLoader.enqueue(imageRequest);
                        } else {
                            apiErrors apiErrors = userInfo.getErrors();
                            Log.d(TAG, "main activity profile: " + apiErrors.getErrorId());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
