/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class viewPagerAdapter extends FragmentStatePagerAdapter {
    
    /*array list for fragments*/
    private ArrayList<Fragment> arrayList = new ArrayList<>();
    
    /*constructor*/
    public viewPagerAdapter (@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    
    @NonNull
    @Override
    public Fragment getItem (int position) {
        return arrayList.get(position);
    }
    
    /*creating a new fragment*/
    @NonNull
    public Fragment createFragment (int position) {
        return arrayList.get(position);
    }
    
    /*adding a fragment*/
    public void addFragment(Fragment fragment) {
        arrayList.add(fragment);
    }
    
    @Override
    public int getCount () {
        return arrayList.size();
    }
}
