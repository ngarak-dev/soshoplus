/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.postsfeed.post;

import java.util.List;

public class userPhotosAdapter extends RecyclerView.Adapter<userPhotosAdapter.ImageHolder> {
    /*.....*/
    private final List<post> postList;
    private Context context;
    /*onclick Listener*/
    private final onClickListener clickListener;
    
    /*....*/
    RequestOptions options = new RequestOptions()
            .format(DecodeFormat.PREFER_RGB_565)
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_placeholder)
            .priority(Priority.LOW);
    
    public userPhotosAdapter (List<post> postList, Context context, onClickListener clickListener) {
        this.postList = postList;
        this.context = context;
        this.clickListener = clickListener;
    }
    
    @NonNull
    @Override
    public ImageHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_layout,
                parent,
                false);
        return new ImageHolder(view, clickListener);
    }
    
    @Override
    public void onBindViewHolder (@NonNull ImageHolder holder, int position) {
        holder.bindImages(postList.get(position), position,
            clickListener, context);
    }
    
    @Override
    public int getItemCount () {
        return postList.size();
    }
    
    class ImageHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ImageHolder (View itemView, onClickListener clickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    
        public void bindImages (post post, int position, onClickListener clickListener, Context context) {
            /*load image*/
            if (post.getPostType().equals("ad")) {
                Glide.with(context).load(post.getAdMedia())
                        .apply(options).into(imageView);
            }
            else {
                Glide.with(context).load(post.getPostFileFull())
                        .apply(options).into(imageView);
            }
            
            /*click listener*/
            if (post.getPostType().equals("ad")) {
                /*show ad popup*/
                imageView.setOnClickListener(view -> {
                    clickListener.viewFullADImage(context, post, imageView);
                });
            }
            else {
                imageView.setOnClickListener(view -> {
                    clickListener.viewFullImage(context, post, imageView);
                });
            }
        }
    }
    
    /*onClick Interface*/
    public interface onClickListener {
        /*on image click*/
        void viewFullImage (Context context, post post, ImageView imageView);
        /*on ad image click*/
        void viewFullADImage (Context context, post post, ImageView imageView);
    }
}
