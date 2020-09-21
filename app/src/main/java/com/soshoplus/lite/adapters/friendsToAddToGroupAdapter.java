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
import coil.transform.CircleCropTransformation;

public class friendsToAddToGroupAdapter extends BaseQuickAdapter<followers, BaseViewHolder> {

    private static String TAG = "Friends";

    public friendsToAddToGroupAdapter(int layoutResId, @Nullable List<followers> data) {
        super(layoutResId, data);

        addChildClickViewIds(R.id.add_to_group);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, followers followers) {

        if (followers == null) {
            return;
        }

        baseViewHolder.setText(R.id.first_name, followers.getFirstName());

        ImageView profile_pic = baseViewHolder.findView(R.id.profile_pic);
        ImageLoader imageLoader = Coil.imageLoader(getContext());
        ImageRequest imageRequest = new ImageRequest.Builder(getContext())
                .data(followers.getAvatar())
                .crossfade(true)
                .transformations(new CircleCropTransformation())
                .target(profile_pic)
                .build();
        imageLoader.enqueue(imageRequest);
    }
}
