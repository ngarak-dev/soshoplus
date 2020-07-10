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

import com.soshoplus.timeline.calls.joinedGroupsCalls;
import com.soshoplus.timeline.calls.recommendedGroupsCalls;
import com.soshoplus.timeline.databinding.FragmentGroupsBinding;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class groupsFragment extends Fragment {
    
    public groupsFragment () {
        // Required empty public constructor
    }
    
    private FragmentGroupsBinding groupsBinding;
    private recommendedGroupsCalls recommendedGroupsCalls;
    private joinedGroupsCalls joinedGroupsCalls;
    
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
    
        /*recommended*/
        recommendedGroupsCalls = new recommendedGroupsCalls(requireContext());
        recommendedGroupsCalls.getRecommends(groupsBinding.suggestedGroupsList,
                groupsBinding.allSetUpImg,
                groupsBinding.allSetUpText, groupsBinding.progressBarSuggested);
    
        /*joined*/
        joinedGroupsCalls = new joinedGroupsCalls(requireContext());
        joinedGroupsCalls.getJoined(groupsBinding.joinedGroupsList,
                groupsBinding.progressBarJoined,
                groupsBinding.joinedGroupsShowHereImg, groupsBinding.joinedGroupsShowHereTxt);
    }
}
