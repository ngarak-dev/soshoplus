/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.friends.suggested.suggestedInfo;

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
        View view =  LayoutInflater.from(context).inflate(R.layout.suggested_friends_list_row, parent, false);
        return new SuggestedFriendsHolder(view);
    }
    
    @Override
    public void onBindViewHolder (@NonNull suggestedFriendsAdapter.SuggestedFriendsHolder holder, int position) {
        /*bind items and set onclick listener*/
        holder.bind(suggestedInfoList.get(position), suggestedClickListener, context);
    }
    
    @Override
    public int getItemCount () {
        return suggestedInfoList.size();
    }
    
    static class SuggestedFriendsHolder extends RecyclerView.ViewHolder{
        
        ShapeableImageView profile_pic;
        TextView full_name;
        TextView about;
        Chip follow;
        
        public SuggestedFriendsHolder (@NonNull View itemView) {
            super(itemView);
            profile_pic = itemView.findViewById(R.id.profile_pic);
            full_name = itemView.findViewById(R.id.full_name);
            follow = itemView.findViewById(R.id.btn_follow);
            about = itemView.findViewById(R.id.about_me);
        }
    
        public void bind (suggestedInfo suggestedInfo, onSuggestedClickListener suggestedClickListener, Context context) {
            
            full_name.setText(suggestedInfo.getName());
            about.setText(Html.fromHtml(String.valueOf(suggestedInfo.getAbout())));

            profile_pic.setShapeAppearanceModel(profile_pic
                    .getShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, 20)
                    .build());
            Glide.with(context).load(suggestedInfo.getAvatar()).placeholder(R.drawable.ic_image_placeholder).thumbnail(0.5f).into(profile_pic);

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
