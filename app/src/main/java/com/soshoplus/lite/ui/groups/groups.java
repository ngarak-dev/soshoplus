/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui.groups;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import com.soshoplus.lite.calls.joinedGroupsCalls;
import com.soshoplus.lite.calls.recommendedGroupsCalls;
import com.soshoplus.lite.databinding.ActivityGroupsBinding;

public class groups extends AppCompatActivity {

    private ActivityGroupsBinding groupsBinding;
    private com.soshoplus.lite.calls.recommendedGroupsCalls recommendedGroupsCalls;
    private com.soshoplus.lite.calls.joinedGroupsCalls joinedGroupsCalls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupsBinding = ActivityGroupsBinding.inflate(getLayoutInflater());
        View view = groupsBinding.getRoot();
        setContentView(view);

        /*get groups*/
        HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed(this::getJoined, 1000);

        HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed(this::getRecommended, 1000);

        groupsBinding.backArrow.setOnClickListener(view_ -> {
            onBackPressed();
        });

        /*refreshing groups*/
        groupsBinding.groupsRefreshLayout.setOnRefreshListener(() -> {
            /*get groups*/
            HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed(this::getJoined, 1000);

            HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed(this::getRecommended, 1000);

            groupsBinding.groupsRefreshLayout.setRefreshing(false);
        });
    }

    private void getRecommended() {
        /*recommended*/
        recommendedGroupsCalls = new recommendedGroupsCalls(groups.this);
        recommendedGroupsCalls.getRecommends(groupsBinding.suggestedGroupsList,
                groupsBinding.allSetUpImg,
                groupsBinding.allSetUpText, groupsBinding.progressBarSuggested);
    }

    private void getJoined() {
        /*joined*/
        joinedGroupsCalls = new joinedGroupsCalls(groups.this);
        joinedGroupsCalls.getJoined(groupsBinding.joinedGroupsList,
                groupsBinding.progressBarJoined,
                groupsBinding.joinedGroupsShowHereImg, groupsBinding.joinedGroupsShowHereTxt);
    }
}