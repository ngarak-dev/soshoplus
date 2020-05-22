/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.auth;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.soshoplus.timeline.databinding.ActivitySignupBinding;
import com.soshoplus.timeline.models.accessToken;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;

import java.util.Objects;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signUp extends AppCompatActivity {
    
    private static String server_key = "a41ab77c99ab5c9f46b66a894d97cce9";
    private static String TAG = "signUp Activity ";
    boolean validate = true;
    private ActivitySignupBinding signUpBinding;
    private ACProgressFlower acProgressFlower;
    private Call<accessToken> accessTokenCall;
    private queries createAccountQuery;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = signUpBinding.getRoot();
        setContentView(view);
        
        acProgressFlower = new ACProgressFlower.Builder(signUp.this).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(Color.WHITE).text("Please Wait").textSize(16).petalCount(15).speed(18).petalThickness(2).build();
        acProgressFlower.setCanceledOnTouchOutside(false);
        
        signUpBinding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                //validate input
                if (!validate()) {
                    return;
                }
                //start progress dialog and attempt signing Up
                acProgressFlower.show();
                callSignUp();
            }
            
            private boolean validate () {
                if (Objects.requireNonNull(signUpBinding.email.getText()).toString().isEmpty()) {
                    signUpBinding.emailInLayout.setError("Email is empty");
                    validate = false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(signUpBinding.email.getText().toString()).matches()) {
                    signUpBinding.emailInLayout.setError("Invalid Email format");
                    validate = false;
                }
                
                if (Objects.requireNonNull(signUpBinding.username.getText()).toString().isEmpty()) {
                    signUpBinding.usernameInLayout.setError("Username is empty");
                    validate = false;
                }
                
                if (Objects.requireNonNull(signUpBinding.password.getText()).toString().isEmpty()) {
                    signUpBinding.passwordInLayout.setError("Password is empty");
                    validate = false;
                } else if (!signUpBinding.password.getText().toString().equals(Objects.requireNonNull(signUpBinding.rePassword.getText()).toString())) {
                    signUpBinding.passwordInLayout.setError("Password do not match");
                    signUpBinding.rePasswordInLayout.setError("Password do not match");
                    validate = false;
                }
                return validate;
            }
        });
        
        signUpBinding.btnToSignIn.setOnClickListener(v -> startActivity(new Intent(signUp.this, signIn.class)));
    }
    
    private void callSignUp () {
        String email = Objects.requireNonNull(signUpBinding.email.getText()).toString();
        String username = Objects.requireNonNull(signUpBinding.username.getText()).toString();
        String phone_num = Objects.requireNonNull(signUpBinding.phone.getText()).toString();
        String password = Objects.requireNonNull(signUpBinding.password.getText()).toString();
        String re_password = Objects.requireNonNull(signUpBinding.rePassword.getText()).toString();
        
        //Initializing Retrofit Instance for signUp
        createAccountQuery = retrofitInstance.getRetrofitInst().create(queries.class);
        //query
        accessTokenCall = createAccountQuery.createAccount(server_key, email, username, password, re_password, phone_num);
        accessTokenCall.enqueue(new Callback<accessToken>() {
            @Override
            public void onResponse (@NonNull Call<accessToken> call, @NonNull Response<accessToken> response) {
                if (response.body() != null) {
                    
                    if (response.body().getApiStatus() == 200) {
                        //dismiss progress dialog
                        acProgressFlower.dismiss();
                        
                        Log.d(TAG, "onResponse: " + response.body().getApiStatus());
                        Log.d(TAG, "onResponse: " + response.body().getAccessToken());
                        Log.d(TAG, "onResponse: " + response.body().getTimezone());
                        Log.d(TAG, "onResponse: " + response.body().getUserId());
                        
                        String[] strings = {response.body().getTimezone(), response.body().getUserId(), response.body().getAccessToken()};
                        
                        //show alert
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(signUp.this).setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET).setItems(strings, (dialog, which) -> {
                            //null
                        }).addButton("DISMISS", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> dialog.dismiss());
                        builder.show();
                        
                    } else {
                        //dismiss dialog and log output
                        acProgressFlower.dismiss();
                        apiErrors apiErrors = response.body().getErrors();
                        
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                        Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
                        
                        //show alert
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(signUp.this).setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT).setTitle("Oops !").setMessage(apiErrors.getErrorText()).addButton("DISMISS", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> dialog.dismiss());
                        builder.show();
                    }
                } else {
                    //response is null
                    Log.d(TAG, "onResponse: " + "Response is Null");
                }
            }
            
            @Override
            public void onFailure (@NonNull Call<accessToken> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
