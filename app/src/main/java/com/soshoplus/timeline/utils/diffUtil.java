/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.soshoplus.timeline.models.postsfeed.post;

import java.util.List;

public class diffUtil extends DiffUtil.Callback {
    
    
    private final List<post> oldPostList;
    private final List<post> newPostList;
    
    public diffUtil (List<post> oldPostList, List<post> newPostList) {
        this.oldPostList = oldPostList;
        this.newPostList = newPostList;
    }
    
    @Override
    public int getOldListSize () {
        return oldPostList.size();
    }
    
    @Override
    public int getNewListSize () {
        return newPostList.size();
    }
    
    @Override
    public boolean areItemsTheSame (int oldItemPosition, int newItemPosition) {
        if (oldPostList.get(oldItemPosition).getPostType().equals("ad")) {
            return oldPostList.get(oldItemPosition).getHeadline().equals(newPostList.get(newItemPosition).getHeadline());
        } else {
            return oldPostList.get(oldItemPosition).getPostId().equals(newPostList.get(newItemPosition).getPostId());
        }
    }
    
    @Override
    public boolean areContentsTheSame (int oldItemPosition, int newItemPosition) {
        if (oldPostList.get(oldItemPosition).getPostType().equals("ad")) {
            return oldPostList.get(oldItemPosition).getDescription().equals(newPostList.get(newItemPosition).getDescription());
        } else {
            return oldPostList.get(oldItemPosition).getPostTime().equals(newPostList.get(newItemPosition).getPostTime());
        }
    }
    
    @Nullable
    @Override
    public Object getChangePayload (int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
