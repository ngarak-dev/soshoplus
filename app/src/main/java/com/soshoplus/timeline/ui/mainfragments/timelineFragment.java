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

import com.soshoplus.timeline.databinding.FragmentTimelineBinding;
import com.soshoplus.timeline.utils.retrofitCalls;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class timelineFragment extends Fragment {
    
    public timelineFragment () {
        // Required empty public constructor
    }
    
    private FragmentTimelineBinding timelineBinding;
    private retrofitCalls calls;
    private static String TAG = "timeline Fragment ";
    
    
    @Override
    public View onCreateView (@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        timelineBinding = FragmentTimelineBinding.inflate(inflater, container, false);
    
        //getCurrent Profile
        getTimelineFeed();
        
       return timelineBinding.getRoot();
    }
    
    /*get Timeline Feed*/
    private void getTimelineFeed () {
        calls = new retrofitCalls(requireContext());
        calls.getTimelineFeed();
    }
}
