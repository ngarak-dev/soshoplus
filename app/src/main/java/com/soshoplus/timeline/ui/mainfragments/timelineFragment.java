/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.mainfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.soshoplus.timeline.calls.timelineCalls;
import com.soshoplus.timeline.databinding.FragmentTimelineBinding;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class timelineFragment extends Fragment {
    
    public timelineFragment () {
        // Required empty public constructor
    }
    
    private FragmentTimelineBinding timelineBinding;
    private timelineCalls calls;
    
    /*initializing a view and inflate it */
    @Override
    public View onCreateView (@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        timelineBinding = FragmentTimelineBinding.inflate(inflater, container, false);
        
        /*timeline feed*/
        getTimelineFeed();
        
        return timelineBinding.getRoot();
    }
    
    private void getTimelineFeed () {
        calls = new timelineCalls(requireContext());
        calls.getTimelineFeed(timelineBinding.timelinePostsList,
                timelineBinding.timelineSmartRefresh,
                timelineBinding.progressBarTimeline,
                timelineBinding.timelineErrorLayout, timelineBinding.tryAgain);
    }
}
