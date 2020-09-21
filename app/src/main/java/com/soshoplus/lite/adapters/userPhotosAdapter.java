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
import com.soshoplus.lite.models.postsfeed.post;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.size.Scale;
import coil.transform.RoundedCornersTransformation;

public class userPhotosAdapter extends BaseQuickAdapter<post, BaseViewHolder> {

    public userPhotosAdapter(int layoutResId, @Nullable List<post> data) {
        super(layoutResId, data);
        addChildClickViewIds(R.id.image);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, post post) {

        ImageLoader imageLoader = Coil.imageLoader(getContext());
        ImageView imageView = baseViewHolder.findView(R.id.image);

        if (post.getPostType().equals("ad")) {
            ImageRequest imageRequest = new ImageRequest.Builder(getContext())
                    .data(post.getAdMedia())
                    .crossfade(true)
                    .placeholder(R.color.grey)
                    .scale(Scale.FIT)
                    .transformations(new RoundedCornersTransformation(10))
                    .target(imageView)
                    .build();
            imageLoader.enqueue(imageRequest);
        } else {
            ImageRequest imageRequest = new ImageRequest.Builder(getContext())
                    .data(post.getPostFileFull())
                    .crossfade(true)
                    .placeholder(R.color.grey)
                    .scale(Scale.FIT)
                    .transformations(new RoundedCornersTransformation(10))
                    .target(imageView)
                    .build();
            imageLoader.enqueue(imageRequest);
        }
    }
}
