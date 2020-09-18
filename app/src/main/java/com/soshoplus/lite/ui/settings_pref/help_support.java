/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui.settings_pref;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.soshoplus.lite.R;
import com.soshoplus.lite.databinding.ActivityHelpSupportBinding;

public class help_support extends AppCompatActivity {

    private static String TAG = "Help Support Activity";
    private ActivityHelpSupportBinding binding;
    private static String [] strings = {"Help Center", "Privacy Policy", "Terms of use", "About Us"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHelpSupportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.backArrow.setOnClickListener(view_ -> {
            onBackPressed();
        });

        ArrayAdapter<String> arrayAdapter  = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strings);
        binding.settingsAbout.setAdapter(arrayAdapter);

        binding.settingsAbout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d(TAG, "onItemClick Position: " + position);
            }
        });
    }
}