/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.adapters;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.friends.followers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class friendsFollowersAdapter extends BaseQuickAdapter<followers, BaseViewHolder> {
    
    private static String TAG = "Friends";
    
    public friendsFollowersAdapter (int layoutResId, @Nullable List<followers> data) {
        super(layoutResId, data);
    }
    
    @Override
    protected void convert (@NotNull BaseViewHolder baseViewHolder, followers followers) {
    
        if (followers == null) {
            return;
        }
    
        baseViewHolder.setText(R.id.full_name, followers.getName());
    
        SimpleDraweeView profile_pic = baseViewHolder.findView(R.id.profile_pic);
        profile_pic.setImageURI(followers.getAvatar());
    }
}
