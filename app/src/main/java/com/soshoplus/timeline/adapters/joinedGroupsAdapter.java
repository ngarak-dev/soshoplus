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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.groups.groupInfo;

import java.util.List;

public class joinedGroupsAdapter extends RecyclerView.Adapter<joinedGroupsAdapter.GroupsHolder> {
    
    private final onGroupClickListener groupClickListener;
    private final List<groupInfo> groupInfoList;
    private Context context;
    private static String TAG = "Joined Groups";
    
    public joinedGroupsAdapter (Context context, List<groupInfo> list, onGroupClickListener groupClickListener) {
        this.context = context;
        this.groupInfoList = list;
        this.groupClickListener = groupClickListener;
    }
    
    /*inflating and initializing a view*/
    @NonNull
    @Override
    public joinedGroupsAdapter.GroupsHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.joined_groups_row, parent, false);
        return new GroupsHolder(view);
    }
    
    @Override
    public void onBindViewHolder (@NonNull joinedGroupsAdapter.GroupsHolder holder, int position) {
        /*bind items and set onclick listener*/
        holder.bind(groupInfoList.get(position), groupClickListener, context);
    }
    
    @Override
    public int getItemCount () {
        return groupInfoList.size();
    }
    
    static class GroupsHolder extends RecyclerView.ViewHolder{
        
        ImageView profile_pic;
        TextView group_title;
        TextView total_members;
        
        public GroupsHolder (@NonNull View itemView) {
            super(itemView);
            profile_pic = itemView.findViewById(R.id.group_profile_pic);
            group_title = itemView.findViewById(R.id.group_title);
            total_members = itemView.findViewById(R.id.total_members);
        }
    
        public void bind (groupInfo groupInfo, onGroupClickListener groupClickListener, Context context) {
    
            Glide.with(context).load(groupInfo.getAvatar()).placeholder(R.drawable.ic_image_placeholder).thumbnail(0.5f).into(profile_pic);
            
            group_title.setText(groupInfo.getGroupTitle());
            total_members.setText(groupInfo.getMembers() + " Members");
            
            /*on Row click*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    groupClickListener.onGroupClick(groupInfo);
                }
            });
        }
    }
    
    /*interface for click listener*/
    public interface onGroupClickListener {
        /*onclick for a row*/
        void onGroupClick (groupInfo groupInfo);
    }
}
