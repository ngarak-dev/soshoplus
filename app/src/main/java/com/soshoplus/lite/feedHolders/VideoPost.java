/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.feedHolders;

import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.material.button.MaterialButton;
import com.soshoplus.lite.R;
import com.soshoplus.lite.models.postsfeed.post;

import org.jetbrains.annotations.NotNull;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.transform.CircleCropTransformation;

public class VideoPost extends BaseItemProvider<post> {
    
    private static String TAG = "VIDEO POST : ";
    
    ImageView profile_pic;
    MaterialButton like;

    private SimpleExoPlayer exoPlayer;
    private PlayerView playerView;
    private boolean playWhenReady = false;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private static String videoLink;
    
    @Override
    public int getItemViewType () {
        return post.VIDEO_POST;
    }
    
    @Override
    public int getLayoutId () {
        return R.layout.video_post_list_row;
    }
    
    @Override
    public void convert (@NotNull BaseViewHolder baseViewHolder, post post) {
        Log.d(TAG, post.getPostId());

        profile_pic = baseViewHolder.findView(R.id.profile_pic);
        like = baseViewHolder.findView(R.id.like_btn);

        playerView = baseViewHolder.findView(R.id.media_player);

        baseViewHolder.setText(R.id.full_name, post.getPublisherInfo().getName());
        baseViewHolder.setText(R.id.time_ago, post.getPostTime());
        baseViewHolder.setText(R.id.no_likes, post.getPostLikes() + " likes");
        baseViewHolder.setText(R.id.no_comments, post.getPostComments() + " comments");

        if (post.getPostTextAPI().isEmpty()) {
            baseViewHolder.setGone(R.id.post_contents, true);
        } else {
            baseViewHolder.setText(R.id.post_contents, post.getOrginaltext());
        }

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

        videoLink = post.getPostFile();
        initializePlayer(post.getPostFile());
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
