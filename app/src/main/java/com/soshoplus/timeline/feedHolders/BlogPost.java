/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.feedHolders;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.ColorRes;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.postsfeed.post;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

public class BlogPost extends BaseItemProvider<post> {
    
    private static String TAG = "BLOG POST : ";
    
    SimpleDraweeView profile_pic, article_thumbnail;
    MaterialButton like;
    
    @Override
    public int getItemViewType () {
        return post.BLOG_POST;
    }
    
    @Override
    public int getLayoutId () {
        return R.layout.blog_post_list_row;
    }
    
    @Override
    public void convert (@NotNull BaseViewHolder baseViewHolder, post post) {
        Log.d(TAG, post.getPostId());

        profile_pic = baseViewHolder.findView(R.id.profile_pic);
        article_thumbnail = baseViewHolder.findView(R.id.article_thumbnail);

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

        baseViewHolder.setText(R.id.article_title, Html.fromHtml(post.getBlog().getTitle()));
        baseViewHolder.setText(R.id.article_description, Html.fromHtml(post.getBlog().getDescription()));

        /*bind profile pic*/
        profile_pic.setImageURI(post.getPublisherInfo().getAvatar());

        /*bind article thumbnail*/
        article_thumbnail.setImageURI(post.getBlog().getThumbnail());

        /*if post is liked*/
        if (post.isLiked()) {
            like.setIconResource(R.drawable.ic_liked);
        } else {
            like.setIconResource(R.drawable.ic_like);
        }
    }
}
