/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.userprofile.infoList;

import java.util.List;

public class infoListAdapter extends RecyclerView.Adapter<infoListAdapter.InfoHolder> {
    
    private List<infoList> infoList;
    private Context context;
    
    public infoListAdapter (Context context, List<infoList> list) {
        this.context = context;
        this.infoList = list;
    }
    
    @NonNull
    @Override
    public infoListAdapter.InfoHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_list_row, parent, false);
        View view = LayoutInflater.from(context).inflate(R.layout.info_list_row, parent, false);
        return new InfoHolder(view);
    }
    
    @Override
    public void onBindViewHolder (@NonNull infoListAdapter.InfoHolder holder, int position) {
       infoList list =  infoList.get(position);
       holder.icon.setImageResource(list.getIcon());
       holder.title.setText(list.getTitle());
       holder.info.setText(list.getInfo());
    }
    
    @Override
    public int getItemCount () {
        return infoList.size();
    }
    
    public class InfoHolder extends RecyclerView.ViewHolder{
        
        ImageView icon;
        TextView title;
        TextView info;
        
        public InfoHolder (@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.info_icon);
            title = itemView.findViewById(R.id.info_title);
            info = itemView.findViewById(R.id.info_info);
        }
    }
}
