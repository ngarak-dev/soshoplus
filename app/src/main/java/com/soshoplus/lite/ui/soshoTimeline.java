/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.soshoplus.lite.R;
import com.soshoplus.lite.adapters.viewPagerAdapter;
import com.soshoplus.lite.databinding.ActivitySoshotimelineBinding;
import com.soshoplus.lite.ui.mainfragments.moreFragment;
import com.soshoplus.lite.ui.mainfragments.notificationFragment;
import com.soshoplus.lite.ui.mainfragments.timelineFragment;
import com.soshoplus.lite.ui.user_profile.myProfile;

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
        setSupportActionBar(soshoTimelineBinding.transToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        
        /*adding fragments*/
        fragmentList.add(new timelineFragment());
        fragmentList.add(new notificationFragment());
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
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.horz_more_dots, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.options) {
            startActivity(new Intent(soshoTimeline.this, settings.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.moveTaskToBack(true);
    }
}
