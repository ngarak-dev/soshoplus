/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.feedHolders;

import android.text.util.Linkify;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hendraanggrian.appcompat.widget.SocialTextView;
import com.soshoplus.lite.R;
import com.soshoplus.lite.calls.likePostCall;
import com.soshoplus.lite.models.postsfeed.post;

import org.jetbrains.annotations.NotNull;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.transform.CircleCropTransformation;
import io.noties.markwon.Markwon;
import io.noties.markwon.linkify.LinkifyPlugin;

public class BlogPost extends BaseItemProvider<post> {

    private static String TAG = "BLOG POST : ";

    ImageView profile_pic, article_thumbnail;
    ImageView like_btn;
    TextView no_likes_holder;
    SocialTextView post_contents, article_title, article_description;

    private int adapterPosition;


    @Override
    public int getItemViewType() {
        return post.BLOG_POST;
    }

    @Override
    public int getLayoutId() {
        return R.layout.blog_post_list_row;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, post post) {
        Log.d(TAG, post.getPostId());

        ImageLoader imageLoader = Coil.imageLoader(getContext());

        profile_pic = baseViewHolder.findView(R.id.profile_pic);
        article_thumbnail = baseViewHolder.findView(R.id.article_thumbnail);
        like_btn = baseViewHolder.findView(R.id.like_btn);
        no_likes_holder = baseViewHolder.findView(R.id.no_likes_holder);
        post_contents = baseViewHolder.findView(R.id.post_contents);
        article_title = baseViewHolder.findView(R.id.article_title);
        article_description = baseViewHolder.findView(R.id.article_description);

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
                .usePlugin(LinkifyPlugin.create(
                        Linkify.EMAIL_ADDRESSES | Linkify.PHONE_NUMBERS | Linkify.WEB_URLS
                ))
                .build();

        if (post.getPostTextAPI().isEmpty()) {
            baseViewHolder.setGone(R.id.post_contents, true);
        } else {
            markwon.setMarkdown(post_contents, post.getPostTextAPI());
        }

        markwon.setMarkdown(article_title, post.getBlog().getTitle());
        markwon.setMarkdown(article_description, post.getBlog().getDescription());

        /*bind profile pic*/
        ImageRequest imageRequest = new ImageRequest.Builder(getContext())
                .data(post.getPublisherInfo().getAvatar())
                .placeholder(R.color.img_placeholder_color)
                .crossfade(true)
                .transformations(new CircleCropTransformation())
                .target(profile_pic)
                .build();
        imageLoader.enqueue(imageRequest);

        /*bind article thumbnail*/
        imageRequest = new ImageRequest.Builder(getContext())
                .data(post.getBlog().getThumbnail())
                .placeholder(R.color.img_placeholder_color)
                .crossfade(true)
                .target(article_thumbnail)
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
