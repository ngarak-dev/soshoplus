/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.soshoplus.timeline.R;
import com.soshoplus.timeline.databinding.ActivitySplashBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class splash extends AppCompatActivity {
    
    private static final String TAG = "splash Activity ";
    ActivitySplashBinding splashBinding;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = splashBinding.getRoot();
        setContentView(view);
        
        new Handler().postDelayed(() -> {
            //retrieving user session
            SharedPreferences pref = getApplicationContext().getSharedPreferences("userCred", 0); // 0 - for private mode
            
            if (pref.contains("userId")) {
                startActivity(new Intent(splash.this, soshoTimeline.class));
                finish();
                
            } else {
                Log.d(TAG, "run: " + "we do not have session");
                //go to getStarted
                startActivity(new Intent(splash.this, getStarted.class));
                finish();
            }
        }, 2000);
    }
}
