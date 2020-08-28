/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.soshoplus.lite.databinding.ActivitySplashBinding;
import com.soshoplus.lite.ui.auth.signIn;

import de.adorsys.android.securestoragelibrary.SecurePreferences;

public class splash extends Activity {
    
    private static final String TAG = "splash Activity ";
    ActivitySplashBinding splashBinding;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /*binding*/
        splashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = splashBinding.getRoot();
        setContentView(view);
        
        new Handler().postDelayed(() -> {
            
            //retrieving user session
            if (SecurePreferences.contains(splash.this, "userId")) {
                startActivity(new Intent(splash.this, soshoTimeline.class));
    
            } else {
                Log.d(TAG, "run: " + "we do not have session");
                //go to getStarted
                startActivity(new Intent(splash.this, signIn.class));
            }
            finish();
            
        }, 2000);
    }
}
