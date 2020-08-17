/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.soshoplus.timeline.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import pl.aprilapps.easyphotopicker.MediaFile;

public class previewImagesAdapter extends BaseQuickAdapter<MediaFile, BaseViewHolder> {

    public previewImagesAdapter(int layoutResId, @Nullable List<MediaFile> data) {
        super(layoutResId, data);

        addChildClickViewIds(R.id.remove_img);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MediaFile mediaFile) {
        SimpleDraweeView imageView = baseViewHolder.findView(R.id.image);
        imageView.setImageURI(Uri.fromFile(mediaFile.getFile()));
    }
}
