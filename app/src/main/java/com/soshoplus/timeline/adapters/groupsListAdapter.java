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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.groups.groupInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class groupsListAdapter extends RecyclerView.Adapter<groupsListAdapter.GroupsHolder> {
    
    private List<groupInfo> groupInfoList;
    private Context context;
    
    public groupsListAdapter (Context context, List<groupInfo> list) {
        this.context = context;
        this.groupInfoList = list;
    }
    
    /*inflating and initializing a view*/
    @NonNull
    @Override
    public groupsListAdapter.GroupsHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.group_list_row, parent, false);
        return new GroupsHolder(view);
    }
    
    @Override
    public void onBindViewHolder (@NonNull groupsListAdapter.GroupsHolder holder, int position) {
        groupInfo list =  groupInfoList.get(position);
    
        holder.profile_pic.setShapeAppearanceModel(holder.profile_pic
                .getShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, 20)
                .build());
        Picasso.get().load(list.getAvatar()).into(holder.profile_pic);
        
        holder.group_title.setText(list.getGroupTitle());
        holder.group_category.setText(list.getCategory());
        holder.total_members.setText(list.getMembers());
        holder.group_descr.setText(list.getAbout());
        /*TODO change button stated accordingly*/
    }
    
    @Override
    public int getItemCount () {
        return groupInfoList.size();
    }
    
    public class GroupsHolder extends RecyclerView.ViewHolder{
        
        ShapeableImageView profile_pic;
        TextView group_title;
        TextView group_category;
        TextView total_members;
        TextView group_descr;
        Button is_joined;
        
        public GroupsHolder (@NonNull View itemView) {
            super(itemView);
            profile_pic = itemView.findViewById(R.id.group_profile_pic);
            group_title = itemView.findViewById(R.id.group_title);
            group_category = itemView.findViewById(R.id.group_category);
            total_members = itemView.findViewById(R.id.total_members);
            is_joined = itemView.findViewById(R.id.btn_join);
            group_descr = itemView.findViewById(R.id.group_description);
        }
    }
}
