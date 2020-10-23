/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.feedHolders;

import android.content.Intent;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hendraanggrian.appcompat.widget.SocialTextView;
import com.soshoplus.lite.R;
import com.soshoplus.lite.calls.likePostCall;
import com.soshoplus.lite.models.postsfeed.post;
import com.soshoplus.lite.ui.hashTagsPosts;
import com.soshoplus.lite.ui.user_profile.userProfile;

import org.jetbrains.annotations.NotNull;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.transform.CircleCropTransformation;
import io.noties.markwon.Markwon;
import io.noties.markwon.image.coil.CoilImagesPlugin;
import io.noties.markwon.linkify.LinkifyPlugin;

public class ColouredPost extends BaseItemProvider<post> {

    private static String TAG = "COLOURED POST : ";

    ImageView profile_pic;
    ImageView like_btn;
    TextView no_likes_holder;
    SocialTextView colored_text;

    private int adapterPosition;

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
        like_btn = baseViewHolder.findView(R.id.like_btn);
        no_likes_holder = baseViewHolder.findView(R.id.no_likes_holder);

        baseViewHolder.setText(R.id.full_name, post.getPublisherInfo().getName());
        baseViewHolder.setText(R.id.time_ago, post.getPostTime());

        /*getting adapter position*/
        adapterPosition = baseViewHolder.getAdapterPosition();

        /*if likes > 0*/
        if (!post.getPostLikes().equals("0")) {
            baseViewHolder.setText(R.id.no_likes_holder, post.getPostLikes() + " Likes");
        }

        /*if comments > 0*/
        if (!post.getPostComments().equals("0")) {
            baseViewHolder.setText(R.id.no_comments_holder, post.getPostComments() + " Comments");
        }

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
            like_btn.setImageResource(R.drawable.ic_liked);
        } else {
            like_btn.setImageResource(R.drawable.ic_like);
        }

        /*on click listeners*/
        //like post
        like_btn.setOnClickListener(v -> {
            new likePostCall(getAdapter(), adapterPosition, like_btn, no_likes_holder);
        });
    }
}
