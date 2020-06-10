/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.gson.Gson;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.postsfeed.post;
import com.soshoplus.timeline.models.postsfeed.sharedInfo;
import com.soshoplus.timeline.models.postsfeed.userData;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class timelineFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    
    private final List<post> postList;
    private Context context;
    private final onClickListener clickListener;
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
    /*COLOURED POST*/
    private static int COLOURED_POST = 6;
    /*VIDEO POST*/
    private static int VIDEO_POST = 7;
    /*IMAGE POST*/
    private static int IMAGE_POST = 8;
    /*AUDIO POST*/
    private static int AUDIO_POST = 9;
    /*BLOG POST*/
    private static int BLOG_POST = 10;
    /*MAP POST*/
    private static int MAP_POST = 11;
    
    public timelineFeedAdapter (List<post> postList, Context context, onClickListener clickListener) {
        this.postList = postList;
        this.context = context;
        this.clickListener = clickListener;
    }
    
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        
       View view;
       /*BY POST TYPE*/
//       if (viewType == NORMAL_POST) {
//           view =  LayoutInflater.from(context).inflate(R.layout.timeline_feed_list_row, parent,
//                false);
//           return new PostViewHolder(view);
//       }
       if (viewType == PROFILE_PIC) {
           view =  LayoutInflater.from(context).inflate(R.layout.profile_cover_post_list_row, parent,
                   false);
           return new ProfileViewHolder(view);
       }
       else if (viewType == COVER_PIC) {
           view =  LayoutInflater.from(context).inflate(R.layout.profile_cover_post_list_row, parent,
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
       else if (viewType == COLOURED_POST){
           view =  LayoutInflater.from(context).inflate(R.layout.coloured_post_list_row, parent,
                   false);
           return new ColouredPostViewHolder(view);
       }
       else if (viewType == VIDEO_POST) {
           view =  LayoutInflater.from(context).inflate(R.layout.video_post_list_row, parent,
                   false);
           return new VideoPostViewHolder(view);
       }
       else if (viewType == IMAGE_POST) {
           view =  LayoutInflater.from(context).inflate(R.layout.image_post_list_row, parent,
                   false);
           return new ImagePostViewHolder(view);
       }
       else if (viewType == AUDIO_POST) {
           view =  LayoutInflater.from(context).inflate(R.layout.audio_post_list_row, parent,
                   false);
           return new AudioPostViewHolder(view);
       }
       else if (viewType == BLOG_POST) {
           view =  LayoutInflater.from(context).inflate(R.layout.blog_post_list_row, parent,
                   false);
           return new BlogPostViewHolder(view);
       }
       else if (viewType == MAP_POST) {
           view =  LayoutInflater.from(context).inflate(R.layout.map_post_list_row, parent,
                   false);
           return new MapPostViewHolder(view);
       }
       else {
           view =  LayoutInflater.from(context).inflate(R.layout.default_post_list_row, parent,
                   false);
           return new PostViewHolder(view);
       }
    }
    
    @Override
    public void onBindViewHolder (@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        /*getting item view type posts*/
//        if (getItemViewType(position) == NORMAL_POST) {
//            ((PostViewHolder) viewHolder).bindNormalPosts(postList.get(position), context);
//        }
        if (getItemViewType(position) == PROFILE_PIC) {
            ((ProfileViewHolder) viewHolder).bindProfilePosts(postList.get(position));
        }
        else if (getItemViewType(position) == COVER_PIC) {
            ((CoverViewHolder) viewHolder).bindCoverPosts(postList.get(position));
        }
        else if (getItemViewType(position) == ADS) {
            ((AdViewHolder) viewHolder).bindAdsPosts(postList.get(position));
        }
        /*SHARED POST*/
        else if (getItemViewType(position) == EMPTY_TYPE) {
            ((SharedPostViewHolder) viewHolder).bindSharedPosts(postList.get(position));
        }
        /*COLOURED POST*/
        else if (getItemViewType(position) == COLOURED_POST){
            ((ColouredPostViewHolder) viewHolder).bindColouredPosts(postList.get(position));
        }
        /*VIDEO POST*/
        else if (getItemViewType(position) == VIDEO_POST) {
            ((VideoPostViewHolder) viewHolder).bindVideoPosts(postList.get(position), clickListener);
        }
        /*SINGLE IMAGE POST*/
        else if (getItemViewType(position) == IMAGE_POST) {
            ((ImagePostViewHolder) viewHolder).bindImagePosts(postList.get(position));
        }
        /*AUDIO POST*/
        else if (getItemViewType(position) == AUDIO_POST) {
            ((AudioPostViewHolder) viewHolder).bindAudioPosts(postList.get(position), context, clickListener);
        }
        /*BLOG POST*/
        else if (getItemViewType(position) == BLOG_POST) {
            ((BlogPostViewHolder) viewHolder).bindBlogPosts(postList.get(position));
        }
        /*MAP POST*/
        else if (getItemViewType(position) == MAP_POST) {
            ((MapPostViewHolder) viewHolder).bindMapPosts(postList.get(position));
        }
        /*DEFAULT RETURN*/
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
//        if (postList.get(position).getPostType().equals(TYPE_POST)) {
//            return NORMAL_POST;
//        }
        
        /*checking file extension*/
        String extension = MimeTypeMap.getFileExtensionFromUrl(postList.get(position).getPostFile());
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = mime.getMimeTypeFromExtension(extension);
    
        if (postList.get(position).getPostType().equals(TYPE_PROFILE_PIC)) {
            return PROFILE_PIC;
        }
        else if (postList.get(position).getPostType().equals(TYPE_PROFILE_COVER_PIC)) {
            return COVER_PIC;
        }
        else if (postList.get(position).getPostType().equals(TYPE_AD)) {
            return ADS;
        }
        /*SHARED POST*/
        else if (postList.get(position).getPostType().equals("")){
            return EMPTY_TYPE;
        }
        /*COLOURED POST*/
        else if (!postList.get(position).getColorId().equals("0")) {
            return COLOURED_POST;
        }
        /*VIDEO POST*/
        else if (Objects.equals(type, "video/mp4")) {
            return VIDEO_POST;
        }
        /*SINGLE IMAGE POST*/
        else if (Objects.equals(type, "image/jpeg")) {
            return IMAGE_POST;
        }
        /*AUDIO POST*/
        else if (Objects.equals(type, "audio/mpeg")) {
            return AUDIO_POST;
        }
        /*BLOG POST*/
        else if (!postList.get(position).getBlogId().equals("0")) {
            return BLOG_POST;
        }
        /*MAP POST*/
        else if (!postList.get(position).getPostMap().isEmpty()) {
            return MAP_POST;
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
            Log.d(TAG, "bindAdsPosts: " + "advertisement");
            
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
            *  SELL PRODUCT*/
            
            if (post.getPostFile().isEmpty()) {
                post_image.setVisibility(View.GONE);
            } else {
                Picasso.get().load(post.getPostFile()).fit().centerCrop().placeholder(R.drawable.ic_image_placeholder).into(post_image);
            }
            if (post.getOrginaltext() == null) {
                contents.setVisibility(View.GONE);
            }
            contents.setText(Html.fromHtml(post.getPostTextAPI()));
        }
    }
    
    /*view holder for profile change posts*/
    static class ProfileViewHolder extends RecyclerView.ViewHolder {
        
        ShapeableImageView profile_pic;
        TextView full_name, time_ago, profile_updated,  no_likes, no_comments, no_shares;
        ImageView post_image;
        Chip likes, comment, share;
        
        public ProfileViewHolder (@NonNull View itemView) {
            super(itemView);
    
            profile_pic = itemView.findViewById(R.id.profile_pic);
            full_name = itemView.findViewById(R.id.full_name);
            time_ago = itemView.findViewById(R.id.time_ago);
            
            profile_updated = itemView.findViewById(R.id.update_profile_cover);
    
            no_likes = itemView.findViewById(R.id.no_likes);
            no_comments = itemView.findViewById(R.id.no_comments);
            no_shares = itemView.findViewById(R.id.no_shares);

            post_image = itemView.findViewById(R.id.post_image);
    
            likes = itemView.findViewById(R.id.like_btn);
            comment = itemView.findViewById(R.id.comment_btn);
            share = itemView.findViewById(R.id.share_btn);
        }
    
        public void bindProfilePosts (post post) {
            Log.d(TAG, "bindProfilePosts: " + "profile image post");
            profile_pic.setShapeAppearanceModel(profile_pic
                    .getShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, 20)
                    .build());
            Picasso.get().load(post.getPublisherInfo().getAvatar()).fit().centerCrop().into(profile_pic);
    
            full_name.setText(post.getPublisherInfo().getName());
            time_ago.setText(post.getPostTime());
            profile_updated.setText("updated profile photo");
            no_likes.setText(post.getPostLikes());
            no_comments.setText(post.getPostComments());
            no_shares.setText(post.getPostShares());
   
            Picasso.get().load(post.getPostFile()).placeholder(R.drawable.ic_image_placeholder).fit().centerCrop().into(post_image);
        }
    }
    
    /*view holder for cover changed posts*/
    static class CoverViewHolder extends RecyclerView.ViewHolder {
    
        ShapeableImageView profile_pic;
        TextView full_name, time_ago, updated_cover,  no_likes, no_comments, no_shares;
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
    
            updated_cover = itemView.findViewById(R.id.update_profile_cover);
            post_image = itemView.findViewById(R.id.post_image);
    
            likes = itemView.findViewById(R.id.like_btn);
            comment = itemView.findViewById(R.id.comment_btn);
            share = itemView.findViewById(R.id.share_btn);
        }
    
        public void bindCoverPosts (post post) {
            Log.d(TAG, "bindCoverPosts: " + "cover image post");
            profile_pic.setShapeAppearanceModel(profile_pic
                    .getShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, 20)
                    .build());
            Picasso.get().load(post.getPublisherInfo().getAvatar()).fit().centerCrop().into(profile_pic);
    
            full_name.setText(post.getPublisherInfo().getName());
            time_ago.setText(post.getPostTime());
            updated_cover.setText("updated cover photo");
            no_likes.setText(post.getPostLikes());
            no_comments.setText(post.getPostComments());
            no_shares.setText(post.getPostShares());
    
            Picasso.get().load(post.getPostFile()).placeholder(R.drawable.ic_image_placeholder).fit().centerCrop().into(post_image);
        }
    }
    
    /*view holder for shared post*/
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
            Log.d(TAG, "bindSharedPosts: " + "single image shared post");
    
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
            
            if (sharedInfo.getPostFile().isEmpty()) {
                shared_post_image.setVisibility(View.GONE);
            } else {
                Picasso.get().load(sharedInfo.getPostFile()).placeholder(R.drawable.ic_image_placeholder).fit().centerCrop().into(shared_post_image);
            }
        }
    }
    
    /*view holder for coloured post*/
    static class ColouredPostViewHolder extends RecyclerView.ViewHolder {
    
        ShapeableImageView profile_pic;
        TextView full_name, time_ago, post_text,  no_likes, no_comments, no_shares;
        Chip likes, comment, share;
        LinearLayout colour_holder;
        
        public ColouredPostViewHolder (View itemView) {
            super(itemView);
            colour_holder = itemView.findViewById(R.id.color_holder);
            post_text = itemView.findViewById(R.id.coloured_post_text);
    
            profile_pic = itemView.findViewById(R.id.profile_pic);
            full_name = itemView.findViewById(R.id.full_name);
            time_ago = itemView.findViewById(R.id.time_ago);
    
            no_likes = itemView.findViewById(R.id.no_likes);
            no_comments = itemView.findViewById(R.id.no_comments);
            no_shares = itemView.findViewById(R.id.no_shares);
    
            likes = itemView.findViewById(R.id.like_btn);
            comment = itemView.findViewById(R.id.comment_btn);
            share = itemView.findViewById(R.id.share_btn);
        }
    
        public void bindColouredPosts (post post) {
            Log.d(TAG, "bindColouredPosts: " + "coloured post");
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
        }
    }
    
    /*view holder for video post*/
    static class VideoPostViewHolder extends RecyclerView.ViewHolder {
    
        ShapeableImageView profile_pic;
        TextView full_name, time_ago, contents,  no_likes, no_comments, no_shares;
        ImageView image_thumbnail;
        Chip likes, comment, share;
        ImageButton play_button;
        
        public VideoPostViewHolder (View itemView) {
            super(itemView);
    
            profile_pic = itemView.findViewById(R.id.profile_pic);
            full_name = itemView.findViewById(R.id.full_name);
            time_ago = itemView.findViewById(R.id.time_ago);
    
            no_likes = itemView.findViewById(R.id.no_likes);
            no_comments = itemView.findViewById(R.id.no_comments);
            no_shares = itemView.findViewById(R.id.no_shares);
    
            contents = itemView.findViewById(R.id.post_contents);
            image_thumbnail = itemView.findViewById(R.id.video_thumbnail);
    
            likes = itemView.findViewById(R.id.like_btn);
            comment = itemView.findViewById(R.id.comment_btn);
            share = itemView.findViewById(R.id.share_btn);
            
            play_button = itemView.findViewById(R.id.play_button);
        }
    
        public void bindVideoPosts (post post, onClickListener clickListener) {
            Log.d(TAG, "bindVideoPosts: " + "single video post");
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
    
            /*getting thumbnail*/
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            //give YourVideoUrl below
            retriever.setDataSource(post.getPostFile(), new HashMap<String, String>());
            // this gets frame at 2nd second
            Bitmap thumbnail = retriever.getFrameAtTime(2000000,
                    MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            //use this bitmap image
            image_thumbnail.setImageBitmap(thumbnail);
            
            play_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    clickListener.onVideoClickPlay(post.getPostFile());
                }
            });
        }
    }
    
    public interface onClickListener {
        /*on play click*/
        void onVideoClickPlay (String postFile);
        void onAudioClickPlay (String postFile, Chip play, Chip pause);
    }
    
    /*view holder for single image post*/
    static class ImagePostViewHolder extends RecyclerView.ViewHolder {
    
        ShapeableImageView profile_pic;
        TextView full_name, time_ago, contents,  no_likes, no_comments, no_shares;
        ImageView post_image;
        Chip likes, comment, share;
        
        public ImagePostViewHolder (View itemView) {
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
    
        public void bindImagePosts (post post) {
            Log.d(TAG, "bindImagePosts: " + "single image post");
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
            
            if (post.getPostTextAPI().isEmpty()) {
                contents.setVisibility(View.GONE);
            }
            contents.setText(Html.fromHtml(post.getPostTextAPI()));
            
            Picasso.get().load(post.getPostFile()).fit().centerCrop().into(post_image);
        }
    }
    
    /*view holder for audio post*/
    static class AudioPostViewHolder extends RecyclerView.ViewHolder {
    
        ShapeableImageView profile_pic;
        TextView full_name, time_ago, contents,  no_likes, no_comments, no_shares;;
        Chip likes, comment, share;
        Chip play,  stop;
        
        public AudioPostViewHolder (View itemView) {
            super(itemView);
    
            profile_pic = itemView.findViewById(R.id.profile_pic);
            full_name = itemView.findViewById(R.id.full_name);
            time_ago = itemView.findViewById(R.id.time_ago);
    
            no_likes = itemView.findViewById(R.id.no_likes);
            no_comments = itemView.findViewById(R.id.no_comments);
            no_shares = itemView.findViewById(R.id.no_shares);
    
            contents = itemView.findViewById(R.id.post_contents);
            play = itemView.findViewById(R.id.play_button);
            stop = itemView.findViewById(R.id.stop_button);
    
            likes = itemView.findViewById(R.id.like_btn);
            comment = itemView.findViewById(R.id.comment_btn);
            share = itemView.findViewById(R.id.share_btn);
        }
    
        public void bindAudioPosts (post post, Context context, onClickListener clickListener) {
            Log.d(TAG, "bindAudioPosts: " + "audio post");
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
                contents.setText(Html.fromHtml(post.getPostTextAPI()));
            } else {
                contents.setVisibility(View.GONE);
            }
            
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View view) {
                    clickListener.onAudioClickPlay(post.getPostFile(), play,
                            stop);
                }
            });
        }
    }
    
    /*view holder for blog & articles*/
    static class BlogPostViewHolder extends RecyclerView.ViewHolder {
    
        ShapeableImageView profile_pic;
        TextView full_name, time_ago, contents, article_title,
                article_description, no_likes, no_comments, no_shares;
        ImageView article_thumbnail;
        Chip likes, comment, share;
        
        public BlogPostViewHolder (View itemView) {
            super(itemView);
    
            profile_pic = itemView.findViewById(R.id.profile_pic);
            full_name = itemView.findViewById(R.id.full_name);
            time_ago = itemView.findViewById(R.id.time_ago);
    
            no_likes = itemView.findViewById(R.id.no_likes);
            no_comments = itemView.findViewById(R.id.no_comments);
            no_shares = itemView.findViewById(R.id.no_shares);
    
            contents = itemView.findViewById(R.id.post_contents);
            article_thumbnail = itemView.findViewById(R.id.article_thumbnail);
            article_title = itemView.findViewById(R.id.article_title);
            article_description = itemView.findViewById(R.id.article_description);
    
            likes = itemView.findViewById(R.id.like_btn);
            comment = itemView.findViewById(R.id.comment_btn);
            share = itemView.findViewById(R.id.share_btn);
        }
    
        public void bindBlogPosts (post post) {
            Log.d(TAG, "bindBlogPosts: " + "articles and blogs");
            
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
    
            if (post.getPostTextAPI().isEmpty()) {
                contents.setVisibility(View.GONE);
            }
            contents.setText(Html.fromHtml(post.getPostTextAPI()));
            
            article_title.setText(Html.fromHtml(post.getBlog().getTitle()));
            article_description.setText(Html.fromHtml(post.getBlog().getDescription()));
            
            Picasso.get().load(post.getBlog().getThumbnail()).fit().centerCrop().placeholder(R.drawable.ic_image_placeholder).into(article_thumbnail);
            
        }
    }
    
    static class MapPostViewHolder extends RecyclerView.ViewHolder {
    
        ShapeableImageView profile_pic;
        TextView full_name, time_ago, contents,  no_likes, no_comments, no_shares, location;
        Chip likes, comment, share;
        
        public MapPostViewHolder (View itemView) {
            super(itemView);
    
            profile_pic = itemView.findViewById(R.id.profile_pic);
            full_name = itemView.findViewById(R.id.full_name);
            time_ago = itemView.findViewById(R.id.time_ago);
    
            no_likes = itemView.findViewById(R.id.no_likes);
            no_comments = itemView.findViewById(R.id.no_comments);
            no_shares = itemView.findViewById(R.id.no_shares);
    
            contents = itemView.findViewById(R.id.post_contents);
            location = itemView.findViewById(R.id.location_content);
    
            likes = itemView.findViewById(R.id.like_btn);
            comment = itemView.findViewById(R.id.comment_btn);
            share = itemView.findViewById(R.id.share_btn);
        }
    
        public void bindMapPosts (post post) {
    
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
                contents.setText(Html.fromHtml(post.getPostTextAPI()));
            } else {
                contents.setVisibility(View.GONE);
            }
            
            location.setText(post.getPostMap());
        }
    }
}
