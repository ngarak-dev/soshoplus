/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.adapters;

import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.soshoplus.lite.R;
import com.soshoplus.lite.models.postsfeed.postComments;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.transform.CircleCropTransformation;

public class commentsAdapter extends BaseQuickAdapter<postComments, BaseViewHolder> {

    private static String TAG = "comments adapter";

    public commentsAdapter(int layoutResId, @Nullable List<postComments> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, postComments postComments) {

        if (postComments == null) {
            return;
        }

        baseViewHolder.setText(R.id.full_name, postComments.getPublisherInfo().getName());
        baseViewHolder.setText(R.id.comment_txt, postComments.getOrginaltext());

        if (postComments.getCommentLikes().equals("0")) {
            baseViewHolder.setGone(R.id.no_likes, true);
        } else {
            baseViewHolder.setText(R.id.no_likes, postComments.getCommentLikes() + " like");
        }

        if (postComments.getReplies().equals("0")) {
            baseViewHolder.setGone(R.id.no_reply, true);
        } else {
            baseViewHolder.setText(R.id.no_reply, postComments.getReplies() + " reply");
        }

        ImageLoader imageLoader = Coil.imageLoader(getContext());
        ImageView profile_pic = baseViewHolder.findView(R.id.profile_pic);

        ImageRequest imageRequest = new ImageRequest.Builder(getContext())
                .data(postComments.getPublisherInfo().getAvatar())
                .placeholder(R.color.light_grey)
                .crossfade(true)
                .transformations(new CircleCropTransformation())
                .target(profile_pic)
                .build();
        imageLoader.enqueue(imageRequest);
    }
}
