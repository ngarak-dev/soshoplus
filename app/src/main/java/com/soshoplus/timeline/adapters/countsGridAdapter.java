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

import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.userprofile.countsGrid;

import java.util.List;

public class countsGridAdapter extends RecyclerView.Adapter<countsGridAdapter.InfoHolder> {
    
    private List<countsGrid> countsGridList;
    private Context context;
    
    public countsGridAdapter (Context context, List<countsGrid> gridList ) {
        this.context = context;
        this.countsGridList = gridList;
    }
    
    @NonNull
    @Override
    public countsGridAdapter.InfoHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.counts_grids, parent, false);
        return new InfoHolder(view);
    }
    
    @Override
    public void onBindViewHolder (@NonNull countsGridAdapter.InfoHolder holder, int position) {
       countsGrid list =  countsGridList.get(position);
       holder.icon.setImageResource(list.getIcon());
       holder.count.setText(list.getCount());
       holder.count_info.setText(list.getCount_info());
    }
    
    @Override
    public int getItemCount () {
        return countsGridList.size();
    }
    
    public class InfoHolder extends RecyclerView.ViewHolder{
        
        ImageView icon;
        TextView count;
        TextView count_info;
        
        public InfoHolder (@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.grid_icon);
            count = itemView.findViewById(R.id.grid_count);
            count_info = itemView.findViewById(R.id.count_info);
        }
    }
}
