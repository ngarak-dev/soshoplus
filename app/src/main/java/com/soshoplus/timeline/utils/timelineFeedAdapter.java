/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.postsfeed.post;

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
        public AdViewHolder (@NonNull View itemView) {
            super(itemView);
        }
    
        public void bindAdsPosts (post post) {
            Log.d(TAG, "bindAdsPosts: " + post.getPostType());
            Log.d(TAG, "bindAdsPosts: " + post.getUrl());
            Log.d(TAG, "bindAdsPosts: " + post.getHeadline());
            Log.d(TAG, "bindAdsPosts: " + post.getLocation());
            Log.d(TAG, "bindAdsPosts: " + post.getAdMedia());
            Log.d(TAG, "bindAdsPosts: " + "............../");
        }
    }
    
    /*view holder for posts*/
    static class PostViewHolder extends RecyclerView.ViewHolder {
        public PostViewHolder (@NonNull View itemView) {
            super(itemView);
        }
    
        public void bindNormalPosts (post post) {
            Log.d(TAG, "bindNormalPosts: " + post.getPostType());
            Log.d(TAG, "bindNormalPosts: " + post.getPostComments());
            Log.d(TAG, "bindNormalPosts: " + post.getPostShares());
            Log.d(TAG, "bindNormalPosts: " + post.getPostLikes());
            Log.d(TAG, "bindNormalPosts: " + post.getOrginaltext());
            Log.d(TAG, "bindNormalPosts: " + post.getPostTime());
            Log.d(TAG, "bindNormalPosts: " + post.getPostFile());
            Log.d(TAG, "bindNormalPosts: " + post.getPublisherInfo().getAvatar());
            Log.d(TAG, "bindAdsPosts: " + "............../");
        }
    }
    
    /*view holder for profile change posts*/
    static class ProfileViewHolder extends RecyclerView.ViewHolder {
        public ProfileViewHolder (@NonNull View itemView) {
            super(itemView);
        }
    
        public void bindProfilePosts (post post) {
            Log.d(TAG, "bindProfilePosts: " + post.getPostType());
            Log.d(TAG, "bindProfilePosts: " + post.getPostComments());
            Log.d(TAG, "bindProfilePosts: " + post.getPostShares());
            Log.d(TAG, "bindProfilePosts: " + post.getPostLikes());
            Log.d(TAG, "bindProfilePosts: " + post.getOrginaltext());
            Log.d(TAG, "bindProfilePosts: " + post.getPostTime());
            Log.d(TAG, "bindProfilePosts: " + post.getPostFile());
            Log.d(TAG, "bindProfilePosts: " + post.getPublisherInfo().getAvatar());
            Log.d(TAG, "bindAdsPosts: " + "............../");
        }
    }
    
    /*view holder for cover changed posts*/
    static class CoverViewHolder extends RecyclerView.ViewHolder {
        public CoverViewHolder (@NonNull View itemView) {
            super(itemView);
        }
    
        public void bindCoverPosts (post post) {
            Log.d(TAG, "bindCoverPosts: " + post.getPostType());
            Log.d(TAG, "bindCoverPosts: " + post.getPostComments());
            Log.d(TAG, "bindCoverPosts: " + post.getPostShares());
            Log.d(TAG, "bindCoverPosts: " + post.getPostLikes());
            Log.d(TAG, "bindCoverPosts: " + post.getOrginaltext());
            Log.d(TAG, "bindCoverPosts: " + post.getPostTime());
            Log.d(TAG, "bindCoverPosts: " + post.getPostFile());
            Log.d(TAG, "bindCoverPosts: " + post.getPublisherInfo().getAvatar());
            Log.d(TAG, "bindAdsPosts: " + "............../");
        }
    }
}
