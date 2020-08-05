/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.groups;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import com.soshoplus.timeline.calls.groupCalls;
import com.soshoplus.timeline.databinding.ActivityViewGroupBinding;

public class viewGroup extends AppCompatActivity {
    
    private ActivityViewGroupBinding viewGroupBinding;
    private groupCalls calls;
    private String group_id;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewGroupBinding = ActivityViewGroupBinding.inflate(getLayoutInflater());
        View view = viewGroupBinding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        group_id = intent.getStringExtra("group_id");

        /*get group Info*/
        HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed(this::getGroupInfo, 300);
    }
    
    /*get group info*/
    private void getGroupInfo () {
        calls = new groupCalls(this);
        calls.getGroupInfo(viewGroupBinding.groupProfilePic, viewGroupBinding.groupCover,
                viewGroupBinding.noMembers,viewGroupBinding.groupPrivacy, viewGroupBinding.groupCategory, group_id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }
}