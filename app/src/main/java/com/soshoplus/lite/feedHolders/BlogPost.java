/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.feedHolders;

import android.text.Html;
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
import coil.transform.RoundedCornersTransformation;

public class BlogPost extends BaseItemProvider<post> {
    
    private static String TAG = "BLOG POST : ";
    
    ImageView profile_pic, article_thumbnail;
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

        ImageLoader imageLoader = Coil.imageLoader(getContext());

        profile_pic = baseViewHolder.findView(R.id.profile_pic);
        article_thumbnail = baseViewHolder.findView(R.id.article_thumbnail);

        like = baseViewHolder.findView(R.id.like_btn);

        baseViewHolder.setText(R.id.full_name, post.getPublisherInfo().getName());
        baseViewHolder.setText(R.id.time_ago, post.getPostTime());
        baseViewHolder.setText(R.id.like_btn, post.getPostLikes());
        baseViewHolder.setText(R.id.comment_btn, post.getPostComments());

        if (post.getPostTextAPI().isEmpty()) {
            baseViewHolder.setGone(R.id.post_contents, true);
        } else {
            baseViewHolder.setText(R.id.post_contents, post.getOrginaltext());
        }

        baseViewHolder.setText(R.id.article_title, Html.fromHtml(post.getBlog().getTitle()));
        baseViewHolder.setText(R.id.article_description, Html.fromHtml(post.getBlog().getDescription()));

        /*bind profile pic*/
        ImageRequest imageRequest = new ImageRequest.Builder(getContext())
                .data(post.getPublisherInfo().getAvatar())
                .placeholder(R.color.light_grey)
                .crossfade(true)
                .transformations(new CircleCropTransformation())
                .target(profile_pic)
                .build();
        imageLoader.enqueue(imageRequest);

        /*bind article thumbnail*/
        imageRequest = new ImageRequest.Builder(getContext())
                .data(post.getBlog().getThumbnail())
                .placeholder(R.color.light_grey)
                .crossfade(true)
                .transformations(new RoundedCornersTransformation(15))
                .target(article_thumbnail)
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
