/*
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 */

package com.soshoplus.lite.calls;

import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseProviderMultiAdapter;
import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.R;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.models.postsfeed.post;
import com.soshoplus.lite.models.postsfeed.reactions.like_dislike;
import com.soshoplus.lite.utils.queries;
import com.soshoplus.lite.utils.retrofitInstance;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import dev.DevUtils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static dev.DevUtils.getContext;

public class likePostCall {

    private static String TAG = "likePost Call :";
    private final int likeCount;
    /*POST LIKE_DISLIKE*/
    private Observable<like_dislike> like_dislikeObservable;

    public likePostCall(BaseProviderMultiAdapter<post> adapter, int position, ImageView like_btn, TextView no_likes_holder) {

        /*getting likes count | if post is likes*/
        likeCount = Integer.parseInt(adapter.getData().get(position).getPostLikes());

        /*update button*/
        if (adapter.getData().get(position).isLiked()) {

            adapter.getData().get(position).setLiked(false);
            like_btn.setImageResource(R.drawable.ic_like);

            removeLike(adapter, position, like_btn, no_likes_holder);
        }
        else {
            adapter.getData().get(position).setLiked(true);
            like_btn.setImageResource(R.drawable.ic_liked);

            addLike(adapter, position, like_btn, no_likes_holder);
        }

        sendRequest(adapter, position, like_btn, no_likes_holder);
    }

    private void sendRequest(BaseProviderMultiAdapter<post> adapter, int position, ImageView like_btn, TextView no_likes_holder) {

        /*initializing query*/
        queries rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);
        String accessToken = SecurePreferences.getStringValue(DevUtils.getContext(), "accessToken", "0");

        like_dislikeObservable = rxJavaQueries.like_dislikePost(accessToken,
                BuildConfig.server_key, adapter.getData().get(position).getPostId(), "like");
        like_dislikeObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<like_dislike>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull like_dislike like_dislike) {
                        if (like_dislike.getApiStatus() == 200) {
                            Log.d(TAG, "onNext: " + like_dislike.getAction());

                            Toast toast = Toast.makeText(getContext(), like_dislike.getAction() + " ... ", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();
                        }
                        else {
                            apiErrors apiErrors = like_dislike.getErrors();
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());

                        /*TODO repeat if failed*/
                        sendRequest(adapter, position, like_btn, no_likes_holder);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    /*increase no likes*/
    private void addLike(BaseProviderMultiAdapter<post> adapter, int position, ImageView like_btn, TextView no_likes_holder) {
        adapter.getData().get(position).setLiked(true);
        adapter.getData().get(position).setPostLikes(String.valueOf(likeCount + 1));
        like_btn.setImageResource(R.drawable.ic_liked);
        no_likes_holder.setText(adapter.getData().get(position).getPostLikes() + " Likes");
    }

    /*decrease no likes*/
    private void removeLike(BaseProviderMultiAdapter<post> adapter, int position, ImageView like_btn, TextView no_likes_holder) {
        adapter.getData().get(position).setLiked(false);
        adapter.getData().get(position).setPostLikes(String.valueOf(likeCount - 1));
        like_btn.setImageResource(R.drawable.ic_like);

        if (adapter.getData().get(position).getPostLikes().equals("0")) {
            no_likes_holder.setText("Like");
        }
        else {
            no_likes_holder.setText(adapter.getData().get(position).getPostLikes() + " Likes");
        }
    }
}
