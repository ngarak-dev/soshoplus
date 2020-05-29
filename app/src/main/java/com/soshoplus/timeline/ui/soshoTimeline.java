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

import com.google.android.material.imageview.ShapeableImageView;
import com.soshoplus.timeline.databinding.ActivityMainBinding;
import com.soshoplus.timeline.utils.retrofitCalls;

public class soshoTimeline extends AppCompatActivity {
    
    private ActivityMainBinding mainBinding;
    private static String TAG = "soshoTimeline Activity";
    private ShapeableImageView profile_pic;
    private TextView full_name, username;
    private retrofitCalls calls;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);
        
        profile_pic = mainBinding.profilePic;
        full_name = mainBinding.fullName;
        username = mainBinding.username;
        
        getProfile();
    }
    
    private void getProfile () {
        calls = new retrofitCalls(this);
        calls.getProfile(profile_pic, full_name, username);
    }
}
