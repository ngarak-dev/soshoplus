/*
 * Ngara K
 * Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 */

package com.soshoplus.timeline.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.soshoplus.timeline.databinding.ActivityGetstartedBinding;
import com.soshoplus.timeline.ui.auth.signIn;
import com.soshoplus.timeline.ui.auth.signUp;

public class getStarted extends AppCompatActivity {
    
    private ActivityGetstartedBinding getStartedBinding;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getStartedBinding = ActivityGetstartedBinding.inflate(getLayoutInflater());
        View view = getStartedBinding.getRoot();
        setContentView(view);
        
        getStartedBinding.btnToSignIn.setOnClickListener(v -> startActivity(new Intent(getStarted.this, signIn.class)));
        
        getStartedBinding.btnToSignUp.setOnClickListener(v -> startActivity(new Intent(getStarted.this, signUp.class)));
        
    }
}
