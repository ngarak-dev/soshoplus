/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui.settings_pref;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.lxj.xpopup.XPopup;
import com.soshoplus.lite.databinding.ActivityGeneralAccountBinding;
import com.soshoplus.lite.utils.xpopup.changePasswordPopup;

public class generalAccount extends AppCompatActivity {

    private ActivityGeneralAccountBinding binding;
    private static String [] general_strings = {"About me", "Social Links", "My Profile", "Verification", "Blocked Users"};
    private static String [] security_strings = {"Change Password", "Two-factor authentication", "Manage Sessions"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGeneralAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.backArrow.setOnClickListener(view_ -> {
            onBackPressed();
        });

        ArrayAdapter<String> generalAdapter  = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, general_strings);
        binding.generalSettingsList.setAdapter(generalAdapter);

        ArrayAdapter<String> securityAdapter  = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, security_strings);
        binding.securitySettingsList.setAdapter(securityAdapter);

        /*general list*/
        binding.generalSettingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        break;

                    case 1:
                        break;

                    case 2:
                        startActivity(new Intent(generalAccount.this, myAccountSettings.class));
                        break;

                    case 3:
                        break;

                    case 4:
                        break;
                }
            }
        });

        /*security list*/
        binding.securitySettingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        new Handler().postDelayed(this::changePassword, 300);
                        break;

                    case 1:
                        break;

                    case 2:
                        break;
                }
            }

            private void changePassword() {
                new XPopup.Builder(generalAccount.this)
                        .asCustom(new changePasswordPopup(generalAccount.this)).show();
            }
        });
    }
}