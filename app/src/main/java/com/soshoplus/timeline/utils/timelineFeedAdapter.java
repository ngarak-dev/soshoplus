/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.gson.Gson;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.postsfeed.post;
import com.soshoplus.timeline.models.postsfeed.userData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class timelineFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private final List<post> postList;
    private Context context;
    private static String TAG = "timelineFeed Adapter";
    
    /*VIEW TYPES*/
    private static String TYPE_POST = "post";
    private static String TYPE_AD = "ad";
    private static String TYPE_PROFILE_PIC = "profile_picture";
    private static String TYPE_PROFILE_COVER_PIC = "profile_cover_picture";
    
    private static int NORMAL_POST = 1;
    private static int PROFILE_PIC = 2;
    private static int COVER_PIC = 3;
    private static int ADS = 4;
    
    public timelineFeedAdapter (List<post> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }
    
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        
        /*TODO add layout for each view*/
        
       View view;
       if (viewType == NORMAL_POST) {
           view =  LayoutInflater.from(context).inflate(R.layout.timeline_feed_list_row, parent,
                false);
           return new PostViewHolder(view);
       }
       else if (viewType == PROFILE_PIC) {
           view =  LayoutInflater.from(context).inflate(R.layout.timeline_feed_list_row, parent,
                   false);
           return new ProfileViewHolder(view);
       }
       else if (viewType == COVER_PIC) {
           view =  LayoutInflater.from(context).inflate(R.layout.timeline_feed_list_row, parent,
                   false);
           return new CoverViewHolder(view);
       }
       else if (viewType == ADS) {
           view =  LayoutInflater.from(context).inflate(R.layout.timeline_feed_ad, parent,
                   false);
           return new AdViewHolder(view);
       }
       else {
           view =  LayoutInflater.from(context).inflate(R.layout.timeline_feed_list_row, parent,
                   false);
           return new PostViewHolder(view);
       }
    }
    
    @Override
    public void onBindViewHolder (@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        /*getting item view type posts*/
        if (getItemViewType(position) == NORMAL_POST) {
            ((PostViewHolder) viewHolder).bindNormalPosts(postList.get(position));
        }
        else if (getItemViewType(position) == PROFILE_PIC) {
            ((ProfileViewHolder) viewHolder).bindProfilePosts(postList.get(position));
        }
        else if (getItemViewType(position) == COVER_PIC) {
            ((CoverViewHolder) viewHolder).bindCoverPosts(postList.get(position));
        }
        else if (getItemViewType(position) == ADS) {
            ((AdViewHolder) viewHolder).bindAdsPosts(postList.get(position));
        }
        else {
            ((PostViewHolder) viewHolder).bindNormalPosts(postList.get(position));
        }
    }
    
    @Override
    public int getItemCount () {
        return postList.size();
    }
    
    @Override
    public int getItemViewType (int position) {
        /*POST TYPES*/
        if (postList.get(position).getPostType().equals(TYPE_POST)) {
            return NORMAL_POST;
        }
        else if (postList.get(position).getPostType().equals(TYPE_PROFILE_PIC)) {
            return PROFILE_PIC;
        }
        else if (postList.get(position).getPostType().equals(TYPE_PROFILE_COVER_PIC)) {
            return COVER_PIC;
        }
        else if (postList.get(position).getPostType().equals(TYPE_AD)) {
            return ADS;
        }
        else {
            return NORMAL_POST;
        }
    }
    
    /*view holder for ads posts*/
    static class AdViewHolder extends RecyclerView.ViewHolder {
        
        ShapeableImageView profile_pic;
        TextView full_name, location, description, headline;
        ImageView media;
        
        public AdViewHolder (@NonNull View itemView) {
            super(itemView);
            profile_pic = itemView.findViewById(R.id.ad_profile_pic);
            full_name = itemView.findViewById(R.id.ad_full_name);
            location = itemView.findViewById(R.id.ad_location);
            description = itemView.findViewById(R.id.ad_description);
            headline = itemView.findViewById(R.id.ad_headline);
            media = itemView.findViewById(R.id.ad_media);
        }
    
        public void bindAdsPosts (post post) {
            Log.d(TAG, "bindAdsPosts: " + post.getPostType());
            
            /*Converting Object to json data*/
            Gson gson = new Gson();
            String toJson = gson.toJson(post.getUserData());
            /*getting data from json string using pojo class*/
            userData user_data = gson.fromJson(toJson, userData.class);
            
            /*setting data*/
            profile_pic.setShapeAppearanceModel(profile_pic
                    .getShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, 20)
                    .build());
            Picasso.get().load(user_data.getAvatar()).fit().centerCrop().into(profile_pic);
            
            full_name.setText(user_data.getName());
            location.setText(post.getLocation());
            description.setText(Html.fromHtml(post.getDescription()));
            headline.setText(post.getHeadline());
            Picasso.get().load(post.getAdMedia()).fit().centerCrop().into(media);
        }
    }
    
    /*view holder for posts*/
    static class PostViewHolder extends RecyclerView.ViewHolder {
        
        ShapeableImageView profile_pic;
        TextView full_name, time_ago, contents,  no_likes, no_comments, no_shares;
        ImageView post_image;
        Chip likes, comment, share;
        
        public PostViewHolder (@NonNull View itemView) {
            super(itemView);
            profile_pic = itemView.findViewById(R.id.profile_pic);
            full_name = itemView.findViewById(R.id.full_name);
            time_ago = itemView.findViewById(R.id.time_ago);
            
            no_likes = itemView.findViewById(R.id.no_likes);
            no_comments = itemView.findViewById(R.id.no_comments);
            no_shares = itemView.findViewById(R.id.no_shares);
            
            contents = itemView.findViewById(R.id.post_contents);
            post_image = itemView.findViewById(R.id.post_image);
            
            likes = itemView.findViewById(R.id.like_btn);
            comment = itemView.findViewById(R.id.comment_btn);
            share = itemView.findViewById(R.id.share_btn);
        }
    
        public void bindNormalPosts (post post) {
            Log.d(TAG, "bindNormalPosts: " + post.getPostType());
    
            profile_pic.setShapeAppearanceModel(profile_pic
                    .getShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, 20)
                    .build());
            Picasso.get().load(post.getPublisherInfo().getAvatar()).fit().centerCrop().into(profile_pic);
            
            full_name.setText(post.getPublisherInfo().getName());
            time_ago.setText(post.getPostTime());
            no_likes.setText(post.getPostLikes());
            no_comments.setText(post.getPostComments());
            no_shares.setText(post.getPostShares());
            
            /*NO POST IMAGE*/
            if (post.getPostFile().isEmpty()) {
                post_image.setVisibility(View.GONE);
            }
            else {
                String extension = MimeTypeMap.getFileExtensionFromUrl(post.getPostFile());
                if (extension != null) {
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String type = mime.getMimeTypeFromExtension(extension);
    
                    Log.d(TAG, "bindNormalPosts: FILE TYPE" + type);
                }
                
                Picasso.get().load(post.getPostFile()).fit().centerCrop().into(post_image);
            }
            
            if (post.getOrginaltext() == null) {
                contents.setVisibility(View.GONE);
            }
            else {
                contents.setText(Html.fromHtml(post.getPostTextAPI()));
            }
        }
    }
    
    /*view holder for profile change posts*/
    static class ProfileViewHolder extends RecyclerView.ViewHolder {
        
        ShapeableImageView profile_pic;
        TextView full_name, time_ago, contents,  no_likes, no_comments, no_shares;
        ImageView post_image;
        Chip likes, comment, share;
        
        public ProfileViewHolder (@NonNull View itemView) {
            super(itemView);
    
            profile_pic = itemView.findViewById(R.id.profile_pic);
            full_name = itemView.findViewById(R.id.full_name);
            time_ago = itemView.findViewById(R.id.time_ago);
    
            no_likes = itemView.findViewById(R.id.no_likes);
            no_comments = itemView.findViewById(R.id.no_comments);
            no_shares = itemView.findViewById(R.id.no_shares);
    
            contents = itemView.findViewById(R.id.post_contents);
            post_image = itemView.findViewById(R.id.post_image);
    
            likes = itemView.findViewById(R.id.like_btn);
            comment = itemView.findViewById(R.id.comment_btn);
            share = itemView.findViewById(R.id.share_btn);
        }
    
        public void bindProfilePosts (post post) {
            Log.d(TAG, "bindProfilePosts: " + post.getPostType());
            
            profile_pic.setShapeAppearanceModel(profile_pic
                    .getShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, 20)
                    .build());
            Picasso.get().load(post.getPublisherInfo().getAvatar()).fit().centerCrop().into(profile_pic);
    
            full_name.setText(post.getPublisherInfo().getName());
            time_ago.setText(post.getPostTime());
            no_likes.setText(post.getPostLikes());
            no_comments.setText(post.getPostComments());
            no_shares.setText(post.getPostShares());
    
            /*NO POST IMAGE*/
            if (post.getPostFile().isEmpty()) {
                post_image.setVisibility(View.GONE);
            }
            else {
                Picasso.get().load(post.getPostFile()).fit().centerCrop().into(post_image);
            }
    
            if (post.getPostTextAPI().isEmpty()) {
                contents.setVisibility(View.GONE);
            }
            else {
                contents.setText(Html.fromHtml(post.getPostTextAPI()));
            }
        }
    }
    
    /*view holder for cover changed posts*/
    static class CoverViewHolder extends RecyclerView.ViewHolder {
    
        ShapeableImageView profile_pic;
        TextView full_name, time_ago, contents,  no_likes, no_comments, no_shares;
        ImageView post_image;
        Chip likes, comment, share;
        
        public CoverViewHolder (@NonNull View itemView) {
            super(itemView);
    
            profile_pic = itemView.findViewById(R.id.profile_pic);
            full_name = itemView.findViewById(R.id.full_name);
            time_ago = itemView.findViewById(R.id.time_ago);
    
            no_likes = itemView.findViewById(R.id.no_likes);
            no_comments = itemView.findViewById(R.id.no_comments);
            no_shares = itemView.findViewById(R.id.no_shares);
    
            contents = itemView.findViewById(R.id.post_contents);
            post_image = itemView.findViewById(R.id.post_image);
    
            likes = itemView.findViewById(R.id.like_btn);
            comment = itemView.findViewById(R.id.comment_btn);
            share = itemView.findViewById(R.id.share_btn);
        }
    
        public void bindCoverPosts (post post) {
            Log.d(TAG, "bindCoverPosts: " + post.getPostType());
    
            profile_pic.setShapeAppearanceModel(profile_pic
                    .getShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, 20)
                    .build());
            Picasso.get().load(post.getPublisherInfo().getAvatar()).fit().centerCrop().into(profile_pic);
    
            full_name.setText(post.getPublisherInfo().getName());
            time_ago.setText(post.getPostTime());
            no_likes.setText(post.getPostLikes());
            no_comments.setText(post.getPostComments());
            no_shares.setText(post.getPostShares());
    
            /*NO POST IMAGE*/
            if (post.getPostFile().isEmpty()) {
                post_image.setVisibility(View.GONE);
            }
            else {
                Picasso.get().load(post.getPostFile()).fit().centerCrop().into(post_image);
            }
    
            if (post.getPostTextAPI().isEmpty()) {
                contents.setVisibility(View.GONE);
            }
            else {
                contents.setText(Html.fromHtml(post.getPostTextAPI()));
            }
        }
    }
}
