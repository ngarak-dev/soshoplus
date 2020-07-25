/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.feedHolders;

import android.text.Html;
import android.util.Log;
import android.widget.LinearLayout;
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

public class ColouredPost extends BaseItemProvider<post> {
    
    private static String TAG = "COLOURED POST : ";
    
    SimpleDraweeView profile_pic;
    TextView full_name, time_ago, post_text,  no_likes, no_comments;
    Chip likes, comment, post_option;
    LinearLayout colour_holder;
    
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
    
        colour_holder = baseViewHolder.findView(R.id.color_holder);
        post_text = baseViewHolder.findView(R.id.coloured_post_text);
    
        profile_pic = baseViewHolder.findView(R.id.profile_pic);
        full_name = baseViewHolder.findView(R.id.full_name);
        time_ago = baseViewHolder.findView(R.id.time_ago);
    
        no_likes = baseViewHolder.findView(R.id.no_likes);
        no_comments = baseViewHolder.findView(R.id.no_comments);
    
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
                no_comments.setText(post.getPostComments() + " comments");;
            
                /*setting colour*/
                switch (post.getColorId()) {
                    case "1":
                        colour_holder.setBackgroundResource(R.drawable.gradient_one);
                        break;
                    case "2":
                        colour_holder.setBackgroundResource(R.drawable.gradient_two);
                        break;
                    case "3":
                        colour_holder.setBackgroundResource(R.drawable.gradient_three);
                        break;
                    case "4":
                        colour_holder.setBackgroundResource(R.drawable.gradient_four);
                        break;
                    case "5":
                        colour_holder.setBackgroundResource(R.drawable.gradient_five);
                        break;
                    case "6":
                        colour_holder.setBackgroundResource(R.drawable.gradient_six);
                        break;
                    default:
                        /*when the number exceeds*/
                        colour_holder.setBackgroundResource(R.drawable.default_gradient);
                        break;
                }
                post_text.setText(Html.fromHtml(post.getPostTextAPI()));
            
                /*getting if post is liked*/
                if (post.isLiked()) {
                    likes.setChipIconResource(R.drawable.ic_liked);
                    likes.setCheckedIconTintResource(R.color.colorPrimary);
                    likes.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
                }
            
                /*bind profile pic*/
                profile_pic.setImageURI(post.getPublisherInfo().getAvatar());
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
