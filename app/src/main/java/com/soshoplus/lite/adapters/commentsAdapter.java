/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.adapters;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.button.MaterialButton;
import com.soshoplus.lite.R;
import com.soshoplus.lite.models.postsfeed.postComments;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.transform.CircleCropTransformation;
import me.ngarak.timeagotextview.TimeAgoTextView;

public class commentsAdapter extends BaseQuickAdapter<postComments, BaseViewHolder> {

    private static String TAG = "comments adapter";

    public commentsAdapter(int layoutResId, @Nullable List<postComments> data) {
        super(layoutResId, data);

        addChildClickViewIds(R.id.no_reply, R.id.no_likes, R.id.reply_comment, R.id.comment_txt);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, postComments postComments) {

        if (postComments == null) {
            return;
        }

        baseViewHolder.setText(R.id.full_name, postComments.getPublisherInfo().getName());
        baseViewHolder.setText(R.id.comment_txt, postComments.getOrginaltext());

        TimeAgoTextView textView = baseViewHolder.findView(R.id.time_ago_txt);
        textView.setDate(Long.parseLong(postComments.getTime()));

        baseViewHolder.setText(R.id.no_likes, postComments.getCommentLikes());

        MaterialButton like = baseViewHolder.findView(R.id.no_likes);

        if (postComments.isCommentLiked()) {
            like.setIconResource(R.drawable.ic_liked);
        }

        if (postComments.getReplies() == null) {
            baseViewHolder.setGone(R.id.no_reply, true);
        } else if (postComments.getReplies().equals("0")) {
            baseViewHolder.setGone(R.id.no_reply, true);
        } else {
            baseViewHolder.setText(R.id.no_reply, postComments.getReplies());
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
