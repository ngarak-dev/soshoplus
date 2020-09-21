/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui.settings_pref;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.soshoplus.lite.databinding.ActivityHelpSupportBinding;

public class helpSupport extends AppCompatActivity {

    private static String TAG = "Help Support Activity";
    private static String[] strings = {"Help Center", "Privacy Policy", "Terms of use", "About Us"};
    private ActivityHelpSupportBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHelpSupportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.backArrow.setOnClickListener(view_ -> {
            onBackPressed();
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
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