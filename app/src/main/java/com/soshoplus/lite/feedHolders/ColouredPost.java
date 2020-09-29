/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.feedHolders;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.button.MaterialButton;
import com.hendraanggrian.appcompat.widget.SocialTextView;
import com.soshoplus.lite.R;
import com.soshoplus.lite.models.postsfeed.post;
import com.soshoplus.lite.ui.hashTagsPosts;
import com.soshoplus.lite.ui.user_profile.userProfile;

import org.commonmark.node.Emphasis;
import org.jetbrains.annotations.NotNull;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.transform.CircleCropTransformation;
import io.noties.markwon.AbstractMarkwonPlugin;
import io.noties.markwon.Markwon;
import io.noties.markwon.MarkwonConfiguration;
import io.noties.markwon.MarkwonSpansFactory;
import io.noties.markwon.RenderProps;
import io.noties.markwon.SpanFactory;
import io.noties.markwon.core.spans.EmphasisSpan;
import io.noties.markwon.image.coil.CoilImagesPlugin;
import io.noties.markwon.linkify.LinkifyPlugin;
import io.noties.markwon.simple.ext.SimpleExtPlugin;

public class ColouredPost extends BaseItemProvider<post> {

    private static String TAG = "COLOURED POST : ";

    ImageView profile_pic;
    MaterialButton like;
    SocialTextView colored_text;

    @Override
    public int getItemViewType() {
        return post.COLOURED_POST;
    }

    @Override
    public int getLayoutId() {
        return R.layout.coloured_post_list_row;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, post post) {
        Log.d(TAG, post.getPostId());

        colored_text = baseViewHolder.findView(R.id.coloured_post_text);

        profile_pic = baseViewHolder.findView(R.id.profile_pic);

        like = baseViewHolder.findView(R.id.like_btn);

        baseViewHolder.setText(R.id.full_name, post.getPublisherInfo().getName());
        baseViewHolder.setText(R.id.time_ago, post.getPostTime());
        baseViewHolder.setText(R.id.like_btn, post.getPostLikes());
        baseViewHolder.setText(R.id.comment_btn, post.getPostComments());

        Markwon markwon = Markwon.builder(getContext())
                .usePlugin(CoilImagesPlugin.create(getContext()))
                .usePlugin(CoilImagesPlugin.create(context, ImageLoader.create(getContext())))
                .usePlugin(LinkifyPlugin.create(
                        Linkify.EMAIL_ADDRESSES | Linkify.PHONE_NUMBERS | Linkify.WEB_URLS
                ))
                .build();

        /*setting colour*/
        switch (post.getColorId()) {
            case "1":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.post_gradient_one);
                break;
            case "2":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.post_gradient_two);
                break;
            case "3":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.post_gradient_three);
                break;
            case "4":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.post_gradient_four);
                break;
            case "5":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.post_gradient_five);
                break;
            case "6":
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.post_gradient_six);
                break;
            default:
                /*when the number exceeds*/
                baseViewHolder.setBackgroundResource(R.id.color_holder, R.drawable.post_default_gradient);
                break;
        }

        markwon.setMarkdown(colored_text, post.getPostTextAPI());

        colored_text.setOnHashtagClickListener((view, text) -> {
            Intent intent = new Intent(context, hashTagsPosts.class);
            intent.putExtra("hashTag", text.toString());
            context.startActivity(intent);
        });

        colored_text.setOnMentionClickListener((view, text) -> {
            Intent intent = new Intent(context, userProfile.class);
            intent.putExtra("username", text.toString());
            context.startActivity(intent);
        });

        /*bind profile pic*/
        ImageLoader imageLoader = Coil.imageLoader(getContext());
        ImageRequest imageRequest = new ImageRequest.Builder(getContext())
                .data(post.getPublisherInfo().getAvatar())
                .placeholder(R.color.light_grey)
                .crossfade(true)
                .transformations(new CircleCropTransformation())
                .target(profile_pic)
                .build();
        imageLoader.enqueue(imageRequest);

        /*if post is liked*/
        if (post.isLiked()) {
            like.setIconResource(R.drawable.ic_liked);
        } else {
            like.setIconResource(R.drawable.ic_like);
        }
    }
}
