/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.mainfragments;

import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;

import com.soshoplus.timeline.R;
import com.soshoplus.timeline.calls.timelineCalls;
import com.soshoplus.timeline.databinding.FragmentTimelineBinding;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class timelineFragment extends Fragment {
    
    private FragmentTimelineBinding timelineBinding;
    private timelineCalls calls;
    
    public timelineFragment () {
        // Required empty public constructor
    }
//
//    private FragmentTimelineBinding timelineBinding;
//    private timelineCalls calls;
    
    /*initializing a view and inflate it */
    @Override
    public View onCreateView (@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }
    
    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        timelineBinding = FragmentTimelineBinding.bind(view);
        timelineBinding.getRoot();
    
        HandlerThread handlerThread = new HandlerThread("timelineF");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        
        /*timeline feed*/
        HandlerCompat.createAsync(looper).postDelayed(this::getTimelineFeed,
                800);
    }

    private void getTimelineFeed () {
        calls = new timelineCalls(requireContext());
        calls.getTimelineFeed(timelineBinding.timelinePostsList,
                timelineBinding.progressBarTimeline,
                timelineBinding.timelineErrorLayout, timelineBinding.tryAgain);
    }
}
