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
import com.ypx.imagepicker.bean.ImageItem;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;

public class previewImagesAdapter extends BaseQuickAdapter<ImageItem, BaseViewHolder> {

    public previewImagesAdapter(int layoutResId, @Nullable List<ImageItem> data) {
        super(layoutResId, data);

        addChildClickViewIds(R.id.remove_img);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ImageItem imageItem) {
        ImageView imageView = baseViewHolder.findView(R.id.image);
        ImageLoader imageLoader = Coil.imageLoader(getContext());

        if (imageItem.isImage()) {
            ImageRequest imageRequest = new ImageRequest.Builder(getContext())
                    .data(imageItem.getUri())
                    .crossfade(true)
                    .target(imageView)
                    .build();
            imageLoader.enqueue(imageRequest);
        }
        else {
            ImageRequest imageRequest = new ImageRequest.Builder(getContext())
                    .data(imageItem.getVideoImageUri())
                    .crossfade(true)
                    .target(imageView)
                    .build();
            imageLoader.enqueue(imageRequest);
        }
    }
}
