/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.feedHolders;

import android.text.Html;
import android.util.Log;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.postsfeed.post;
import com.soshoplus.timeline.models.postsfeed.sharedInfo;

import org.jetbrains.annotations.NotNull;

public class SharedPost extends BaseItemProvider<post> {
    
    private static String TAG = "SHARED POST : ";
    
    SimpleDraweeView profile_pic, shared_profile_pic, shared_post_image;
    MaterialButton like;
    
    @Override
    public int getItemViewType () {
        return post.SHARED_POST;
    }
    
    @Override
    public int getLayoutId () {
        return R.layout.shared_post_list_row;
    }
    
    @Override
    public void convert (@NotNull BaseViewHolder baseViewHolder, post post) {
        Log.d(TAG, post.getPostId());

        profile_pic = baseViewHolder.findView(R.id.profile_pic);
        shared_profile_pic = baseViewHolder.findView(R.id.shared_profile_pic);
        shared_post_image = baseViewHolder.findView(R.id.shared_post_image);

        like = baseViewHolder.findView(R.id.like_btn);


        baseViewHolder.setText(R.id.full_name, post.getPublisherInfo().getName());
        baseViewHolder.setText(R.id.time_ago, post.getPostTime());
        baseViewHolder.setText(R.id.no_likes, post.getPostLikes() + " likes");
        baseViewHolder.setText(R.id.no_comments, post.getPostComments() + " comments");

        if (post.getPostTextAPI().isEmpty()) {
            baseViewHolder.setGone(R.id.post_contents, true);
        } else {
            baseViewHolder.setText(R.id.post_contents, Html.fromHtml(post.getPostTextAPI()));
        }

        /*bind profile pic*/
        profile_pic.setImageURI(post.getPublisherInfo().getAvatar());

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
            baseViewHolder.setText(R.id.shared_post_contents, Html.fromHtml(sharedInfo.getPostTextAPI()));
        }

        /*bind profile pic*/
        shared_profile_pic.setImageURI(sharedInfo.getPublisherInfo().getAvatar());

        if (sharedInfo.getPostFile().isEmpty()) {
            baseViewHolder.setGone(R.id.shared_post_image, true);
        } else {
            /*bind post pic*/
            shared_post_image.setImageURI(post.getPostFile());
        }
    }
}