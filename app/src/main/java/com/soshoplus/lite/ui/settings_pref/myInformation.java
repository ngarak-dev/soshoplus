/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui.settings_pref;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.soshoplus.lite.R;
import com.soshoplus.lite.databinding.ActivityMyInformationBinding;

public class myInformation extends AppCompatActivity {

    private ActivityMyInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyInformationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.backArrow.setOnClickListener(view_ -> {
            onBackPressed();
        });
    }
}