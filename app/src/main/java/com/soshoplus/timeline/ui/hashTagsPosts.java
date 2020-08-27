/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.soshoplus.timeline.calls.hashTagsPostsCalls;
import com.soshoplus.timeline.calls.timelineCalls;
import com.soshoplus.timeline.databinding.ActivityHashTagsPostsBinding;

public class hashTagsPosts extends AppCompatActivity {

    private ActivityHashTagsPostsBinding binding;
    private hashTagsPostsCalls calls;
    private static String hashTag;
    private final static String type = "hashtag";

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

    private void getTimelineFeed () {
        calls = new hashTagsPostsCalls(hashTagsPosts.this);
        calls.getTimelineFeed(binding.timelinePostsList,
                binding.progressBarTimeline,
                type, binding.timelineErrorLayout, binding.tryAgain,
                binding.refreshPostsLayout, hashTag);
    }
}