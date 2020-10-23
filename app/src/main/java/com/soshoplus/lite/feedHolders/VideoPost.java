/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.feedHolders;

import android.net.Uri;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
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

public class VideoPost extends BaseItemProvider<post> {

    private static String TAG = "VIDEO POST : ";
    private static String videoLink;
    ImageView profile_pic;
    ImageView like_btn;
    TextView no_likes_holder;
    SocialTextView post_contents;
    private SimpleExoPlayer exoPlayer;
    private PlayerView playerView;
    private boolean playWhenReady = false;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private int adapterPosition;

    @Override
    public int getItemViewType() {
        return post.VIDEO_POST;
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_post_list_row;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, post post) {
        Log.d(TAG, post.getPostId());

        profile_pic = baseViewHolder.findView(R.id.profile_pic);
        like_btn = baseViewHolder.findView(R.id.like_btn);
        no_likes_holder = baseViewHolder.findView(R.id.no_likes_holder);
        playerView = baseViewHolder.findView(R.id.media_player);
        post_contents = baseViewHolder.findView(R.id.post_contents);

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
                )).build();

        if (post.getPostTextAPI().isEmpty()) {
            baseViewHolder.setGone(R.id.post_contents, true);
        } else {
            markwon.setMarkdown(post_contents, post.getPostTextAPI());
        }

        /*bind profile pic*/
        ImageLoader imageLoader = Coil.imageLoader(getContext());
        ImageRequest imageRequest = new ImageRequest.Builder(getContext())
                .data(post.getPublisherInfo().getAvatar())
                .placeholder(R.color.img_placeholder_color)
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

        videoLink = post.getPostFile();
        initializePlayer(post.getPostFile());

        /*on click listeners*/
        //like post
        like_btn.setOnClickListener(v -> {
            new likePostCall(getAdapter(), adapterPosition, like_btn, no_likes_holder);
        });

    }

    private void initializePlayer(String postFile) {
        exoPlayer = new SimpleExoPlayer.Builder(getContext()).build();
        playerView.setPlayer(exoPlayer);

        Uri uri = Uri.parse(postFile);
        MediaSource mediaSource = buildMediaSource(uri);

        exoPlayer.setPlayWhenReady(playWhenReady);
        exoPlayer.seekTo(currentWindow, playbackPosition);
        exoPlayer.prepare(mediaSource, true, true);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getContext(), "soshoplay");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    @Override
    public void onViewAttachedToWindow(@NotNull BaseViewHolder holder) {
        if (exoPlayer == null) {
            initializePlayer(videoLink);
        }
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NotNull BaseViewHolder holder) {
        releasePlayer();
        super.onViewDetachedFromWindow(holder);
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            playWhenReady = exoPlayer.getPlayWhenReady();
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
