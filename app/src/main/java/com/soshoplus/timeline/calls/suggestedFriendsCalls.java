/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.calls;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.xpopup.XPopup;
import com.onurkagan.ksnack_lib.KSnack.KSnack;
import com.soshoplus.timeline.BuildConfig;
import com.soshoplus.timeline.adapters.suggestedFriendsAdapter;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.friends.suggested.suggestedInfo;
import com.soshoplus.timeline.models.friends.suggested.suggestedList;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;
import com.soshoplus.timeline.utils.xpopup.previewProfilePopup;

import java.util.List;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class suggestedFriendsCalls {
    
    private final static String TAG = "Suggested friends Calls";
    /*context*/
    private Context context;
    /*......*/
    private queries rxJavaQueries;
    private String accessToken, userId, timezone;
    
    /*......*/
    private List<suggestedInfo> suggestedInfoList = null;
    private static String suggested_friends = "users";
    private Observable<suggestedList> suggestedListObservable;
    
    public suggestedFriendsCalls (Context context) {
        this.context = context;
    
        userId = SecurePreferences.getStringValue(context, "userId", "0");
        timezone = SecurePreferences.getStringValue(context, "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(context, "accessToken"
                , "0");
    
        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);
    }
    
    public void getSuggestedFriends (RecyclerView suggestedFriendsList, TextView suggestedTitle,
                                     ProgressBar progressBarSuggested, ImageButton refreshSuggested) {
    
        /*show progressbar*/
        progressBarSuggested.setVisibility(View.VISIBLE);
    
        suggestedListObservable =
                rxJavaQueries.getPeopleYouMayKnow(accessToken, BuildConfig.server_key,
                        suggested_friends, "10");
    
        suggestedListObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<suggestedList>() {
                    @Override
                    public void onSubscribe (@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
                
                    @Override
                    public void onNext (@NonNull suggestedList suggestedList) {
                        if (suggestedList.getApiStatus() == 200) {
                            /*initializing list*/
                            suggestedInfoList =
                                    suggestedList.getSuggestedInfo();
                        
                            /*initializing adapter*/
                            suggestedFriendsAdapter listAdapter = new suggestedFriendsAdapter(context, suggestedInfoList,
                                    new suggestedFriendsAdapter.onSuggestedClickListener() {
                                        @Override
                                        public void onClick (suggestedInfo suggestedInfo) {
                                            /*preview profile*/
                                            new XPopup.Builder(context).asCustom(new previewProfilePopup(context, suggestedInfo.getUserId())).show();
                                        }
                                    });
                        
                            /*Setting Layout*/
                            suggestedFriendsList.setLayoutManager(new LinearLayoutManager(context));
                            suggestedFriendsList.setItemAnimator(new DefaultItemAnimator());
                        
                            /*Setting Adapter*/
                            suggestedFriendsList.setAdapter(listAdapter);
                        
                            /*show recycler view, refresh btn, hide progress*/
                            suggestedTitle.setText("People you may know");
                            suggestedFriendsList.setVisibility(View.VISIBLE);
                            progressBarSuggested.setVisibility(View.GONE);
                            refreshSuggested.setVisibility(View.VISIBLE);
                        }
                        else {
                            apiErrors apiErrors = suggestedList.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                            /*.......*/
                            progressBarSuggested.setVisibility(View.GONE);
                            refreshSuggested.setVisibility(View.VISIBLE);
                            /*........*/
                            suggestedTitle.setText("Error getting users");
                        }
                    }
                
                    @Override
                    public void onError (@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    
                        /*show refresh btn*/
                        progressBarSuggested.setVisibility(View.GONE);
                        refreshSuggested.setVisibility(View.VISIBLE);
                    
                        /*.....*/
                        suggestedTitle.setText("Error getting users");
                    }
                
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    
        /*refresh btn*/
        refreshSuggested.setOnClickListener(view -> {
            getSuggestedFriends(suggestedFriendsList, suggestedTitle,
                    progressBarSuggested, refreshSuggested);
        
            /*visibility*/
            suggestedFriendsList.setVisibility(View.GONE);
            refreshSuggested.setVisibility(View.GONE);
            progressBarSuggested.setVisibility(View.VISIBLE);
        
        });
    }
}
