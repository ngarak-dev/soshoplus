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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.groups.groupInfo;


import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

public class suggestedGroupsAdapter extends RecyclerView.Adapter<suggestedGroupsAdapter.GroupsHolder> {
    
    private final onGroupClickListener groupClickListener;
    private final List<groupInfo> groupInfoList;
    private Context context;
    private static String TAG = "Suggested Groups";
    
    public suggestedGroupsAdapter (Context context, List<groupInfo> list, onGroupClickListener groupClickListener) {
        this.context = context;
        this.groupInfoList = list;
        this.groupClickListener = groupClickListener;
    }
    
    /*inflating and initializing a view*/
    @NonNull
    @Override
    public suggestedGroupsAdapter.GroupsHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.suggested_group_list_row, parent, false);
        return new GroupsHolder(view);
    }
    
    @Override
    public void onBindViewHolder (@NonNull suggestedGroupsAdapter.GroupsHolder holder, int position) {
        /*bind items and set onclick listener*/
        holder.bind(groupInfoList.get(position), groupClickListener, context,
                position);
    }
    
    @Override
    public int getItemCount () {
        return groupInfoList.size();
    }
    
    static class GroupsHolder extends RecyclerView.ViewHolder{
        
        ShapeableImageView profile_pic;
        TextView group_title;
        TextView group_category;
        TextView total_members;
        MaterialButton is_joined;
        ProgressBar progressBar;
        
        public GroupsHolder (@NonNull View itemView) {
            super(itemView);
            profile_pic = itemView.findViewById(R.id.group_profile_pic);
            group_title = itemView.findViewById(R.id.group_title);
            group_category = itemView.findViewById(R.id.group_category);
            total_members = itemView.findViewById(R.id.total_members);
            is_joined = itemView.findViewById(R.id.btn_join);
            progressBar = itemView.findViewById(R.id.progressBar_join);
        }
    
        public void bind (groupInfo groupInfo, onGroupClickListener groupClickListener, Context context, int position) {
    
            Observable.fromArray(groupInfo).subscribe(new Consumer<com.soshoplus.timeline.models.groups.groupInfo>() {
                @Override
                public void accept (groupInfo groupInfo) throws Throwable {
    
                    Glide.with(context).load(groupInfo.getAvatar()).placeholder(R.drawable.ic_image_placeholder)
                            .thumbnail(0.5f).into(profile_pic);
    
                    group_title.setText(groupInfo.getGroupTitle());
                    group_category.setText(groupInfo.getCategory());
                    total_members.setText(groupInfo.getMembers() + " Members");
                    
                }
            }).dispose();
            
            /*on Row click*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    groupClickListener.onGroupClick(groupInfo);
                }
            });
            
            /*on Button join click*/
            is_joined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    groupClickListener.onJoinClick(groupInfo, is_joined,
                            position, progressBar);
                }
            });
        }
    }
    
    /*interface for click listener*/
    public interface onGroupClickListener {
        /*onclick for a row*/
        void onGroupClick (groupInfo groupInfo);
        /*onclick for a button*/
        void onJoinClick (groupInfo groupInfo, MaterialButton is_joined, int position,
                          ProgressBar progressBar);
    }
}
