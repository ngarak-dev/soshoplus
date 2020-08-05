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
import com.soshoplus.timeline.models.groups.groupInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class joinedGroupsAdapter extends BaseQuickAdapter<groupInfo, BaseViewHolder> {

    private static String TAG = "Joined Groups";

    public joinedGroupsAdapter(int layoutResId, @Nullable List<groupInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, groupInfo groupInfo) {

        SimpleDraweeView group_avatar = baseViewHolder.findView(R.id.group_profile_pic);

        baseViewHolder.setText(R.id.group_title, groupInfo.getGroupTitle());
        baseViewHolder.setText(R.id.total_members, groupInfo.getMembers() + " members");

        /*....*/
        group_avatar.setImageURI(groupInfo.getAvatar());
    }
}
