/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.feedHolders;

import android.text.util.Linkify;
import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.hendraanggrian.appcompat.widget.SocialTextView;
import com.soshoplus.lite.R;
import com.soshoplus.lite.models.postsfeed.post;
import com.soshoplus.lite.models.postsfeed.sharedInfo;

import org.jetbrains.annotations.NotNull;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.transform.CircleCropTransformation;
import coil.transform.RoundedCornersTransformation;
import io.noties.markwon.Markwon;
import io.noties.markwon.linkify.LinkifyPlugin;

public class SharedPost extends BaseItemProvider<post> {

    private static String TAG = "SHARED POST : ";

    ImageView profile_pic, shared_profile_pic, shared_post_image;
    MaterialButton like;
    SocialTextView post_contents, shared_post_contents;

    @Override
    public int getItemViewType() {
        return post.SHARED_POST;
    }

    @Override
    public int getLayoutId() {
        return R.layout.shared_post_list_row;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, post post) {
        Log.d(TAG, post.getPostId());

        ImageLoader imageLoader = Coil.imageLoader(getContext());

        profile_pic = baseViewHolder.findView(R.id.profile_pic);
        shared_profile_pic = baseViewHolder.findView(R.id.shared_profile_pic);
        shared_post_image = baseViewHolder.findView(R.id.shared_post_image);

        like = baseViewHolder.findView(R.id.like_btn);

        post_contents = baseViewHolder.findView(R.id.post_contents);
        shared_post_contents = baseViewHolder.findView(R.id.shared_post_contents);

        baseViewHolder.setText(R.id.full_name, post.getPublisherInfo().getName());
        baseViewHolder.setText(R.id.time_ago, post.getPostTime());
        baseViewHolder.setText(R.id.like_btn, post.getPostLikes());
        baseViewHolder.setText(R.id.comment_btn, post.getPostComments());

        Markwon markwon = Markwon.builder(getContext())
                .usePlugin(LinkifyPlugin.create(
                        Linkify.EMAIL_ADDRESSES | Linkify.PHONE_NUMBERS | Linkify.WEB_URLS
                ))
                .build();

        if (post.getPostTextAPI().isEmpty()) {
            baseViewHolder.setGone(R.id.post_contents, true);
        } else {
            markwon.setMarkdown(post_contents, post.getPostTextAPI());
        }

        /*bind profile pic*/
        ImageRequest imageRequest = new ImageRequest.Builder(getContext())
                .data(post.getPublisherInfo().getAvatar())
                .placeholder(R.color.light_grey)
                .crossfade(true)
                .transformations(new CircleCropTransformation())
                .target(profile_pic)
                .build();
        imageLoader.enqueue(imageRequest);

        /*if post is liked*/
        if (post.isLiked()) {
            like.setIconResource(R.drawable.ic_liked);
        } else {
            like.setIconResource(R.drawable.ic_like);
        }

        /*shared data*/
        /*Converting Object to json data*/
        Gson gson = new Gson();
        String toJson = gson.toJson(post.getSharedInfo());
        /*getting data from json string using pojo class*/
        sharedInfo sharedInfo = gson.fromJson(toJson, sharedInfo.class);

        baseViewHolder.setText(R.id.shared_full_name, sharedInfo.getPublisherInfo().getName());
        baseViewHolder.setText(R.id.shared_time_ago, sharedInfo.getPostTime());
        if (!sharedInfo.getPostTextAPI().isEmpty()) {
            markwon.setMarkdown(shared_post_contents, sharedInfo.getPostTextAPI());
        }

        /*bind profile pic*/
        imageRequest = new ImageRequest.Builder(getContext())
                .data(sharedInfo.getPublisherInfo().getAvatar())
                .placeholder(R.color.light_grey)
                .crossfade(true)
                .transformations(new CircleCropTransformation())
                .target(shared_profile_pic)
                .build();
        imageLoader.enqueue(imageRequest);

        if (sharedInfo.getPostFile().isEmpty()) {
            baseViewHolder.setGone(R.id.shared_post_image, true);
        } else {
            /*bind post pic*/
            imageRequest = new ImageRequest.Builder(getContext())
                    .data(post.getPostFile())
                    .placeholder(R.color.light_grey)
                    .crossfade(true)
                    .transformations(new RoundedCornersTransformation(10))
                    .target(shared_post_image)
                    .build();
            imageLoader.enqueue(imageRequest);
        }
    }
}
