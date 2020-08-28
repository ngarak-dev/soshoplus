/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class viewPagerAdapter extends FragmentStateAdapter {
    
    /*list of fragments*/
    List<Fragment> fragments;
    
    public viewPagerAdapter (@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments) {
        super(fragmentActivity);
        this.fragments  = fragments;
    }
    
    /*creating a new fragment*/
    @NonNull
    @Override
    public Fragment createFragment (int position) {
        return fragments.get(position);
    }
    
    @Override
    public int getItemCount () {
        return fragments == null ? 0 : fragments.size();
    }

    /*adding a fragment*/
    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }
}
