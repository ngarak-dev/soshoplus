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
    
    /*....*/
    RequestOptions options = new RequestOptions()
            .format(DecodeFormat.PREFER_RGB_565)
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_placeholder)
            .priority(Priority.LOW);
    
    public userPhotosAdapter (List<post> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }
    
    @NonNull
    @Override
    public ImageHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_layout,
                parent,
                false);
        return new ImageHolder(view);
    }
    
    @Override
    public void onBindViewHolder (@NonNull ImageHolder holder, int position) {
        Glide.with(context).load(postList.get(position).getPostFileFull())
                .apply(options).into(holder.imageView);
    }
    
    @Override
    public int getItemCount () {
        return postList.size();
    }
    
    static class ImageHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ImageHolder (View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
