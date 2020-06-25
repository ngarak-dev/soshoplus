/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.adapters.viewPagerAdapter;
import com.soshoplus.timeline.databinding.ActivitySoshotimelineBinding;
import com.soshoplus.timeline.ui.mainfragments.friendsFragment;
import com.soshoplus.timeline.ui.mainfragments.groupsFragment;
import com.soshoplus.timeline.ui.mainfragments.moreFragment;
import com.soshoplus.timeline.ui.mainfragments.profileFragment;
import com.soshoplus.timeline.ui.mainfragments.timelineFragment;
import com.soshoplus.timeline.utils.retrofitCalls;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class soshoTimeline extends AppCompatActivity {
    
    private ActivitySoshotimelineBinding soshoTimelineBinding;
    private static String TAG = "soshoTimeline Activity";
    private ShapeableImageView profile_pic;
    private TextView full_name, username;
    private retrofitCalls calls;
    private ViewPager sosho_viewPager;
    private viewPagerAdapter viewPagerAdapter;
    private BubbleTabBar bubbleTabBar;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        soshoTimelineBinding = ActivitySoshotimelineBinding.inflate(getLayoutInflater());
        View view = soshoTimelineBinding.getRoot();
        setContentView(view);
        
        //view declaration
        profile_pic = soshoTimelineBinding.profilePic;
        full_name = soshoTimelineBinding.fullName;
        username = soshoTimelineBinding.username;
        bubbleTabBar = soshoTimelineBinding.bottomNavigation;
        sosho_viewPager = soshoTimelineBinding.soshoViewPager;
        
        //getCurrent Profile
        getProfile();
        
        //initialize adapter
        viewPagerAdapter = new viewPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        
        // adding Fragments
        viewPagerAdapter.addFragment(new timelineFragment());
        viewPagerAdapter.addFragment(new friendsFragment());
        viewPagerAdapter.addFragment(new groupsFragment());
        viewPagerAdapter.addFragment(new profileFragment());
        viewPagerAdapter.addFragment(new moreFragment());
        
        int limit = ( viewPagerAdapter.getCount() > 1 ? viewPagerAdapter.getCount() -1:1);
        /*retaining fragments*/
        sosho_viewPager.setOffscreenPageLimit(limit);
        //setView Pager Adapter
        sosho_viewPager.setAdapter(viewPagerAdapter);
        //initialize Bottom Navigation
        bottomNavigationListener();
        
        /*profile click*/
        soshoTimelineBinding.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                sosho_viewPager.setCurrentItem(3);
            }
        });
    }
    
    private void bottomNavigationListener () {
        /*setup viewpager with bubble tabbar*/
        bubbleTabBar.setupBubbleTabBar(sosho_viewPager);
        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick (int id) {
                switch (id) {
                    case R.id.home:
                        sosho_viewPager.setCurrentItem(0);
                        break;
                
                    case R.id.friends:
                        sosho_viewPager.setCurrentItem(1);
                        break;
                
                    case R.id.groups:
                        sosho_viewPager.setCurrentItem(2);
                        break;
                
                    case R.id.profile:
                        sosho_viewPager.setCurrentItem(3);
                        break;
                
                    case R.id.more:
                        sosho_viewPager.setCurrentItem(4);
                        break;
                }
            }
        });
    }

    private void getProfile () {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run () {
                calls = new retrofitCalls(soshoTimeline.this);
                calls.getProfile(profile_pic, full_name, username);
            }
        });
    }
    
    //overriding back button
    @Override
    public void onBackPressed() {
        if (sosho_viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            // go to previous step
            /*TODO should go to previous clicked*/
            sosho_viewPager.setCurrentItem(sosho_viewPager.getCurrentItem() - 1);
        }
    }
}
