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
import com.soshoplus.timeline.calls.followersUsersCalls;
import com.soshoplus.timeline.calls.followingUsersCalls;
import com.soshoplus.timeline.calls.suggestedFriendsCalls;
import com.soshoplus.timeline.databinding.FragmentFriendsBinding;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class friendsFragment extends Fragment {
    
    public friendsFragment () {
        // Required empty public constructor
    }
    
    private FragmentFriendsBinding friendsBinding;
    private suggestedFriendsCalls suggestedFriendsCalls;
    private followingUsersCalls followingUsersCalls;
    private followersUsersCalls followersUsersCalls;
    
    /*initializing a view and inflate it */
    @Override
    public View onCreateView (@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }
    
    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        friendsBinding = FragmentFriendsBinding.bind(view);
        friendsBinding.getRoot();

        /*get Suggested friends*/
        new Handler().postDelayed(this::getSuggestedFriends,
                1000);

        /*get Friends*/
        /*get Suggested friends*/
        new Handler().postDelayed(this::getFriends,
                1000);
    }

    private void getSuggestedFriends () {
        suggestedFriendsCalls = new suggestedFriendsCalls(requireContext());
        suggestedFriendsCalls.getSuggestedFriends(friendsBinding.suggestedFriendsList,
                friendsBinding.suggestedTitle,
                friendsBinding.progressBarSuggested,
                friendsBinding.refreshSuggested);
    }

    private void getFriends () {
        /*following*/
        followingUsersCalls = new followingUsersCalls(requireContext());
        followingUsersCalls.getFollowing(friendsBinding.friendsFollowingList,
                friendsBinding.followingTitle,
                friendsBinding.progressBarFollowing, friendsBinding.refreshFollowing);

        /*followers*/
        followersUsersCalls = new followersUsersCalls(requireContext());
        followersUsersCalls.getFollowers(friendsBinding.friendsFollowersList,
                friendsBinding.followersTitle,
                friendsBinding.progressBarFollowers, friendsBinding.refreshFollowers);
    }
}
