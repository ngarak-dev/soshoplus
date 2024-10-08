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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.R;
import com.soshoplus.lite.adapters.joinedGroupsAdapter;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.models.groups.groupInfo;
import com.soshoplus.lite.models.groups.groupList;
import com.soshoplus.lite.ui.groups.viewGroup;
import com.soshoplus.lite.utils.constants;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class joinedGroupsCalls {

    private final static String TAG = "Joined groups Calls";
    private static String joined_groups = "joined_groups";
    private Context context;
    private Observable<groupList> groupListObservable;
    private List<groupInfo> groupInfoList = null;

    public joinedGroupsCalls(Context context) {
        this.context = context;
    }


    public void getJoined(RecyclerView joinedGroupsList, ProgressBar progressBarJoined,
                          ImageView joinedGroupsShowHereImg, TextView joinedGroupsShowHereTxt) {

        groupListObservable = constants.rxJavaQueries.getJoinedGroups(constants.accessToken,
                BuildConfig.server_key, joined_groups, constants.userId);
        groupListObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<groupList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull groupList groupList) {
                        if (groupList.getApiStatus() == 200) {

                            /*initializing list*/
                            groupInfoList = groupList.getInfo();

                            /*initializing adapter*/
                            joinedGroupsAdapter groupsAdapter = new joinedGroupsAdapter(R.layout.joined_groups_row, groupInfoList);

                            /*Setting Adapter*/
                            joinedGroupsList.setAdapter(groupsAdapter);

                            /*......*/
                            /*After fetching Data*/
                            updateUI();

                            groupsAdapter.setOnItemClickListener((adapter, view, position) -> {
                                Intent intent = new Intent(context, viewGroup.class);
                                intent.putExtra("group_id", groupsAdapter.getData().get(position).getGroupId());
                                intent.putExtra("no_members", groupsAdapter.getData().get(position).getMembers());
                                intent.putExtra("group_info", groupsAdapter.getData().get(position).getAbout());
                                intent.putExtra("group_url", groupsAdapter.getData().get(position).getUrl());
                                intent.putExtra("is_joined", true);
                                context.startActivity(intent);
                            });
                        } else {
                            apiErrors apiErrors = groupList.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        }
                    }

                    private void updateUI() {
                        if (groupInfoList.size() == 0) {
                            /*Show all set*/
                            joinedGroupsList.setVisibility(View.GONE);
                            progressBarJoined.setVisibility(View.GONE);

                            joinedGroupsShowHereImg.setVisibility(View.VISIBLE);
                            joinedGroupsShowHereTxt.setVisibility(View.VISIBLE);

                        } else {
                            /*Show recyclerview*/
                            joinedGroupsShowHereImg.setVisibility(View.GONE);
                            joinedGroupsShowHereTxt.setVisibility(View.GONE);

                            progressBarJoined.setVisibility(View.GONE);
                            joinedGroupsList.setVisibility(View.VISIBLE);
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
