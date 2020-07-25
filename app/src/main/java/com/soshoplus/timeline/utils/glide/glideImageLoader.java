/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils.glide;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class glideImageLoader {
    
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    
    public glideImageLoader (ImageView imageView, ProgressBar progressBar) {
        mImageView = imageView;
        mProgressBar = progressBar;
    }
    
    public void load(final String url, RequestOptions options) {
        if (url == null || options == null) return;
        
        onConnecting();
        
        //set Listener & start
        progressGlideModule.expect(url, new progressGlideModule.UIonProgressListener() {
            @Override
            public void onProgress(long bytesRead, long expectedLength) {
                if (mProgressBar != null) {
                    mProgressBar.setProgress((int) (100 * bytesRead / expectedLength));
                }
            }
            
            @Override
            public float getGranualityPercentage() {
                return 1.0f;
            }
        });
        //Get Image
        Glide.with(mImageView.getContext())
                .load(url)
                .thumbnail(0.5f)
                .transition(withCrossFade())
                .apply(options.skipMemoryCache(true))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressGlideModule.forget(url);
                        onFinished();
                        return false;
                    }
                    
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressGlideModule.forget(url);
                        onFinished();
                        return false;
                    }
                })
                .into(mImageView);
    }
    
    
    private void onConnecting() {
        if (mProgressBar != null) mProgressBar.setVisibility(View.VISIBLE);
    }
    
    private void onFinished() {
        if (mProgressBar != null && mImageView != null) {
            mProgressBar.setVisibility(View.GONE);
            mImageView.setVisibility(View.VISIBLE);
        }
    }
}
