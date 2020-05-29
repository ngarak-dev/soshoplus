/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class viewPagerAdapter extends FragmentStatePagerAdapter {
    
    private ArrayList<Fragment> arrayList = new ArrayList<>();
    
    
    public viewPagerAdapter (@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    
    @NonNull
    @Override
    public Fragment getItem (int position) {
        return arrayList.get(position);
    }
    
    @NonNull
    public Fragment createFragment (int position) {
        return arrayList.get(position);
    }
    
    public void addFragment(Fragment fragment) {
        arrayList.add(fragment);
    }
    
    @Override
    public int getCount () {
        return arrayList.size();
    }
}
