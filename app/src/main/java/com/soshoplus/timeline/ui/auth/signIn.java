/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.onurkagan.ksnack_lib.KSnack.KSnack;
import com.soshoplus.timeline.BuildConfig;
import com.soshoplus.timeline.databinding.ActivitySigninBinding;
import com.soshoplus.timeline.models.accessToken;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.ui.soshoTimeline;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import de.adorsys.android.securestoragelibrary.SecureStorageException;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class signIn extends AppCompatActivity {
    
    private static String TAG = "SignIn Activity ";
    boolean validate = true;
    private ActivitySigninBinding signInBinding;
    private Observable<accessToken> tokenObservable;
    private queries signInQuery;
    private BasePopupView popupView;
    private KSnack kSnack;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInBinding = ActivitySigninBinding.inflate(getLayoutInflater());
        View view = signInBinding.getRoot();
        setContentView(view);
        
        /*initializing loading dialog*/
        popupView = new XPopup.Builder(signIn.this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .autoDismiss(false)
                .asLoading("Please wait");
        
        /*initializing ksnack*/
        kSnack = new KSnack(signIn.this);

        signInBinding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                //validate input
                if (!validate()) {
                    return;
                }
                //start progress dialog and attempt login
                popupView.show();
    
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run () {
                        callSignIn();
                    }
                });
                executorService.shutdown();
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
    
        tokenObservable = signInQuery.signIn(BuildConfig.server_key, username
                , password);
        
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
    
                            //storing user session
                            try {
                                SecurePreferences.setValue(signIn.this, "userId",
                                        accessToken.getUserId());
                                SecurePreferences.setValue(signIn.this,
                                        "timezone", accessToken.getTimezone());
                                SecurePreferences.setValue(signIn.this,
                                        "accessToken", accessToken.getAccessToken());
                                
                            } catch (SecureStorageException e) {
                                e.printStackTrace();
                                /*TODO
                                *  Handle this exception*/
                            }
                            
                            //dismiss progress dialog
                            popupView.dismissWith(new Runnable() {
                                @Override
                                public void run () {
                                    startActivity(new Intent(signIn.this, soshoTimeline.class));
                                    finish();
                                }
                            });
                        }
                        else {
                            //dismiss dialog and log output
                            apiErrors apiErrors = accessToken.getErrors();
    
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
                            Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
    
                            /*displaying a snack dialog*/
                            popupView.dismissWith(() -> {
                                
                                kSnack.setMessage("Oops !\n" + apiErrors.getErrorText());
                                kSnack.show();
                                kSnack.setAction("DISMISS", view -> {
                                    kSnack.dismiss();
                                });
                                kSnack.setDuration(3000);
                                
                            });
                        }
                    }
    
                    @Override
                    public void onError (@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
    
                        //dismiss progress dialog
                        /*displaying a snack dialog*/
                        popupView.dismissWith(() -> {
        
                            kSnack.setMessage("Oops ! \nSomething " +
                                    "went wrong\nPlease check your internet " +
                                    "connection");
                            kSnack.show();
                            kSnack.setAction("DISMISS", view -> {
                                kSnack.dismiss();
                            });
                            kSnack.setDuration(3000);
        
                        });
                    }
    
                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
