/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.mainfragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lxj.xpopup.XPopup;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.calls.timelineCalls;
import com.soshoplus.timeline.databinding.FragmentPostsBinding;
import com.soshoplus.timeline.ui.imageVideoPost;
import com.soshoplus.timeline.utils.xpopup.newNormalPostPopup;

public class postsFragment extends Fragment {

    private static String TAG = "posts Fragment";

    private FragmentPostsBinding postsBinding;
    private timelineCalls calls;

    public postsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        postsBinding = FragmentPostsBinding.bind(view);
        postsBinding.getRoot();

        /*timeline feed*/
        new Handler().postDelayed(this::getTimelineFeed, 1000);

        postsBinding.postTypes.addPost.setOnClickListener(view_ -> {
            postsBinding.postTypes.collapsingLayout.toggle();
        });

        /*normal post*/
        postsBinding.postTypes.newTxtPost.setOnClickListener(view_ -> {
            new XPopup.Builder(requireContext())
                    .asCustom(new newNormalPostPopup(requireContext(), postsBinding.postProgress)).show();
        });

        postsBinding.postTypes.colorPost.setOnClickListener(view_ -> {
            new XPopup.Builder(requireContext())
                    .asCustom(new newNormalPostPopup(requireContext(), postsBinding.postProgress)).show();
        });

        /*post with images*/
        postsBinding.postTypes.imagePost.setOnClickListener(view_ -> {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(requireContext(), imageVideoPost.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }, 500);
        });

        /*post with video*/
        postsBinding.postTypes.videoPost.setOnClickListener(view_ -> {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(requireContext(), imageVideoPost.class);
                intent.putExtra("type", 2);
                startActivity(intent);

            }, 500);
        });
    }

    private void getTimelineFeed () {
    calls = new timelineCalls(requireContext());
    calls.getTimelineFeed(postsBinding.timelinePostsList,
            postsBinding.progressBarTimeline,
            postsBinding.timelineErrorLayout, postsBinding.tryAgain);
    }
}