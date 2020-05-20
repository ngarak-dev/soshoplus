/*
 * Ngara K
 * Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 */

package com.soshoplus.timeline.ui.auth;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.soshoplus.timeline.databinding.ActivityForgotPasswordBinding;

import java.util.Objects;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class forgotPassword extends AppCompatActivity {
    
    private static String TAG = "signUp Activity ";
    private boolean validate = true;
    private ActivityForgotPasswordBinding forgotPasswordBinding;
    private ACProgressFlower acProgressFlower;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forgotPasswordBinding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        View view = forgotPasswordBinding.getRoot();
        setContentView(view);
        
        acProgressFlower = new ACProgressFlower.Builder(forgotPassword.this).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(Color.WHITE).text("Please Wait").textSize(16).petalCount(15).speed(18).petalThickness(2).build();
        acProgressFlower.setCanceledOnTouchOutside(false);
        
        forgotPasswordBinding.btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                //validate input
                if (!validate()) {
                    return;
                }
                //start progress dialog and attempt login
                acProgressFlower.show();
                callResetInfo();
            }
            
            private boolean validate () {
                if (Objects.requireNonNull(forgotPasswordBinding.email.getText()).toString().isEmpty()) {
                    forgotPasswordBinding.emailInLayout.setError("Email is empty");
                    validate = false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(forgotPasswordBinding.email.getText().toString()).matches()) {
                    forgotPasswordBinding.emailInLayout.setError("Invalid Email format");
                    validate = false;
                }
                
                return validate;
            }
        });
    }
    
    private void callResetInfo () {
        //TO-DO
    }
}
