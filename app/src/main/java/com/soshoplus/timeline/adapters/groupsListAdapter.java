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
    
    private final onGroupClickListener groupClickListener;
    private final List<groupInfo> groupInfoList;
    private Context context;
    
    public groupsListAdapter (Context context, List<groupInfo> list, onGroupClickListener groupClickListener) {
        this.context = context;
        this.groupInfoList = list;
        this.groupClickListener = groupClickListener;
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
        /*bind items and set onclick listener*/
        holder.bind(groupInfoList.get(position), groupClickListener);
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
    
        public void bind (groupInfo groupInfo, onGroupClickListener groupClickListener) {

            profile_pic.setShapeAppearanceModel(profile_pic
                    .getShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, 20)
                    .build());
            Picasso.get().load(groupInfo.getAvatar()).into(profile_pic);
    
            group_title.setText(groupInfo.getGroupTitle());
            group_category.setText(groupInfo.getCategory());
            total_members.setText(groupInfo.getMembers());
            group_descr.setText(groupInfo.getAbout());
            
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
                    groupClickListener.onJoinClick(groupInfo);
                    /*TODO change button stated accordingly*/
                }
            });
        }
    }
    
    /*interface for click listener*/
    public interface onGroupClickListener {
        /*onclick for a row*/
        void onGroupClick (groupInfo groupInfo);
        /*onclick for a button*/
        void onJoinClick (groupInfo groupInfo);
    }
}
