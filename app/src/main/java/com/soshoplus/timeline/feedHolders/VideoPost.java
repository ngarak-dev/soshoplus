/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.feedHolders;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.postsfeed.post;
import com.soshoplus.timeline.utils.glide.glideImageLoader;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

public class VideoPost extends BaseItemProvider<post> {
    
    private static String TAG = "VIDEO POST : ";
    
    SimpleDraweeView profile_pic, image_thumbnail;
    TextView full_name, time_ago, contents,  no_likes, no_comments;
    Chip likes, comment, post_option;
    ImageButton play_button;
    
    @Override
    public int getItemViewType () {
        return post.VIDEO_POST;
    }
    
    @Override
    public int getLayoutId () {
        return R.layout.video_post_list_row;
    }
    
    @Override
    public void convert (@NotNull BaseViewHolder baseViewHolder, post post) {
    
        profile_pic = baseViewHolder.findView(R.id.profile_pic);
        full_name = baseViewHolder.findView(R.id.full_name);
        time_ago = baseViewHolder.findView(R.id.time_ago);
    
        no_likes = baseViewHolder.findView(R.id.no_likes);
        no_comments = baseViewHolder.findView(R.id.no_comments);
    
        contents = baseViewHolder.findView(R.id.post_contents);
        image_thumbnail = baseViewHolder.findView(R.id.video_thumbnail);
    
        likes = baseViewHolder.findView(R.id.like_btn);
        comment = baseViewHolder.findView(R.id.comment_btn);
        post_option = baseViewHolder.findView(R.id.post_option);
    
        play_button = baseViewHolder.findView(R.id.play_button);
    
        Observable.fromArray(post).subscribe(new Consumer<post>() {
            @Override
            public void accept (post post) throws Throwable {
                Log.d(TAG, post.getPostId());
            
                full_name.setText(post.getPublisherInfo().getName());
                time_ago.setText(post.getPostTime());
                no_likes.setText(post.getPostLikes() + " likes");
                no_comments.setText(post.getPostComments() + " comments");
            
                /*getting if post is liked*/
                if (post.isLiked()) {
                    likes.setChipIconResource(R.drawable.ic_liked);
                    likes.setCheckedIconTintResource(R.color.colorPrimary);
                    likes.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
                }
            
                if (!post.getPostTextAPI().isEmpty()) {
                    contents.setText(post.getOrginaltext());
                } else {
                    contents.setVisibility(View.GONE);
                }
            
                /*bind profile pic*/
                profile_pic.setImageURI(post.getPublisherInfo().getAvatar());
            
                /*getting thumbnail*/
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                //give YourVideoUrl below
                retriever.setDataSource(post.getPostFile(), new HashMap<String, String>());
                // this gets frame at 2nd second
                Bitmap thumbnail = retriever.getFrameAtTime(2000000,
                        MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                //use this bitmap image
//                image_thumbnail.set(thumbnail);
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
