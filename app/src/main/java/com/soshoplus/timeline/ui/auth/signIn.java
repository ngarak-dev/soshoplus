/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.soshoplus.timeline.databinding.ActivitySigninBinding;
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

public class signIn extends AppCompatActivity {
    
    private static String server_key = "a41ab77c99ab5c9f46b66a894d97cce9";
    private static String TAG = "SignIn Activity ";
    boolean validate = true;
    private ActivitySigninBinding signInBinding;
    private Call<accessToken> accessTokenCall;
    private queries signInQuery;
    private ACProgressFlower acProgressFlower;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInBinding = ActivitySigninBinding.inflate(getLayoutInflater());
        View view = signInBinding.getRoot();
        setContentView(view);
        
        acProgressFlower = new ACProgressFlower.Builder(signIn.this).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(Color.WHITE).text("Please Wait").textSize(16).petalCount(15).speed(18).petalThickness(2).build();
        acProgressFlower.setCanceledOnTouchOutside(false);
        
        signInBinding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                //validate input
                if (!validate()) {
                    return;
                }
                //start progress dialog and attempt login
                acProgressFlower.show();
                callSignIn();
            }
            
            //client validate input
            private boolean validate () {
                if (Objects.requireNonNull(signInBinding.username.getText()).toString().isEmpty()) {
                    signInBinding.usernameInLayout.setError("Username is empty");
                    validate = false;
                }
                
                if (Objects.requireNonNull(signInBinding.password.getText()).toString().isEmpty()) {
                    signInBinding.passwordInLayout.setError("Password is empty");
                    validate = false;
                }
                return validate;
            }
        });
        
        signInBinding.btnToSignUp.setOnClickListener(v -> startActivity(new Intent(signIn.this, signUp.class)));
        
        signInBinding.btnForgotPassword.setOnClickListener(v -> startActivity(new Intent(signIn.this, forgotPassword.class)));
    }
    
    private void callSignIn () {
        
        String username = Objects.requireNonNull(signInBinding.username.getText()).toString();
        String password = Objects.requireNonNull(signInBinding.password.getText()).toString();
        
        //Initializing Retrofit Instance for Login
        signInQuery = retrofitInstance.getRetrofitInst().create(queries.class);
        
        accessTokenCall = signInQuery.signIn(server_key, username, password);
        
        accessTokenCall.enqueue(new Callback<com.soshoplus.timeline.models.accessToken>() {
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
                        
                        //storing user session
                        SharedPreferences pref = getApplicationContext().getSharedPreferences(
                                "userCred", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        
                        editor.putString("userId", response.body().getUserId());
                        editor.putString("timezone", response.body().getTimezone());
                        editor.putString("accessToken", response.body().getAccessToken());
                        editor.apply();
                        
                        String[] strings = {response.body().getTimezone(), response.body().getUserId(), response.body().getAccessToken()};
                        
                        //show alert
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(signIn.this).setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET).setTitle("Successful").setItems(strings, (dialog, which) -> {
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
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(signIn.this).setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT).setTitle("Oops !").setMessage(apiErrors.getErrorText()).addButton("DISMISS", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> dialog.dismiss());
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
