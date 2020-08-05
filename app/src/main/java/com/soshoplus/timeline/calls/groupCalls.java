/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.calls;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.button.MaterialButton;
import com.soshoplus.timeline.BuildConfig;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.groups.group;
import com.soshoplus.timeline.models.groups.groupInfo;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class groupCalls {

    private final static String TAG = "Group Calls";
    /*context*/
    private Context context;
    /*......*/
    private queries rxJavaQueries;
    private String accessToken, userId, timezone;
    /*.......*/
    private Observable<group> groupInfoObservable;

    public groupCalls(Context context) {
        this.context = context;

        userId = SecurePreferences.getStringValue(context, "userId", "0");
        timezone = SecurePreferences.getStringValue(context, "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(context, "accessToken"
                , "0");

        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);
    }

    public void getGroupInfo(SimpleDraweeView groupProfilePic, SimpleDraweeView groupCover,
                             TextView noMembers, TextView groupPrivacy, TextView groupCategory, String group_id,
                             String no_members, MaterialButton joinBtn) {

        groupInfoObservable = rxJavaQueries.getGroupInfo(accessToken, BuildConfig.server_key, group_id);

        groupInfoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<group>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull group group) {
                        if (group.getApiStatus() == 200) {
                            groupProfilePic.setImageURI(group.getGroupInfo().getAvatar());
                            groupCover.setImageURI(group.getGroupInfo().getCover());

                            noMembers.setText(no_members + " Members");
                            groupCategory.setText(group.getGroupInfo().getCategory());

                            if (group.getGroupInfo().getPrivacy().equals("1")) {
                                groupPrivacy.setText("Public group");
                            } else {
                                groupPrivacy.setText("Private group");
                            }

                            if (!group.getGroupInfo().isIsJoined()) {
                                joinBtn.setVisibility(View.VISIBLE);
                            }
                        }

                        else {
                            apiErrors errors = group.getErrors();
                            Log.d(TAG, "Error: " + errors.getErrorId());
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
