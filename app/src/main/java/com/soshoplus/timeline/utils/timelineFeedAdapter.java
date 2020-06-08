/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils;

import android.content.Context;
import android.net.Uri;
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

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.gson.Gson;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.postsfeed.post;
import com.soshoplus.timeline.models.postsfeed.sharedInfo;
import com.soshoplus.timeline.models.postsfeed.userData;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class timelineFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<post> postList;
    private Context context;
    private static String TAG = "timelineFeed Adapter";
    
    /*VIEW TYPES*/
    private static String TYPE_POST = "post";
    private static String TYPE_AD = "ad";
    private static String TYPE_PROFILE_PIC = "profile_picture";
    private static String TYPE_PROFILE_COVER_PIC = "profile_cover_picture";
    
    /*BY POST TYPE*/
    private static int NORMAL_POST = 1;
    private static int PROFILE_PIC = 2;
    private static int COVER_PIC = 3;
    private static int ADS = 4;
    private static int EMPTY_TYPE = 5;
    
    public timelineFeedAdapter (List<post> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }
    
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        
       View view;
       /*BY POST TYPE*/
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
       else if (viewType == EMPTY_TYPE){
           /*POST TYPE IS EMPTY*/
           view =  LayoutInflater.from(context).inflate(R.layout.shared_post_list_row, parent,
                   false);
           return new SharedPostViewHolder(view);
       }
       else {
           view =  LayoutInflater.from(context).inflate(R.layout.timeline_feed_list_row, parent,
                   false);
           return new SharedPostViewHolder(view);
       }
    }
    
    @Override
    public void onBindViewHolder (@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        /*getting item view type posts*/
        if (getItemViewType(position) == NORMAL_POST) {
            ((PostViewHolder) viewHolder).bindNormalPosts(postList.get(position), context);
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
        else if (getItemViewType(position) == EMPTY_TYPE) {
            ((SharedPostViewHolder) viewHolder).bindSharedPosts(postList.get(position));
        }
        else {
            ((PostViewHolder) viewHolder).bindNormalPosts(postList.get(position), context);
        }
    }
    
    @Override
    public int getItemCount () {
        return postList.size();
    }
    
    @Override
    public int getItemViewType (int position) {
        /*BY POST TYPES*/
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
        else if (postList.get(position).getPostType().equals("")){
            return EMPTY_TYPE;
        }
        return NORMAL_POST;
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
        PlayerView audio_player, video_player;
        SimpleExoPlayer player;
        
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
            audio_player = itemView.findViewById(R.id.post_audio);
            video_player = itemView.findViewById(R.id.post_video);
            
            likes = itemView.findViewById(R.id.like_btn);
            comment = itemView.findViewById(R.id.comment_btn);
            share = itemView.findViewById(R.id.share_btn);
        }
    
        public void bindNormalPosts (post post, Context context) {
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
            
            /*TODO OTHER POSTS
            *  MAP LOCATION
            *  POOLS
            *  GIF
            *  FEELINGS
            *  COLOR
            *  SELL PRODUCT*/
            
            /*NO POST IMAGE*/
            if (post.getPostFile().isEmpty()) {
                post_image.setVisibility(View.GONE);
                audio_player.setVisibility(View.GONE);
                video_player.setVisibility(View.GONE);
            }
            else {
                String extension = MimeTypeMap.getFileExtensionFromUrl(post.getPostFile());
                if (extension != null) {
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String type = mime.getMimeTypeFromExtension(extension);
                    
                    if (Objects.equals(type, "audio/mpeg")) {
                        post_image.setVisibility(View.GONE);
                        video_player.setVisibility(View.GONE);
                        /*TODO PLAY AUDIO*/
                        audio_player.setVisibility(View.VISIBLE);
                        player = new SimpleExoPlayer.Builder(context).build();
                        audio_player.setPlayer(player);
                        
                        Uri uri = Uri.parse(post.getPostFile());
                        // Produces DataSource instances through which media data is loaded.
                        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                                Util.getUserAgent(context, "soshoplus"));
                        // This is the MediaSource representing the media to be played.
                        MediaSource mediaSource =
                                new ProgressiveMediaSource.Factory(dataSourceFactory)
                                        .createMediaSource(uri);
                        // Prepare the player with the source.
                        player.prepare(mediaSource);
                        
                        
                    }
                    else if (Objects.equals(type, "image/jpeg")){
                        audio_player.setVisibility(View.GONE);
                        video_player.setVisibility(View.GONE);
                        Picasso.get().load(post.getPostFile()).fit().centerCrop().into(post_image);
                    }
                    else if (Objects.equals(type, "video/mp4")) {
                        audio_player.setVisibility(View.GONE);
                        post_image.setVisibility(View.GONE);
                        /*TODO Play Video*/
//                        video_player.setVisibility(View.VISIBLE);
//                        player = new SimpleExoPlayer.Builder(context).build();
//                        video_player.setPlayer(player);
//
//                        Uri uri = Uri.parse(post.getPostFile());
//                        // Produces DataSource instances through which media data is loaded.
//                        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
//                                Util.getUserAgent(context, "soshoplus"));
//                        // This is the MediaSource representing the media to be played.
//                        MediaSource mediaSource =
//                                new ProgressiveMediaSource.Factory(dataSourceFactory)
//                                        .createMediaSource(uri);
//                        // Prepare the player with the source.
//                        player.prepare(mediaSource);
                        
                    }
                    else {
                        audio_player.setVisibility(View.GONE);
                        video_player.setVisibility(View.GONE);
                        post_image.setVisibility(View.GONE);
                    }
    
                    Log.d(TAG, "bindNormalPosts: FILE TYPE" + type);
                }
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
   
            Picasso.get().load(post.getPostFile()).placeholder(R.drawable.ic_image_placeholder).fit().centerCrop().into(post_image);
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
    
            Picasso.get().load(post.getPostFile()).placeholder(R.drawable.ic_image_placeholder).fit().centerCrop().into(post_image);
        }
    }
    
    static class SharedPostViewHolder extends RecyclerView.ViewHolder {
    
        ShapeableImageView profile_pic, shared_profile_pic;
        TextView full_name, shared_full_name, time_ago, shared_time_ago
        , contents, shared_contents,  no_likes, no_comments, no_shares;
        ImageView shared_post_image;
        Chip likes, comment, share;
    
        public SharedPostViewHolder (View itemView) {
            super(itemView);
    
            profile_pic = itemView.findViewById(R.id.profile_pic);
            shared_profile_pic = itemView.findViewById(R.id.shared_profile_pic);
            full_name = itemView.findViewById(R.id.full_name);
            shared_full_name = itemView.findViewById(R.id.shared_full_name);
            time_ago = itemView.findViewById(R.id.time_ago);
            shared_time_ago = itemView.findViewById(R.id.shared_time_ago);
    
            no_likes = itemView.findViewById(R.id.no_likes);
            no_comments = itemView.findViewById(R.id.no_comments);
            no_shares = itemView.findViewById(R.id.no_shares);
    
            contents = itemView.findViewById(R.id.post_contents);
            shared_contents = itemView.findViewById(R.id.shared_post_contents);
            shared_post_image = itemView.findViewById(R.id.shared_post_image);
    
            likes = itemView.findViewById(R.id.like_btn);
            comment = itemView.findViewById(R.id.comment_btn);
            share = itemView.findViewById(R.id.share_btn);
        }
    
        public void bindSharedPosts (post post) {
            Log.d(TAG, "bindSharedPosts: " + "Shared Post");
    
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
            
            if (!post.getPostTextAPI().isEmpty()) {
                contents.setText(post.getOrginaltext());
            } else {
                contents.setVisibility(View.GONE);
            }
            
            /*shared data*/
            /*Converting Object to json data*/
            Gson gson = new Gson();
            String toJson = gson.toJson(post.getSharedInfo());
            /*getting data from json string using pojo class*/
            sharedInfo sharedInfo = gson.fromJson(toJson, sharedInfo.class);
            
            shared_profile_pic.setShapeAppearanceModel(shared_profile_pic
                    .getShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, 20)
                    .build());
            Picasso.get().load(sharedInfo.getPublisherInfo().getAvatar()).fit().centerCrop().into(shared_profile_pic);
    
            shared_full_name.setText(sharedInfo.getPublisherInfo().getName());
            shared_time_ago.setText(sharedInfo.getPostTime());
            shared_contents.setText(Html.fromHtml(sharedInfo.getPostTextAPI()));
    
            Picasso.get().load(sharedInfo.getPostFile()).placeholder(R.drawable.ic_image_placeholder).fit().centerCrop().into(shared_post_image);
        }
    }
}
