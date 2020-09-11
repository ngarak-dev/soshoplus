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
import com.hendraanggrian.appcompat.widget.SocialEditText;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.R;
import com.soshoplus.lite.adapters.commentsAdapter;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.models.postsfeed.commentsList;
import com.soshoplus.lite.models.simpleResponse;
import com.soshoplus.lite.utils.queries;
import com.soshoplus.lite.utils.retrofitInstance;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import dev.DevUtils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@SuppressLint("ViewConstructor")
public class commentsPopup extends FullScreenPopupView {

    private static String TAG = "comments popup";
    private static String type;

    private static String _id;
    private RecyclerView commentsRv;
    private ProgressBar progressBar;
    private SocialEditText addComment;
    private MaterialButton sendComment;

    private Observable<commentsList> commentsListObservable;
    private Observable<simpleResponse> simpleResponseObservable;
    private queries rxJavaQueries;
    private String accessToken;
    private commentsAdapter commentsAdapter, replyCommentAdapter;

    public commentsPopup(@NonNull Context context, String Id, String _type) {
        super(context);
        _id = Id;
        type = _type;
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
        addComment = findViewById(R.id.add_comment);
        sendComment = findViewById(R.id.send_comment);

        back.setOnClickListener(view -> smartDismiss());

        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);
        Log.d(TAG, "TYPE : " + type);
        /*fetch comments*/
        new Handler().postDelayed(this::fetchComments, 1000);

        sendComment.setOnClickListener(view -> {
            if (addComment.getText().toString().isEmpty()) {
                Toast toast = Toast.makeText(getContext(), "Please add comment to send ... ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                toast.show();
            }
            else {
                new Handler().postDelayed(this::sendComment, 1000);
            }
        });
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
                                commentsAdapter = new commentsAdapter(R.layout.comment_view, comments.getPostComments());
                                commentsRv.setAdapter(commentsAdapter);

                                progressBar.setVisibility(View.GONE);
                                commentsRv.setVisibility(View.VISIBLE);

                                commentsAdapter.setOnItemChildClickListener((adapter_, view, position) -> {
                                    /*show comments reply*/
                                    type = "fetch_comments_reply";
                                    if (view.getId() == R.id.no_reply) {
                                        new XPopup.Builder(getContext()).asCustom(new commentsPopup(getContext(),
                                                commentsAdapter.getData().get(position).getId(), type)).show();
                                    }
                                    else if (view.getId() == R.id.reply_comment) {
                                        addComment.setText(null);
                                        addComment.append("@" + commentsAdapter.getData().get(position).getPublisherInfo().getUsername() + " ");
                                    }
                                });
                            }
                            else {
                                apiErrors errors = comments.getErrors();
                                Log.d(TAG, "onNext: " + errors.getErrorId());
                                Log.d(TAG, "onNext: " + errors.getErrorText());

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
            commentsListObservable = rxJavaQueries.commentsActions(accessToken, BuildConfig.server_key, type, _id, null, null);
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
                                replyCommentAdapter = new commentsAdapter(R.layout.comment_view, comments.getPostComments());
                                commentsRv.setAdapter(replyCommentAdapter);

                                progressBar.setVisibility(View.GONE);
                                commentsRv.setVisibility(View.VISIBLE);

                                replyCommentAdapter.setOnItemChildClickListener((adapter_, view, position) -> {
                                    /*show comments reply*/
                                    if (view.getId() == R.id.no_reply) {
                                        new XPopup.Builder(getContext()).asCustom(new commentsPopup(getContext(),
                                                replyCommentAdapter.getData().get(position).getId(), type)).show();
                                    }
                                    else if (view.getId() == R.id.reply_comment) {
                                        addComment.setText(null);
                                        addComment.append("@" + replyCommentAdapter.getData().get(position).getPublisherInfo().getUsername() + " ");
                                    }
                                });
                            }
                            else {
                                apiErrors errors = comments.getErrors();
                                Log.d(TAG, "onNext: " + errors.getErrorId());
                                Log.d(TAG, "onNext: " + errors.getErrorText());

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

    private void sendComment() {
        /*
        * type
        *
        * -- create -> post_id
        * -- create_reply  -> comment_id
        *
        * */

        progressBar.setVisibility(View.VISIBLE);

        addComment.setEnabled(false);
        sendComment.setEnabled(false);

        simpleResponseObservable = rxJavaQueries.createComment(accessToken, BuildConfig.server_key, "create", null, _id,
                addComment.getText().toString());
        simpleResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<simpleResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull simpleResponse simpleResponse) {
                        if (simpleResponse.getApiStatus() == 200) {

                            fetchComments();

                            DevUtils.getHandler().postDelayed(() -> {
                                Toast toast = Toast.makeText(getContext(), "Comment sent ... ", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                                toast.show();

                            }, 2000);
                        }
                        else {
                            apiErrors errors = simpleResponse.getErrors();
                            Log.d(TAG, "onNext: " + errors.getErrorId());
                            Log.d(TAG, "onNext: " + errors.getErrorText());

                            Toast toast = Toast.makeText(getContext(), "Comment not sent ... ", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                            toast.show();

                            progressBar.setVisibility(View.GONE);

                            addComment.setEnabled(true);
                            sendComment.setEnabled(true);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());

                        Toast toast = Toast.makeText(getContext(), "Comment not sent ... ", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                        toast.show();

                        progressBar.setVisibility(View.GONE);

                        addComment.setEnabled(true);
                        sendComment.setEnabled(true);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");

                        addComment.setEnabled(true);
                        addComment.setText(null);
                        sendComment.setEnabled(true);
                    }
                });
    }
}
