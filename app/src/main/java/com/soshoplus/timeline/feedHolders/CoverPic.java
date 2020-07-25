/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.feedHolders;

import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.chip.Chip;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.postsfeed.post;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

public class CoverPic extends BaseItemProvider<post> {
    
    private static String TAG = "COVER POST : ";
    
    SimpleDraweeView profile_pic, post_image;
    TextView full_name, time_ago, updated_cover,  no_likes, no_comments;
    ProgressBar progressBar;
    Chip likes, comment, post_option;
    
    @Override
    public int getItemViewType () {
        return post.COVER_PIC;
    }
    
    @Override
    public int getLayoutId () {
        return R.layout.profile_cover_post_list_row;
    }
    
    @Override
    public void convert (@NotNull BaseViewHolder baseViewHolder, post post) {
        
        profile_pic = baseViewHolder.findView(R.id.profile_pic);
        full_name = baseViewHolder.findView(R.id.full_name);
        time_ago = baseViewHolder.findView(R.id.time_ago);
    
        no_likes = baseViewHolder.findView(R.id.no_likes);
        no_comments = baseViewHolder.findView(R.id.no_comments);
    
        updated_cover = baseViewHolder.findView(R.id.update_profile_cover);
        post_image = baseViewHolder.findView(R.id.post_image);
        progressBar  = baseViewHolder.findView(R.id.progressBar);
    
        likes = baseViewHolder.findView(R.id.like_btn);
        comment = baseViewHolder.findView(R.id.comment_btn);
        post_option = baseViewHolder.findView(R.id.post_option);
    
        Observable.fromArray(post).subscribe(new Consumer<post>() {
            @Override
            public void accept (post post) throws Throwable {
                Log.d(TAG, "bindCoverPosts: " + "cover image post");
                Log.d(TAG, "bindCoverPosts: " + post.getPostId());
            
                full_name.setText(post.getPublisherInfo().getName());
                time_ago.setText(post.getPostTime());
                updated_cover.setText("updated cover photo");
                no_likes.setText(post.getPostLikes() + " likes");
                no_comments.setText(post.getPostComments() + " comments");
            
                /*getting if post is liked*/
                if (post.isLiked()) {
                    likes.setChipIconResource(R.drawable.ic_liked);
                    likes.setCheckedIconTintResource(R.color.colorPrimary);
                    likes.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
                }
            
                /*bind profile pic*/
                profile_pic.setImageURI(post.getPublisherInfo().getAvatar());
            
                /*bind post pic*/
                post_image.setImageURI(post.getPostFile());
            }
        }).dispose();
    
        /*getting if post is liked*/
        if (post.isLiked()) {
            likes.setChipIconResource(R.drawable.ic_liked);
            likes.setCheckedIconTintResource(R.color.colorPrimary);
            likes.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        }
    }
}
