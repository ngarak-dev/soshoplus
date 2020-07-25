/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.feedHolders;

import android.text.Html;
import android.util.Log;
import android.view.View;
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

public class AudioPost extends BaseItemProvider<post> {
    
    private static String TAG = "AUDIO POST : ";
    
    SimpleDraweeView profile_pic;
    TextView full_name, time_ago, contents,  no_likes, no_comments;
    Chip likes, comment, post_option;
    Chip play,  stop;
    
    @Override
    public int getItemViewType () {
        return post.AUDIO_POST;
    }
    
    @Override
    public int getLayoutId () {
        return R.layout.audio_post_list_row;
    }
    
    @Override
    public void convert (@NotNull BaseViewHolder baseViewHolder, post post) {
    
        profile_pic = baseViewHolder.findView(R.id.profile_pic);
        full_name = baseViewHolder.findView(R.id.full_name);
        time_ago = baseViewHolder.findView(R.id.time_ago);
    
        no_likes = baseViewHolder.findView(R.id.no_likes);
        no_comments = baseViewHolder.findView(R.id.no_comments);
    
        contents = baseViewHolder.findView(R.id.post_contents);
        play = baseViewHolder.findView(R.id.play_button);
        stop = baseViewHolder.findView(R.id.stop_button);
    
        likes = baseViewHolder.findView(R.id.like_btn);
        comment = baseViewHolder.findView(R.id.comment_btn);
        post_option = baseViewHolder.findView(R.id.post_option);
    
        Observable.fromArray(post).subscribe(new Consumer<post>() {
            @Override
            public void accept (post post) throws Throwable {
                Log.d(TAG, post.getPostId());
            
                full_name.setText(post.getPublisherInfo().getName());
                time_ago.setText(post.getPostTime());
                no_likes.setText(post.getPostLikes() + " likes");
                no_comments.setText(post.getPostComments() + " comments");
            
                if (!post.getPostTextAPI().isEmpty()) {
                    contents.setText(Html.fromHtml(post.getPostTextAPI()));
                } else {
                    contents.setVisibility(View.GONE);
                }
            
                /*getting if post is liked*/
                if (post.isLiked()) {
                    likes.setChipIconResource(R.drawable.ic_liked);
                    likes.setCheckedIconTintResource(R.color.colorPrimary);
                    likes.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
                }
            
                /*bind profile pic*/
                Observable.just(post.getPublisherInfo().getAvatar()).subscribe(new Consumer<String>() {
                    @Override
                    public void accept (String avatar_pic) throws Throwable {
                        profile_pic.setImageURI(avatar_pic);
                    }
                }).dispose();
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
