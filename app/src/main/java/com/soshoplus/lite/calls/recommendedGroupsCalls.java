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

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.onurkagan.ksnack_lib.KSnack.KSnack;
import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.R;
import com.soshoplus.lite.adapters.suggestedGroupsAdapter;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.models.groups.groupInfo;
import com.soshoplus.lite.models.groups.groupList;
import com.soshoplus.lite.models.groups.join.join_unjoin;
import com.soshoplus.lite.ui.groups.viewGroup;
import com.soshoplus.lite.utils.constants;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class recommendedGroupsCalls {

    private static String TAG = "Recommended groups Calls";
    /*......*/
    private static String fetch_recommended = "groups";
    private Context context;
    /*ADAPTERS*/
    private suggestedGroupsAdapter suggested_groups_adapter;
    private Observable<groupList> groupListObservable;
    private List<groupInfo> groupInfoList = null;

    /*JOIN GROUP*/
    private Observable<join_unjoin> joinUnjoinObservable;

    /*......*/
    private KSnack snack;

    public recommendedGroupsCalls(Context context) {
        this.context = context;
        /*initializing snack*/
        snack = new KSnack((FragmentActivity) context);
    }

    public void getRecommends(RecyclerView suggestedGroupsList, ImageView allSetUpImg,
                              TextView allSetUpText, ProgressBar progressBarSuggested) {

        groupListObservable = constants.rxJavaQueries.getRecommended(constants.accessToken,
                BuildConfig.server_key, fetch_recommended, "5");
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
                            suggested_groups_adapter = new suggestedGroupsAdapter(R.layout.suggested_group_list_row, groupInfoList);

                            /*Setting Layout*/
                            suggestedGroupsList.setLayoutManager(new LinearLayoutManager(context));

                            /*Setting Adapter*/
                            suggestedGroupsList.setAdapter(suggested_groups_adapter);

                            /*......*/
                            /*After fetching Data*/
                            updateUI();

                            suggested_groups_adapter.setOnItemClickListener((adapter, view, position) -> {
                                Intent intent = new Intent(context, viewGroup.class);
                                intent.putExtra("group_id", suggested_groups_adapter.getData().get(position).getGroupId());
                                intent.putExtra("no_members", suggested_groups_adapter.getData().get(position).getMembers());
                                intent.putExtra("group_info", suggested_groups_adapter.getData().get(position).getAbout());
                                intent.putExtra("group_url", suggested_groups_adapter.getData().get(position).getUrl());
                                intent.putExtra("is_joined", false);
                                context.startActivity(intent);
                            });

                            suggested_groups_adapter.setOnItemChildClickListener((adapter, view, position) -> {

                                MaterialButton is_joined = view.findViewById(R.id.btn_join);

                                if (view.getId() == R.id.btn_join) {

                                    is_joined.setText(null);
                                    adapter.getViewByPosition(position, R.id.progressBar_join)
                                            .setVisibility(View.VISIBLE);

                                    joinUnjoinObservable =
                                            constants.rxJavaQueries.joinGroup(constants.accessToken, BuildConfig.server_key,
                                                    suggested_groups_adapter.getData().get(position).getGroupId());

                                    joinUnjoinObservable.subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<join_unjoin>() {
                                                @Override
                                                public void onSubscribe(@NonNull Disposable d) {
                                                    Log.d(TAG, "onSubscribe: ");
                                                }

                                                @Override
                                                public void onNext(@NonNull join_unjoin join_unjoin) {
                                                    if (join_unjoin.getApiStatus() == 200) {

                                                        adapter.getViewByPosition(position, R.id.progressBar_join)
                                                                .setVisibility(View.GONE);

                                                        if (join_unjoin.getJoinStatus().equals("left")) {
                                                            snack = new KSnack((FragmentActivity) context);
                                                            snack.setMessage("You have left " + suggested_groups_adapter.getData().get(position).getName());

                                                            is_joined.setText("Join");

                                                        } else {
                                                            snack = new KSnack((FragmentActivity) context);
                                                            snack.setMessage("You have joined " + suggested_groups_adapter.getData().get(position).getName());

                                                            is_joined.setText("Joined");

                                                            /*TODO Add to joined groups*/
                                                        }
                                                    } else {
                                                        apiErrors errors = join_unjoin.getErrors();
                                                        Log.d(TAG, "onNext: " + errors.getErrorText());

                                                        snack = new KSnack((FragmentActivity) context);
                                                        snack.setMessage("Oops !\nSomething went " +
                                                                "wrong");
                                                        snack.setAction("DISMISS", view -> {
                                                            snack.dismiss();
                                                        });
                                                    }
                                                    snack.show();
                                                    snack.setDuration(3000);
                                                }

                                                @Override
                                                public void onError(@NonNull Throwable e) {
                                                    Log.d(TAG, "onError: " + e.getMessage());

                                                    snack = new KSnack((FragmentActivity) context);
                                                    snack.setMessage("Oops !\nSomething went " +
                                                            "wrong");
                                                    snack.show();
                                                    snack.setDuration(3000);
                                                }

                                                @Override
                                                public void onComplete() {
                                                    Log.d(TAG, "onComplete: ");
                                                }
                                            });
                                }
                            });
                        } else {
                            apiErrors apiErrors = groupList.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        }
                    }

                    private void updateUI() {
                        if (groupInfoList.size() == 0) {
                            /*Show all set*/
                            suggestedGroupsList.setVisibility(View.GONE);
                            progressBarSuggested.setVisibility(View.GONE);

                            allSetUpImg.setVisibility(View.VISIBLE);
                            allSetUpText.setVisibility(View.VISIBLE);

                        } else {
                            /*Show recyclerview*/
                            allSetUpImg.setVisibility(View.GONE);
                            allSetUpText.setVisibility(View.GONE);

                            progressBarSuggested.setVisibility(View.GONE);
                            suggestedGroupsList.setVisibility(View.VISIBLE);
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
