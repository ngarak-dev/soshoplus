/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.adapters;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.soshoplus.lite.R;
import com.soshoplus.lite.models.friends.followers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.transform.RoundedCornersTransformation;

public class friendsFollowersAdapter extends BaseQuickAdapter<followers, BaseViewHolder> {

    private static String TAG = "Friends";

    public friendsFollowersAdapter(int layoutResId, @Nullable List<followers> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, followers followers) {

        if (followers == null) {
            return;
        }

        baseViewHolder.setText(R.id.full_name, followers.getName());

        ImageLoader imageLoader = Coil.imageLoader(getContext());
        ImageView profile_pic = baseViewHolder.findView(R.id.profile_pic);

        ImageRequest imageRequest = new ImageRequest.Builder(getContext())
                .data(followers.getAvatar())
                .placeholder(R.color.light_grey)
                .crossfade(true)
                .transformations(new RoundedCornersTransformation(10))
                .target(profile_pic)
                .build();
        imageLoader.enqueue(imageRequest);
    }
}
