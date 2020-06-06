/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.friends.suggested.suggestedInfo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class suggestedFriendsAdapter extends RecyclerView.Adapter<suggestedFriendsAdapter.SuggestedFriendsHolder> {
    
    private final onSuggestedClickListener suggestedClickListener;
    private final List<suggestedInfo> suggestedInfoList;
    private Context context;
    private static String TAG = "Suggested Groups";
    
    public suggestedFriendsAdapter (Context context, List<suggestedInfo> list,
                                    onSuggestedClickListener suggestedClickListener) {
        this.context = context;
        this.suggestedInfoList = list;
        this.suggestedClickListener = suggestedClickListener;
    }
    
    /*inflating and initializing a view*/
    @NonNull
    @Override
    public suggestedFriendsAdapter.SuggestedFriendsHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.suggested_group_list_row, parent, false);
        return new SuggestedFriendsHolder(view);
    }
    
    @Override
    public void onBindViewHolder (@NonNull suggestedFriendsAdapter.SuggestedFriendsHolder holder, int position) {
        /*bind items and set onclick listener*/
        holder.bind(suggestedInfoList.get(position), suggestedClickListener);
    }
    
    @Override
    public int getItemCount () {
        return suggestedInfoList.size();
    }
    
    public class SuggestedFriendsHolder extends RecyclerView.ViewHolder{
        
        ShapeableImageView profile_pic;
        TextView group_title;
        TextView group_category;
        TextView total_members;
        Button is_joined;
        
        public SuggestedFriendsHolder (@NonNull View itemView) {
            super(itemView);
            profile_pic = itemView.findViewById(R.id.group_profile_pic);
            group_title = itemView.findViewById(R.id.group_title);
            group_category = itemView.findViewById(R.id.group_category);
            total_members = itemView.findViewById(R.id.total_members);
            is_joined = itemView.findViewById(R.id.btn_join);
        }
    
        public void bind (suggestedInfo suggestedInfo, onSuggestedClickListener suggestedClickListener) {

            profile_pic.setShapeAppearanceModel(profile_pic
                    .getShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, 20)
                    .build());
            Picasso.get().load("/*TODO*/").placeholder(R.drawable.ic_image_placeholder).into(profile_pic
                    , new Callback() {
                @Override
                public void onSuccess () {
                    Log.d(TAG, "onSuccess: " + "Image loaded");
                }
    
                @Override
                public void onError (Exception e) {
                    Log.d(TAG, "onError: " + e.getMessage());
                    profile_pic.setImageResource(R.drawable.ic_image_placeholder);
                    /*TODO reload icon*/
                }
            });
            
            /*on click*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    suggestedClickListener.onClick(suggestedInfo);
                }
            });
        }
    }
    
    /*interface for click listener*/
    public interface onSuggestedClickListener {
        /*onclick*/
        void onClick (suggestedInfo suggestedInfo);
    }
}
