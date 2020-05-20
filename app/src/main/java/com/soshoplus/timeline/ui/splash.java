/*
 * Ngara K
 * Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 */

package com.soshoplus.timeline.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.soshoplus.timeline.R;

public class splash extends AppCompatActivity {
    
    private static final String TAG = "splash Activity ";
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        new Handler().postDelayed(() -> {
            //storing user session
            SharedPreferences pref = getApplicationContext().getSharedPreferences("session", 0); // 0 - for private mode
            
            if (pref != null) {
                String userId = pref.getString("userId", null);
                String timezone = pref.getString("timezone", null);
                String accessToken = pref.getString("accessToken", null);
                
                Log.d(TAG, "run: " + "hey we have an active session here");
                Log.d(TAG, "run: " + userId);
                Log.d(TAG, "run: " + timezone);
                Log.d(TAG, "run: " + accessToken);
                
                //go to mainActivity
                startActivity(new Intent(splash.this, getStarted.class));
                finish();
            } else {
                Log.d(TAG, "run: " + "we do not have session");
                //go to getStarted
                startActivity(new Intent(splash.this, getStarted.class));
                finish();
            }
        }, 2500);
    }
}
