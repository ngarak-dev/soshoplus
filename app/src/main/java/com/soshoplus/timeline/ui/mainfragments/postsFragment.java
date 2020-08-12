/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.mainfragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.soshoplus.timeline.R;
import com.soshoplus.timeline.calls.timelineCalls;
import com.soshoplus.timeline.databinding.FragmentPostsBinding;

public class postsFragment extends Fragment {

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

        /*refreshing posts*/
        postsBinding.postsRefreshLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(this::getTimelineFeed, 1000);
            postsBinding.postsRefreshLayout.setRefreshing(false);
        });
    }

    private void getTimelineFeed () {
    calls = new timelineCalls(requireContext());
    calls.getTimelineFeed(postsBinding.timelinePostsList,
            postsBinding.progressBarTimeline,
            postsBinding.timelineErrorLayout, postsBinding.tryAgain);
    }
}