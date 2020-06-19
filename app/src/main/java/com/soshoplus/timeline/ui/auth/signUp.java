/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupType;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.soshoplus.timeline.databinding.ActivitySignupBinding;
import com.soshoplus.timeline.models.accessToken;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.ui.soshoTimeline;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class signUp extends AppCompatActivity {
    
    private static String server_key = "a41ab77c99ab5c9f46b66a894d97cce9";
    private static String TAG = "signUp Activity ";
    boolean validate = true;
    private ActivitySignupBinding signUpBinding;
    private Observable<accessToken> tokenObservable;
    private queries createAccountQuery;
    private BasePopupView popupView;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = signUpBinding.getRoot();
        setContentView(view);
    
        /*initializing loading dialog*/
        popupView = new XPopup.Builder(signUp.this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .autoDismiss(false)
                .asLoading("Please wait");
        
        signUpBinding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                //validate input
                if (!validate()) {
                    return;
                }
                //start progress dialog and attempt signing Up
                popupView.show();
    
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run () {
                        callSignUp();
                    }
                });
                executorService.shutdown();
    
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
        createAccountQuery = retrofitInstance.getInstRxJava().create(queries.class);
        //query
        tokenObservable = createAccountQuery.createAccount(server_key, email, username,
                password, re_password, phone_num);
        
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
                            popupView.dismiss();
        
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
        
                            startActivity(new Intent(signUp.this,
                                    soshoTimeline.class));
                            finish();
                        }
                        else {
                            //dismiss dialog and log output
                            popupView.dismiss();
                            apiErrors apiErrors = accessToken.getErrors();
        
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
        
                            /*displaying a dialog*/
                            new XPopup.Builder(signUp.this).popupType(PopupType.Bottom).asConfirm(
                                    "Oops " + "!", apiErrors.getErrorText(),
                                    "DISMISS", null, null, null, false).show();
                        }
                    }
    
                    @Override
                    public void onError (@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
    
                        Log.d(TAG, "onError: " + e.getMessage());
    
                        //dismiss progress dialog
                        popupView.dismiss();
    
                        /*displaying a dialog*/
                        new XPopup.Builder(signUp.this).popupType(PopupType.Bottom).asConfirm("Oops " + "!", "Something went " + "wrong\nPlease check your " + "internet connection", "DISMISS", "TRY AGAIN", new OnConfirmListener() {
                            @Override
                            public void onConfirm () {
                                callSignUp();
                            }
                        }, null, false).show();
                    }
    
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
