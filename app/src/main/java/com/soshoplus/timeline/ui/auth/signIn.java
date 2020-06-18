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
import com.soshoplus.timeline.ui.soshoTimeline;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signIn extends AppCompatActivity {
    
    private static String server_key = "a41ab77c99ab5c9f46b66a894d97cce9";
    private static String TAG = "SignIn Activity ";
    boolean validate = true;
    private ActivitySigninBinding signInBinding;
    private Observable<accessToken> tokenObservable;
    private queries signInQuery;
    private ACProgressFlower acProgressFlower;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInBinding = ActivitySigninBinding.inflate(getLayoutInflater());
        View view = signInBinding.getRoot();
        setContentView(view);
        
        /*initializing progress dialog*/
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
    
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run () {
                        callSignIn();
                    }
                });
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
        signInQuery = retrofitInstance.getInstRxJava().create(queries.class);
    
        tokenObservable = signInQuery.signIn(server_key, username, password);
        
        tokenObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<accessToken>() {
                    @Override
                    public void onSubscribe (@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
    
                    @Override
                    public void onNext (@io.reactivex.rxjava3.annotations.NonNull accessToken accessToken) {
                        if (accessToken.getApiStatus() == 200) {
    
                            //dismiss progress dialog
                            acProgressFlower.dismiss();
    
                            //storing user session
                            SharedPreferences pref = getApplicationContext().getSharedPreferences(
                                    "userCred", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
    
                            editor.putString("userId", accessToken.getUserId());
                            editor.putString("timezone",
                                    accessToken.getTimezone());
                            editor.putString("accessToken",
                                    accessToken.getAccessToken());
                            editor.apply();
                            
                            startActivity(new Intent(signIn.this, soshoTimeline.class));
                            finish();
                        }
                        else {
                            //dismiss dialog and log output
                            acProgressFlower.dismiss();
                            apiErrors apiErrors = accessToken.getErrors();
    
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
    
                            /*displaying a dialog*/
                            CFAlertDialog.Builder builder =
                                    new CFAlertDialog.Builder(signIn.this)
                                            .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                            .setCancelable(false)
                                            .setTitle("Oops !")
                                            .setMessage(apiErrors.getErrorText())
                                            .addButton("DISMISS", -1, -1,
                                                    CFAlertDialog.CFAlertActionStyle.DEFAULT,
                                                    CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (
                                                            dialog, which) -> dialog.dismiss());
                            builder.show();
                            
                        }
                    }
    
                    @Override
                    public void onError (@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
    
                        //dismiss progress dialog
                        acProgressFlower.dismiss();
                        
                        /*displaying a dialog*/
                        CFAlertDialog.Builder builder =
                                new CFAlertDialog.Builder(signIn.this)
                                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                        .setCancelable(false)
                                        .setTitle("Oops !")
                                        .setMessage("Something went " +
                                                "wrong\nPlease check your " +
                                                "internet connection")
                                        .addButton("TRY AGAIN", -1 ,-1,
                                                CFAlertDialog.CFAlertActionStyle.DEFAULT,
                                                CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (
                                                        (dialogInterface, i) -> {
                                                            dialogInterface.dismiss();
                                                            callSignIn();
                                                        }))
                                        .addButton("DISMISS", -1, -1,
                                                CFAlertDialog.CFAlertActionStyle.DEFAULT,
                                                CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (
                                                        dialog, which) -> dialog.dismiss());
                        builder.show();
                    }
    
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
