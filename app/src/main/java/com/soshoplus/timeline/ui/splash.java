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

import androidx.appcompat.app.AppCompatActivity;

import com.soshoplus.timeline.R;

public class splash extends AppCompatActivity {
    
    private static final String TAG = "splash Activity ";
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        new Handler().postDelayed(() -> {
            //retrieving user session
            SharedPreferences pref = getApplicationContext().getSharedPreferences("userCred", 0); // 0 - for private mode
            
            if (pref.contains("userId")) {
                String userId = pref.getString("userId", "0");
                String timezone = pref.getString("timezone", "0");
                String accessToken = pref.getString("accessToken", "0");
    
                /*TO MAIN ACTIVITY FOR NOW*/
                /*TODO*/
                /*LATER IMPLEMENT KITU KINGINE*/
                startActivity(new Intent(splash.this, soshoTimeline.class));
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
