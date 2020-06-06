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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.postsfeed.post;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class timelineFeedAdapter extends RecyclerView.Adapter<timelineFeedAdapter.TimelineFeedHolder>  {

    private final List<post> postList;
    private Context context;
    private static String TAG = "Suggested Groups";
    
    public timelineFeedAdapter (List<post> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }
    
    @NonNull
    @Override
    public timelineFeedAdapter.TimelineFeedHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.timeline_feed_list_row, parent,
                false);
        return new TimelineFeedHolder(view);
    }
    
    @Override
    public void onBindViewHolder (@NonNull timelineFeedAdapter.TimelineFeedHolder holder, int position) {
        holder.bind(postList.get(position));
    }
    
    @Override
    public int getItemCount () {
        return postList.size();
    }
    
    @Override
    public int getItemViewType (int position) {
        return super.getItemViewType(position);
    }
    
    public class TimelineFeedHolder extends RecyclerView.ViewHolder{
    
        ShapeableImageView profile_pic;
        TextView full_name, time_ago;
        ImageView post_pic;
        Chip no_shares, no_comments, no_likes;
        
        public TimelineFeedHolder (@NonNull View itemView) {
            super(itemView);
            
            profile_pic = itemView.findViewById(R.id.profile_pic);
            full_name = itemView.findViewById(R.id.full_name);
            no_shares = itemView.findViewById(R.id.no_shares);
            no_comments = itemView.findViewById(R.id.no_comments);
            no_likes = itemView.findViewById(R.id.no_likes);
        }
    
        public void bind (post post) {
            
            full_name.setText(post.getPublisherInfo().getName());
            time_ago.setText(post.getTime());
            
            no_shares.setText(post.getPostShares());
            no_comments.setText(post.getCommentsStatus());
            no_likes.setText(post.getPostLikes());
    
            profile_pic.setShapeAppearanceModel(profile_pic
                    .getShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, 20)
                    .build());
            Picasso.get().load(post.getPublisherInfo().getAvatar()).into(profile_pic
                    , new Callback() {
                        @Override
                        public void onSuccess () {
                            Log.d(TAG, "onSuccess: " + "Image loaded");
                        }
                
                        @Override
                        public void onError (Exception e) {
                            Log.d(TAG, "onError: " + e.getMessage());
//                            profile_pic.setImageResource(R.drawable.ic_image_placeholder);
                            /*TODO reload icon*/
                        }
                    });
        }
    }
}
