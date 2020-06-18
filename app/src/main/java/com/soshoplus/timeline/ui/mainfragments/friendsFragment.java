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

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public View onCreateView (@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        friendsBinding = FragmentFriendsBinding.inflate(inflater, container, false);
        
        /*get Suggested friends*/
        getSuggestedFriends();
        
        /*get Friends*/
        getFriends();
        
        return friendsBinding.getRoot();
    }
    
    private void getSuggestedFriends () {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run () {
                calls = new retrofitCalls(requireContext());
                calls.getSuggestedFriends(friendsBinding.suggestedFriendsList
                        , executorService);
            }
        });
    }
    
    private void getFriends () {
    
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run () {
                /*following*/
                calls = new retrofitCalls(requireContext());
                calls.getFollowing(friendsBinding.friendsFollowingList);
                /*followers*/
                calls = new retrofitCalls(requireContext());
                calls.getFollowers(friendsBinding.friendsFollowersList);
            }
        });
        
        executorService.shutdown();
    }
}
