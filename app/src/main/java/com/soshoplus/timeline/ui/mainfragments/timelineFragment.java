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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.soshoplus.timeline.R;
import com.soshoplus.timeline.adapters.viewPagerAdapter;
import com.soshoplus.timeline.databinding.FragmentTimelineBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import me.ibrahimsn.lib.OnItemSelectedListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class timelineFragment extends Fragment {
    
    private FragmentTimelineBinding timelineBinding;
    private List<Fragment> fragmentList = new ArrayList<>();

    public timelineFragment () {
        // Required empty public constructor
    }
    
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

        fragmentList.add(new postsFragment());
        fragmentList.add(new searchFragment());
        fragmentList.add(new friendsFragment());

        timelineBinding.timelineViewPager.setAdapter(new viewPagerAdapter((FragmentActivity) requireContext(), fragmentList));

        timelineBinding.tabNavigation.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int position) {
                switch (position) {
                    case 0:
                        timelineBinding.timelineViewPager.setCurrentItem(0);
                        break;

                    case 1:
                        timelineBinding.timelineViewPager.setCurrentItem(1);
                        break;

                    case 2:
                        timelineBinding.timelineViewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
    }
}
