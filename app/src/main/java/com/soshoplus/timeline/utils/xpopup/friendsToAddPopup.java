/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils.xpopup;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.onurkagan.ksnack_lib.KSnack.KSnack;
import com.soshoplus.timeline.BuildConfig;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.adapters.friendsToAddToGroupAdapter;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.friends.friends;
import com.soshoplus.timeline.models.groups.addingUser;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class friendsToAddPopup extends FullScreenPopupView {

    private static String TAG = "Friends popup";

    /*......*/
    private queries rxJavaQueries;
    private String accessToken, userId, timezone, group_id;
    private Observable<friends> friendsObservable;
    private static String friends_list = "followers,following";
    private KSnack snack;
    /*......*/
    private RecyclerView friendsRV;
    /*.........*/
    private Observable<addingUser> addingUserObservable;

    public friendsToAddPopup(@NonNull Context context, String groupId) {
        super(context);
        group_id = groupId;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.friends_to_add_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        friendsRV = findViewById(R.id.friends_to_add_list);
        MaterialButton back = findViewById(R.id.back_arrow);

        userId = SecurePreferences.getStringValue(getContext(), "userId", "0");
        timezone = SecurePreferences.getStringValue(getContext(), "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(getContext(), "accessToken"
                , "0");

        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);

        /*get friendsToAdd*/
        new Handler().postDelayed(this::geFriendsToAdd, 1000);

        back.setOnClickListener(view -> {
            smartDismiss();
        });
    }

    private void geFriendsToAdd() {

        friendsObservable = rxJavaQueries.getFriendsFollowing(accessToken,
                BuildConfig.server_key, friends_list, userId, "12");

        friendsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<friends>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull friends friends) {
                        if (friends.getApiStatus() == 200) {

                            /*initializing adapter*/
                            friendsToAddToGroupAdapter addToGroupAdapter =
                                    new friendsToAddToGroupAdapter(R.layout.friends_to_add_to_group,
                                            friends.getFriendsList().getFollowers());

                            /*Setting Layout*/
                            friendsRV.setLayoutManager(new GridLayoutManager(getContext(), 3));

                            /*Setting Adapter*/
                            friendsRV.setAdapter(addToGroupAdapter);
                            friendsRV.setVisibility(View.VISIBLE);

                            addToGroupAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                                if (view.getId() == R.id.add_to_group) {

                                    MaterialButton addToGroup = view.findViewById(R.id.add_to_group);
                                    addToGroup.setText("....");

                                    new Handler().postDelayed(() -> {
                                        addUserToGroup(addToGroupAdapter.getData().get(position).getUserId(), addToGroup);
                                    }, 300);
                                }
                            });

                        } else {
                            apiErrors apiErrors = friends.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        }
            }

            private void addUserToGroup(String userId, MaterialButton addToGroup) {
                addingUserObservable = rxJavaQueries.addMemberToGroup(accessToken, BuildConfig.server_key, group_id, userId);

                addingUserObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<addingUser>() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                                Log.d(TAG, "onSubscribe: ");
                            }

                            @Override
                            public void onNext(@io.reactivex.rxjava3.annotations.NonNull addingUser addingUser) {
                                if (addingUser.getApiStatus() == 200) {

                                    addToGroup.setEnabled(false);
                                    addToGroup.setText("Added");

                                    snack = new KSnack((FragmentActivity) getContext());
                                    snack.setMessage("Friend added to group");

                                } else {
                                    snack = new KSnack((FragmentActivity) getContext());
                                    snack.setMessage("User is already in a group");
                                }
                                snack.show();
                                snack.setDuration(3000);
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                addToGroup.setEnabled(true);
                                Log.d(TAG, "onError: ");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete: ");
                            }
                        });
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    @Override
    protected int getMaxWidth () {
        return super.getMaxWidth();
    }

    @Override
    protected int getMaxHeight () {
        return super.getMaxHeight();
    }

    @Override
    protected int getPopupWidth () {
        return 0;
    }

    @Override
    protected int getPopupHeight () {
        return 0;
    }
}
