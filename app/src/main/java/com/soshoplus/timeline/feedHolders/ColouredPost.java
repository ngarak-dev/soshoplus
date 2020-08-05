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
import com.google.android.material.chip.Chip;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.postsfeed.post;

import org.jetbrains.annotations.NotNull;

public class ColouredPost extends BaseItemProvider<post> {
    
    private static String TAG = "COLOURED POST : ";
    
    SimpleDraweeView profile_pic;
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
        baseViewHolder.setText(R.id.no_likes, post.getPostLikes() + " likes");
        baseViewHolder.setText(R.id.no_comments, post.getPostComments() + " comments");

        /*setting colour*/
        switch (post.getColorId()) {
            case "1":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.gradient_one);
                break;
            case "2":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.gradient_two);
                break;
            case "3":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.gradient_three);
                break;
            case "4":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.gradient_four);
                break;
            case "5":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.gradient_five);
                break;
            case "6":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.gradient_six);
                break;
            default:
                /*when the number exceeds*/
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.default_gradient);
                break;
        }

        baseViewHolder.setText(R.id.coloured_post_text, Html.fromHtml(post.getPostTextAPI()));

        /*bind profile pic*/
        profile_pic.setImageURI(post.getPublisherInfo().getAvatar());

        /*if post is liked*/
        if (post.isLiked()) {
            like.setIconResource(R.drawable.ic_liked);
        } else {
            like.setIconResource(R.drawable.ic_like);
        }
    }
}
