/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.utils.xpopup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.R;
import com.soshoplus.lite.adapters.commentsAdapter;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.models.postsfeed.commentsList;
import com.soshoplus.lite.utils.queries;
import com.soshoplus.lite.utils.retrofitInstance;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@SuppressLint("ViewConstructor")
public class commentsPopup extends BottomPopupView {

    private static String TAG = "comments popup";
    private static String type = "fetch_comments";

    private static String _id;
    private RecyclerView commentsRv;
    private ProgressBar progressBar;

    private Observable<commentsList> commentsListObservable;
    private queries rxJavaQueries;
    private String accessToken;

    public commentsPopup(@NonNull Context context, String Id) {
        super(context);
        _id = Id;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.comments_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        accessToken = SecurePreferences.getStringValue(getContext(), "accessToken", "0");

        MaterialButton back = findViewById(R.id.back_arrow);
        commentsRv = findViewById(R.id.comments_rv);
        progressBar = findViewById(R.id.fetch_progress);

        back.setOnClickListener(view -> smartDismiss());

        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);
        /*fetch comments*/
        new Handler().postDelayed(this::fetchComments, 1000);
    }

    private void fetchComments() {

        if (type.equals("fetch_comments")) {
            commentsListObservable = rxJavaQueries.getPostComments(accessToken, BuildConfig.server_key, type, _id);
            commentsListObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<commentsList>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                            Log.d(TAG, "onSubscribe: ");
                        }

                        @Override
                        public void onNext(@io.reactivex.rxjava3.annotations.NonNull commentsList comments) {
                            if (comments.getApiStatus() == 200) {
                                commentsRv.setLayoutManager(new LinearLayoutManager(getContext()));
                                commentsAdapter adapter = new commentsAdapter(R.layout.comment_view, comments.getPostComments());
                                commentsRv.setAdapter(adapter);

                                progressBar.setVisibility(View.GONE);

                                adapter.setOnItemChildClickListener((adapter_, view, position) -> {
                                    /*show comments reply*/
                                    type = "fetch_comments_reply";
                                    new XPopup.Builder(getContext()).asCustom(new commentsPopup(getContext(),
                                            adapter.getData().get(position).getId())).show();
                                });
                            }
                            else {
                                apiErrors errors = comments.getErrors();
                                Log.d(TAG, "onNext: " + errors.getErrorId());

                                Toast toast = Toast.makeText(getContext(), "Failed to get comments ... ", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                                toast.show();

                                progressBar.setVisibility(View.GONE);
                                smartDismiss();
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            Log.d(TAG, "onError: " + e.getMessage());

                            Toast toast = Toast.makeText(getContext(), "Failed to get comments ... ", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                            toast.show();

                            progressBar.setVisibility(View.GONE);
                            smartDismiss();
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: ");
                        }
                    });
        }
        else {
            commentsListObservable = rxJavaQueries.commentsActions(accessToken, BuildConfig.server_key, type, _id);
            commentsListObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<commentsList>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                            Log.d(TAG, "onSubscribe: ");
                        }

                        @Override
                        public void onNext(@io.reactivex.rxjava3.annotations.NonNull commentsList comments) {
                            if (comments.getApiStatus() == 200) {
                                commentsRv.setLayoutManager(new LinearLayoutManager(getContext()));
                                commentsAdapter adapter = new commentsAdapter(R.layout.comment_view, comments.getPostComments());
                                commentsRv.setAdapter(adapter);

                                progressBar.setVisibility(View.GONE);

                                adapter.setOnItemChildClickListener((adapter_, view, position) -> {
                                    /*show comments reply*/
                                    new XPopup.Builder(getContext()).asCustom(new commentsPopup(getContext(),
                                            adapter.getData().get(position).getId())).show();
                                });
                            }
                            else {
                                apiErrors errors = comments.getErrors();
                                Log.d(TAG, "onNext: " + errors.getErrorId());

                                Toast toast = Toast.makeText(getContext(), "Failed to get comments ... ", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                                toast.show();

                                progressBar.setVisibility(View.GONE);
                                smartDismiss();
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            Log.d(TAG, "onError: " + e.getMessage());

                            Toast toast = Toast.makeText(getContext(), "Failed to get comments ... ", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                            toast.show();

                            progressBar.setVisibility(View.GONE);
                            smartDismiss();
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: ");
                        }
                    });
        }
    }
}
