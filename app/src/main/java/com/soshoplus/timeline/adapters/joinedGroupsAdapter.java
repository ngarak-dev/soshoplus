/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.adapters;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.groups.groupInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;

public class joinedGroupsAdapter extends BaseQuickAdapter<groupInfo, BaseViewHolder> {

    private static String TAG = "Joined Groups";

    public joinedGroupsAdapter(int layoutResId, @Nullable List<groupInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, groupInfo groupInfo) {

        ImageView group_avatar = baseViewHolder.findView(R.id.group_profile_pic);

        baseViewHolder.setText(R.id.group_title, groupInfo.getGroupTitle());
        baseViewHolder.setText(R.id.total_members, groupInfo.getMembers() + " members");

        /*....*/
        ImageLoader imageLoader = Coil.imageLoader(getContext());
        ImageRequest imageRequest = new ImageRequest.Builder(getContext())
                .data(groupInfo.getAvatar())
                .placeholder(R.color.light_grey)
                .crossfade(true)
                .target(group_avatar)
                .build();
        imageLoader.enqueue(imageRequest);
    }
}
