/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.utils.xpopup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.hendraanggrian.appcompat.widget.SocialEditText;
import com.hendraanggrian.appcompat.widget.SocialTextView;
import com.hendraanggrian.appcompat.widget.SocialView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.R;
import com.soshoplus.lite.adapters.commentsAdapter;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.models.postsfeed.commentsList;
import com.soshoplus.lite.models.simpleResponse;
import com.soshoplus.lite.ui.hashTagsPosts;
import com.soshoplus.lite.ui.user_profile.userProfile;
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
    private static String userId, timezone, accessToken;
    private RecyclerView commentsRv;
    private SocialEditText addComment;
    private MaterialButton sendComment;
    private ImageView circle_loader;
    private MaterialTextView no_comment_tv;
    private Observable<commentsList> commentsListObservable;
    private Observable<simpleResponse> simpleResponseObservable;
    private commentsAdapter commentsAdapter, replyCommentAdapter;
    private queries rxJavaQueries;

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
        MaterialButton back = findViewById(R.id.back_arrow);
        circle_loader = findViewById(R.id.circle_loader);
        commentsRv = findViewById(R.id.comments_rv);
        addComment = findViewById(R.id.add_comment);
        sendComment = findViewById(R.id.send_comment);

        no_comment_tv = findViewById(R.id.no_comments_tv);

        Glide.with(getContext()).load(R.drawable.circles_loader).into(circle_loader);

        userId = SecurePreferences.getStringValue(getContext(), "userId", "0");
        timezone = SecurePreferences.getStringValue(getContext(), "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(getContext(), "accessToken", "0");
        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);

        back.setOnClickListener(view -> smartDismiss());

        Log.d(TAG, "TYPE : " + type);
        /*fetch comments*/
        new Handler().postDelayed(this::fetchComments, 1000);

        sendComment.setOnClickListener(view -> {
            if (addComment.getText().toString().isEmpty()) {
                Toast toast = Toast.makeText(getContext(), "Please add comment to send ... ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
            } else {
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

                                circle_loader.setVisibility(View.GONE);
                                commentsRv.setVisibility(View.VISIBLE);

                                commentsAdapter.setOnItemChildClickListener((adapter_, view, position) -> {
                                    /*show comments reply*/
                                    type = "fetch_comments_reply";

                                    if (view.getId() == R.id.no_reply) {
                                        new XPopup.Builder(getContext()).asCustom(new commentsPopup(getContext(),
                                                commentsAdapter.getData().get(position).getId(), type)).show();
                                    } else if (view.getId() == R.id.no_likes) {
                                        MaterialButton like = view.findViewById(R.id.no_likes);
                                        likeComment(commentsAdapter.getData().get(position).getId(), like);
                                    } else if (view.getId() == R.id.reply_comment) {
                                        addComment.setText(null);
                                        addComment.append("@" + commentsAdapter.getData().get(position).getPublisherInfo().getUsername() + " ");
                                    } else if (view.getId() == R.id.comment_txt) {
                                        SocialTextView socialTextView = view.findViewById(R.id.comment_txt);
                                        socialTextView.setOnLongClickListener(null);
                                        openHashMention(socialTextView);
                                    }
                                });

                                if (comments.getPostComments().size() == 0) {
                                    no_comment_tv.setVisibility(View.VISIBLE);
                                } else {
                                    no_comment_tv.setVisibility(View.GONE);
                                }
                            } else {
                                apiErrors errors = comments.getErrors();
                                Log.d(TAG, "onNext: " + errors.getErrorId());
                                Log.d(TAG, "onNext: " + errors.getErrorText());

                                Toast toast = Toast.makeText(getContext(), "Failed to get comments ... ", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();

                                circle_loader.setVisibility(View.GONE);
                                smartDismiss();
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            Log.d(TAG, "onError: " + e.getMessage());

                            Toast toast = Toast.makeText(getContext(), "Failed to get comments ... ", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();

                            circle_loader.setVisibility(View.GONE);
                            smartDismiss();
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: ");
                        }
                    });
        } else {
            commentsListObservable = rxJavaQueries.commentsActions(accessToken, BuildConfig.server_key,
                    type, _id, null, null);
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

                                circle_loader.setVisibility(View.GONE);
                                commentsRv.setVisibility(View.VISIBLE);

                                replyCommentAdapter.setOnItemChildClickListener((adapter_, view, position) -> {
                                    /*show comments reply*/
                                    if (view.getId() == R.id.no_reply) {
                                        new XPopup.Builder(getContext()).asCustom(new commentsPopup(getContext(),
                                                replyCommentAdapter.getData().get(position).getId(), type)).show();
                                    } else if (view.getId() == R.id.no_likes) {
                                        MaterialButton like = view.findViewById(R.id.no_likes);
                                        likeComment(replyCommentAdapter.getData().get(position).getId(), like);
                                    } else if (view.getId() == R.id.reply_comment) {
                                        addComment.setText(null);
                                        addComment.append("@" + replyCommentAdapter.getData().get(position).getPublisherInfo().getUsername() + " ");
                                    } else if (view.getId() == R.id.comment_txt) {
                                        SocialTextView socialTextView = view.findViewById(R.id.comment_txt);
                                        socialTextView.setOnLongClickListener(null);
                                        openHashMention(socialTextView);
                                    }
                                });

                                if (comments.getPostComments().size() == 0) {
                                    no_comment_tv.setVisibility(View.VISIBLE);
                                } else {
                                    no_comment_tv.setVisibility(View.GONE);
                                }
                            } else {
                                apiErrors errors = comments.getErrors();
                                Log.d(TAG, "onNext: " + errors.getErrorId());
                                Log.d(TAG, "onNext: " + errors.getErrorText());

                                Toast toast = Toast.makeText(getContext(), "Failed to get comments ... ", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();

                                circle_loader.setVisibility(View.GONE);
                                smartDismiss();
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            Log.d(TAG, "onError: " + e.getMessage());

                            Toast toast = Toast.makeText(getContext(), "Failed to get comments ... ", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();

                            circle_loader.setVisibility(View.GONE);
                            smartDismiss();
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: ");
                        }
                    });
        }
    }

    private void likeComment(String comment_id, MaterialButton like) {
        simpleResponseObservable = rxJavaQueries.simpleCommentActions(accessToken,
                BuildConfig.server_key, "comment_like", comment_id, null, null);

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

                            /*
                             * Response
                             * - 0 - Unlike
                             * - 1 - Like
                             *
                             * Log.d(TAG, "CODE RESPONSE: " + simpleResponse.getCode());
                             * */

                            if (simpleResponse.getCode() == 0) {
                                like.setIconResource(R.drawable.ic_like);

                                int i = Integer.parseInt(like.getText().toString());
                                like.setText(String.valueOf(i - 1));
                            } else {
                                like.setIconResource(R.drawable.ic_liked);

                                int i = Integer.parseInt(like.getText().toString());
                                like.setText(String.valueOf(i + 1));
                            }
                        } else {
                            apiErrors errors = simpleResponse.getErrors();
                            Log.d(TAG, "onNext: " + errors.getErrorId());
                            Log.d(TAG, "onNext: " + errors.getErrorText());

                            Toast toast = Toast.makeText(getContext(), "Oops error ... ", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());

                        Toast toast = Toast.makeText(getContext(), "Comment not sent ... ", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void openHashMention(SocialTextView socialTextView) {
        socialTextView.setOnHashtagClickListener(new SocialView.OnClickListener() {
            @Override
            public void onClick(@androidx.annotation.NonNull SocialView view,
                                @androidx.annotation.NonNull CharSequence text) {

                Intent intent = new Intent(getContext(), hashTagsPosts.class);
                intent.putExtra("hashTag", text.toString());
                getContext().startActivity(intent);
            }
        });

        socialTextView.setOnMentionClickListener(new SocialView.OnClickListener() {
            @Override
            public void onClick(@androidx.annotation.NonNull SocialView view,
                                @androidx.annotation.NonNull CharSequence text) {

                Intent intent = new Intent(getContext(), userProfile.class);
                intent.putExtra("username", text.toString());
                getContext().startActivity(intent);
            }
        });
    }

    private void sendComment() {
        /*
         * type
         *
         * -- create -> post_id
         * -- create_reply  -> comment_id
         *
         * */

        circle_loader.setVisibility(View.VISIBLE);

        addComment.setEnabled(false);
        sendComment.setEnabled(false);

        simpleResponseObservable = rxJavaQueries.simpleCommentActions(accessToken, BuildConfig.server_key, "create", null, _id,
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
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();

                            }, 2000);
                        } else {
                            apiErrors errors = simpleResponse.getErrors();
                            Log.d(TAG, "onNext: " + errors.getErrorId());
                            Log.d(TAG, "onNext: " + errors.getErrorText());

                            Toast toast = Toast.makeText(getContext(), "Comment not sent ... ", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();

                            circle_loader.setVisibility(View.GONE);

                            addComment.setText(null);
                            addComment.setEnabled(true);
                            sendComment.setEnabled(true);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());

                        Toast toast = Toast.makeText(getContext(), "Comment not sent ... ", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();

                        circle_loader.setVisibility(View.GONE);

                        addComment.setText(null);
                        addComment.setEnabled(true);
                        sendComment.setEnabled(true);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");

                        addComment.setText(null);
                        addComment.setEnabled(true);
                        sendComment.setEnabled(true);
                    }
                });
    }
}
