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
import com.soshoplus.timeline.models.friends.following;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.transform.RoundedCornersTransformation;

public class friendsFollowingAdapter extends BaseQuickAdapter<following, BaseViewHolder> {
    
    private static String TAG = "Friends";
    
    public friendsFollowingAdapter (int layoutResId, @Nullable List<following> data) {
        super(layoutResId, data);
    }
    
    @Override
    protected void convert (@NotNull BaseViewHolder baseViewHolder,
                            following followings) {
    
        if (followings == null) {
            return;
        }
    
        baseViewHolder.setText(R.id.full_name, followings.getName());

        ImageLoader imageLoader = Coil.imageLoader(getContext());
        ImageView profile_pic = baseViewHolder.findView(R.id.profile_pic);

        ImageRequest imageRequest = new ImageRequest.Builder(getContext())
                .data(followings.getAvatar())
                .placeholder(R.color.light_grey)
                .crossfade(true)
                .transformations(new RoundedCornersTransformation(10))
                .target(profile_pic)
                .build();
        imageLoader.enqueue(imageRequest);
    }
}
