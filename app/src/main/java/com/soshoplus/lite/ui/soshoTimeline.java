/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.soshoplus.lite.R;
import com.soshoplus.lite.adapters.viewPagerAdapter;
import com.soshoplus.lite.databinding.ActivitySoshotimelineBinding;
import com.soshoplus.lite.ui.mainfragments.friendsFragment;
import com.soshoplus.lite.ui.mainfragments.moreFragment;
import com.soshoplus.lite.ui.mainfragments.postsFragment;

import java.util.ArrayList;
import java.util.List;

public class soshoTimeline extends AppCompatActivity {

    private static String TAG = "soshoTimeline Activity";
    private ActivitySoshotimelineBinding binding;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySoshotimelineBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /*adding fragments*/
        fragmentList.add(new postsFragment());
        fragmentList.add(new friendsFragment());
        fragmentList.add(new moreFragment());

        /*setting view pager adapter*/
        binding.soshoViewPager.setAdapter(new viewPagerAdapter(soshoTimeline.this, fragmentList));
        binding.soshoViewPager.setCurrentItem(0);

        /*disabling swiping viewpager 2*/
        binding.soshoViewPager.setUserInputEnabled(false);

        /*on page scroll listener*/
        binding.soshoViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        binding.bottomNavigation.getMenu().findItem(R.id.home).setChecked(true);
                        setTopTitle("Home");
                        break;

                    case 1:
                        binding.bottomNavigation.getMenu().findItem(R.id.friends).setChecked(true);
                        setTopTitle("Friends");
                        break;

                    case 2:
                        binding.bottomNavigation.getMenu().findItem(R.id.more).setChecked(true);
                        setTopTitle("More");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        /*bottom navigation item click*/
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    binding.soshoViewPager.setCurrentItem(0);
                    setTopTitle("Home");
                    break;

                case R.id.friends:
                    binding.soshoViewPager.setCurrentItem(1);
                    setTopTitle("Friends");
                    break;

                case R.id.more:
                    binding.soshoViewPager.setCurrentItem(2);
                    setTopTitle("More");
                    break;
            }
            return true;
        });

        /*to settings*/
        binding.settingsBtn.setOnClickListener(v -> {
            startActivity(new Intent(soshoTimeline.this, settings.class));
        });
    }

    /*setting fragment title*/
    private void setTopTitle(String title) {
        binding.homeTv.setText(title);
    }

    @Override
    public void onBackPressed() {
        super.moveTaskToBack(true);
    }
}
