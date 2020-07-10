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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.onurkagan.ksnack_lib.KSnack.KSnack;
import com.soshoplus.timeline.BuildConfig;
import com.soshoplus.timeline.adapters.suggestedGroupsAdapter;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.groups.groupInfo;
import com.soshoplus.timeline.models.groups.groupList;
import com.soshoplus.timeline.models.groups.join.join_unjoin;
import com.soshoplus.timeline.ui.groups.viewGroup;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;

import java.util.List;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class recommendedGroupsCalls {
    
    private static String TAG = "Recommended groups Calls";
    /*context*/
    private Context context;
    /*......*/
    private queries rxJavaQueries;
    private String accessToken, userId, timezone;
    
    /*ADAPTERS*/
    private suggestedGroupsAdapter suggested_groups_adapter;
    
    /*......*/
    private static String fetch_recommended = "groups";
    private Observable<groupList> groupListObservable;
    private List<groupInfo> groupInfoList = null;
    
    /*JOIN GROUP*/
    private Observable<join_unjoin> joinUnjoinObservable;
    
    /*......*/
    private KSnack snack;
    
    public recommendedGroupsCalls (Context context) {
        this.context = context;
    
        userId = SecurePreferences.getStringValue(context, "userId", "0");
        timezone = SecurePreferences.getStringValue(context, "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(context, "accessToken"
                , "0");
    
        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);
    
        /*initializing snack*/
        snack = new KSnack((FragmentActivity) context);
    }
    
    
    public void getRecommends (RecyclerView suggestedGroupsList, ImageView allSetUpImg,
                               TextView allSetUpText, ProgressBar progressBarSuggested) {
    
        groupListObservable = rxJavaQueries.getRecommended(accessToken,
                BuildConfig.server_key, fetch_recommended, "5");
        groupListObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<groupList>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
                
                    @Override
                    public void onNext (@NonNull groupList groupList) {
                        if (groupList.getApiStatus() == 200) {
                        
                            /*initializing list*/
                            groupInfoList = groupList.getInfo();
                        
                            /*initializing adapter*/
                            suggested_groups_adapter =
                                    new suggestedGroupsAdapter(context,
                                            groupInfoList, new suggestedGroupsAdapter.onGroupClickListener() {
                                        @Override
                                        public void onGroupClick (groupInfo groupInfo) {
                                            Log.d(TAG, "onGroupClick: " + groupInfo.getGroupName());
                                            
                                            /*TODO
                                            *  PREVIEW GROUP POPUP*/
                                            
                                        }
                                    
                                        @Override
                                        public void onJoinClick (groupInfo groupInfo, MaterialButton is_joined,
                                                                 int position, ProgressBar progressBar) {
                                            is_joined.setText(null);
                                            progressBar.setVisibility(View.VISIBLE);
                                        
                                            joinUnjoinObservable =
                                                    rxJavaQueries.joinGroup(accessToken, BuildConfig.server_key, groupInfo.getGroupId());
                                        
                                            joinUnjoinObservable.subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new Observer<join_unjoin>() {
                                                        @Override
                                                        public void onSubscribe (@NonNull Disposable d) {
                                                            Log.d(TAG, "onSubscribe: ");
                                                        }
                                                    
                                                        @Override
                                                        public void onNext (@NonNull join_unjoin join_unjoin) {
                                                            if (join_unjoin.getApiStatus() == 200) {
                                                            
                                                                progressBar.setVisibility(View.GONE);
                                                            
                                                                if (join_unjoin.getJoinStatus().equals("left")) {
                                                                    snack = new KSnack((FragmentActivity) context);
                                                                    snack.setMessage("You have left " + groupInfo.getName());
                                                                    snack.show();
                                                                    snack.setDuration(3000);
                                                                
                                                                } else {
                                                                   /*remove
                                                                   from list
                                                                   and update
                                                                    adapter*/
                                                                    groupInfoList.remove(position);
                                                                    suggested_groups_adapter.notifyDataSetChanged();
                                                                
                                                                    /*.....*/
                                                                    updateUI();
                                                                   
                                                                   /*TODO Add
                                                                       group
                                                                       to
                                                                       joined
                                                                        groups*/
                                                                   /*show
                                                                   snack*/
                                                                    snack = new KSnack((FragmentActivity) context);
                                                                    snack.setMessage("You have joined " + groupInfo.getName());
                                                                    snack.show();
                                                                    snack.setDuration(3000);
                                                                }
                                                            }
                                                            else {
                                                                apiErrors errors = join_unjoin.getErrors();
                                                                Log.d(TAG, "onNext: " + errors.getErrorText());
                                                            
                                                                snack = new KSnack((FragmentActivity) context);
                                                                snack.setMessage("Oops !\nSomething went " +
                                                                        "wrong");
                                                                snack.setAction("DISMISS", view -> {
                                                                    snack.dismiss();
                                                                });
                                                                snack.show();
                                                                snack.setDuration(3000);
                                                            }
                                                        }
                                                    
                                                        @Override
                                                        public void onError (@NonNull Throwable e) {
                                                            Log.d(TAG,
                                                                    "onError:" +
                                                                            " " + e.getMessage());
                                                        
                                                            snack = new KSnack((FragmentActivity) context);
                                                            snack.setMessage("Oops !\nSomething went " +
                                                                    "wrong");
                                                            snack.show();
                                                            snack.setDuration(3000);
                                                        }
                                                    
                                                        @Override
                                                        public void onComplete () {
                                                            Log.d(TAG, "onComplete: ");
                                                        }
                                                    });
                                        }
                                    });
                        
                        
                            /*Setting Layout*/
                            suggestedGroupsList.setLayoutManager(new LinearLayoutManager(context));
                            suggestedGroupsList.setItemAnimator(new DefaultItemAnimator());
                        
                            /*Setting Adapter*/
                            suggestedGroupsList.setAdapter(suggested_groups_adapter);
                        
                            /*......*/
                            /*After fetching Data*/
                            updateUI();
                        }
                        else {
                            apiErrors apiErrors = groupList.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                        }
                    }
                
                    private void updateUI () {
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
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " +e.getMessage());
                    }
                
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
