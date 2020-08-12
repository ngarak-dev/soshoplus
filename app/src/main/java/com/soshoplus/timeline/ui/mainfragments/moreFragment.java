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
import com.soshoplus.timeline.calls.simpleProfileCalls;
import com.soshoplus.timeline.databinding.FragmentMoreBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class moreFragment extends Fragment {

    private FragmentMoreBinding moreBinding;
    private simpleProfileCalls calls;

    public moreFragment () {
        // Required empty public constructor
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moreBinding = FragmentMoreBinding.bind(view);
        moreBinding.getRoot();

        new Handler().postDelayed(this::loadProfile, 1000);
    }

    private void loadProfile() {
        calls = new simpleProfileCalls(requireContext());
        calls.getProfile(moreBinding.profilePic, moreBinding.fullName, moreBinding.userEmail);
    }
}
