/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.feedHolders;

import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.button.MaterialButton;
import com.soshoplus.lite.R;
import com.soshoplus.lite.models.postsfeed.post;

import org.jetbrains.annotations.NotNull;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.transform.CircleCropTransformation;

public class ColouredPost extends BaseItemProvider<post> {
    
    private static String TAG = "COLOURED POST : ";
    
    ImageView profile_pic;
    MaterialButton like;
    
    @Override
    public int getItemViewType () {
        return post.COLOURED_POST;
    }
    
    @Override
    public int getLayoutId () {
        return R.layout.coloured_post_list_row;
    }
    
    @Override
    public void convert (@NotNull BaseViewHolder baseViewHolder, post post) {
        Log.d(TAG, post.getPostId());

        profile_pic = baseViewHolder.findView(R.id.profile_pic);

        like = baseViewHolder.findView(R.id.like_btn);

        baseViewHolder.setText(R.id.full_name, post.getPublisherInfo().getName());
        baseViewHolder.setText(R.id.time_ago, post.getPostTime());
        baseViewHolder.setText(R.id.like_btn, post.getPostLikes());
        baseViewHolder.setText(R.id.comment_btn, post.getPostComments());

        /*setting colour*/
        switch (post.getColorId()) {
            case "1":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.post_gradient_one);
                break;
            case "2":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.post_gradient_two);
                break;
            case "3":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.post_gradient_three);
                break;
            case "4":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.post_gradient_four);
                break;
            case "5":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.post_gradient_five);
                break;
            case "6":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.post_gradient_six);
                break;
            default:
                /*when the number exceeds*/
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.post_default_gradient);
                break;
        }

        baseViewHolder.setText(R.id.coloured_post_text, post.getPostText());

        /*bind profile pic*/
        ImageLoader imageLoader = Coil.imageLoader(getContext());
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
    }
}
