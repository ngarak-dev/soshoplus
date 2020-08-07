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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.soshoplus.timeline.R;
import com.soshoplus.timeline.calls.simpleProfileCalls;
import com.soshoplus.timeline.databinding.FragmentProfileBinding;

import org.jetbrains.annotations.NotNull;

import de.adorsys.android.securestoragelibrary.SecurePreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class profileFragment extends Fragment {
    
    public profileFragment () {
        // Required empty public constructor
    }

    private static String TAG = "profileFragment ";
    private FragmentProfileBinding profileBinding;
    private static String userId, timezone, accessToken;
    /*..........*/
    private simpleProfileCalls calls;

    @Override
    public View onCreateView (@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    
    @Override
    public void onViewCreated (@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileBinding = FragmentProfileBinding.bind(view);
        profileBinding.getRoot();

        userId = SecurePreferences.getStringValue(requireContext(), "userId", "0");
        timezone = SecurePreferences.getStringValue(requireContext(), "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(requireContext(), "accessToken"
                , "0");

        new Handler().postDelayed(this::loadProfile, 500);
    }

    private void loadProfile() {
        calls = new simpleProfileCalls(requireContext());
        calls.getProfile(profileBinding.profilePic, profileBinding.fullName, profileBinding.userEmail);
    }
}
