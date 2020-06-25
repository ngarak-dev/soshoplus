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

import com.soshoplus.timeline.databinding.FragmentGroupsBinding;
import com.soshoplus.timeline.utils.retrofitCalls;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 */
public class groupsFragment extends Fragment {
    
    public groupsFragment () {
        // Required empty public constructor
    }
    
    private FragmentGroupsBinding groupsBinding;
    private retrofitCalls calls;
    
    /*initializing a view and inflate it */
    @Override
    public View onCreateView (@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        groupsBinding = FragmentGroupsBinding.inflate(inflater, container, false);
        
        /*get groups*/
        getGroups();
        
        return groupsBinding.getRoot();
    }
    
    private void getGroups () {
        
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run () {
                /*recommended*/
                calls = new retrofitCalls(requireContext());
                calls.getRecommends(groupsBinding.suggestedGroupsList);
                /*joined*/
                calls = new retrofitCalls(requireContext());
                calls.getJoined(groupsBinding.joinedGroupsList);
            }
        });
    
        executorService.shutdown();
    }
}
