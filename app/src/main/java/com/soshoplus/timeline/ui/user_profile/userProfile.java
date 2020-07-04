/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.user_profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.soshoplus.timeline.databinding.ActivityUserProfileBinding;

public class userProfile extends AppCompatActivity {
    
    private ActivityUserProfileBinding userProfileBinding;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userProfileBinding =
                ActivityUserProfileBinding.inflate(getLayoutInflater());
        View view = userProfileBinding.getRoot();
        setContentView(view);
    }
}