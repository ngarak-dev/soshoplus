/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.soshoplus.lite.calls.hashTagsPostsCalls;
import com.soshoplus.lite.databinding.ActivityHashTagsPostsBinding;

public class hashTagsPosts extends AppCompatActivity {

    private final static String type = "hashtag";
    private static String hashTag;
    private ActivityHashTagsPostsBinding binding;
    private hashTagsPostsCalls calls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHashTagsPostsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        hashTag = intent.getStringExtra("hashTag");

        binding.backArrow.setOnClickListener(view_ -> {
            onBackPressed();
        });

        binding.hashtagTitle.setText("#" + hashTag);

        /*timeline posts with selected hashtag*/
        new Handler().postDelayed(this::getTimelineFeed, 1000);
    }

    private void getTimelineFeed() {
        calls = new hashTagsPostsCalls(hashTagsPosts.this);
        calls.getTimelineFeed(binding.timelinePostsList,
                binding.progressBarTimeline,
                type, binding.timelineErrorLayout, binding.tryAgain,
                binding.refreshPostsLayout, hashTag);
    }
}