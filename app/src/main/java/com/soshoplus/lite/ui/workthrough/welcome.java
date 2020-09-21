/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui.workthrough;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.soshoplus.lite.databinding.ActivityWelcomeBinding;

public class welcome extends AppCompatActivity {

    private static String TAG = "Welcome Activity";
    private ActivityWelcomeBinding binding;
    private static String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityWelcomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toSecondStep.setOnClickListener(view_ -> {
            binding.firstStepCard.setVisibility(View.GONE);
            binding.secondStepCard.setVisibility(View.VISIBLE);
        });

        binding.toFirstStep.setOnClickListener(view_ -> {
            binding.firstStepCard.setVisibility(View.VISIBLE);
            binding.secondStepCard.setVisibility(View.GONE);
        });
    }

    public void onGenderRadioClick(View view) {

    }
}