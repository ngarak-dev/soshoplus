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

import com.soshoplus.timeline.databinding.FragmentFriendsBinding;
import com.soshoplus.timeline.utils.retrofitCalls;

/**
 * A simple {@link Fragment} subclass.
 */
public class friendsFragment extends Fragment {
    
    public friendsFragment () {
        // Required empty public constructor
    }
    
    private FragmentFriendsBinding friendsBinding;
    private retrofitCalls calls;
    
    /*TODO NULL exception zipo AVOID AVOID*/
    
    /*initializing a view and inflate it */
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        friendsBinding = FragmentFriendsBinding.inflate(inflater, container, false);
    
        /*get followers*/
        getFollowers();
        
        /*get Following*/
        getFollowing();
        
        return friendsBinding.getRoot();
    }
    
    private void getFollowing () {
        calls = new retrofitCalls(requireContext());
        calls.getFollowing(friendsBinding.friendsFollowingList);
    }
    
    private void getFollowers () {
        calls = new retrofitCalls(requireContext());
        calls.getFollowers(friendsBinding.friendsFollowersList);
    }
}
