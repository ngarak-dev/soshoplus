/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.soshoplus.timeline.R;
import com.soshoplus.timeline.adapters.viewPagerAdapter;
import com.soshoplus.timeline.databinding.ActivitySoshotimelineBinding;
import com.soshoplus.timeline.ui.mainfragments.friendsFragment;
import com.soshoplus.timeline.ui.mainfragments.groupsFragment;
import com.soshoplus.timeline.ui.mainfragments.moreFragment;
import com.soshoplus.timeline.ui.mainfragments.profileFragment;
import com.soshoplus.timeline.ui.mainfragments.timelineFragment;

import java.util.ArrayList;
import java.util.List;

public class soshoTimeline extends AppCompatActivity {
    
    private ActivitySoshotimelineBinding soshoTimelineBinding;
    private static String TAG = "soshoTimeline Activity";
    private List<Fragment> fragmentList = new ArrayList<>();
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        soshoTimelineBinding = ActivitySoshotimelineBinding.inflate(getLayoutInflater());
        View view = soshoTimelineBinding.getRoot();
        setContentView(view);
        
        /*appBar*/
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.colorPrimaryDark));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_soshoplus_appbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        
        /*adding fragments*/
        fragmentList.add(new timelineFragment());
        fragmentList.add(new friendsFragment());
        fragmentList.add(new groupsFragment());
        fragmentList.add(new profileFragment());
        fragmentList.add(new moreFragment());
        
        /*setting view pager adapter*/
        soshoTimelineBinding.soshoViewPager.setAdapter(new viewPagerAdapter(soshoTimeline.this, fragmentList));
        soshoTimelineBinding.soshoViewPager.setCurrentItem(0);
        /*on page scroll listener*/
        soshoTimelineBinding.soshoViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled (int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                Log.d(TAG, "onPageScrolled: ACTIVE POSITION : " + position);
                soshoTimelineBinding.bottomNavigation.setItemActiveIndex(position);
            }
    
            @Override
            public void onPageScrollStateChanged (int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        /*bottom navigation item click*/
        soshoTimelineBinding.bottomNavigation.setOnItemSelectedListener(position -> {
            Log.d(TAG, "onCreate: Bottom Navigation position: " + position);
            switch (position) {
                case 0:
                    soshoTimelineBinding.soshoViewPager.setCurrentItem(0);
                    break;

                case 1:
                    soshoTimelineBinding.soshoViewPager.setCurrentItem(1);
                    break;

                case 2:
                    soshoTimelineBinding.soshoViewPager.setCurrentItem(2);
                    break;

                case 3:
                    soshoTimelineBinding.soshoViewPager.setCurrentItem(3);
                    break;

                case 4:
                    soshoTimelineBinding.soshoViewPager.setCurrentItem(4);
                    break;
            }

            return true;
        });
    }
}
