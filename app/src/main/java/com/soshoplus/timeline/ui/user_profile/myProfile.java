/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.user_profile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.soshoplus.timeline.BuildConfig;
import com.soshoplus.timeline.calls.simpleProfileCalls;
import com.soshoplus.timeline.databinding.ActivityMyProfileBinding;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.simpleResponse;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;
import com.soshoplus.timeline.ui.auth.signIn;
import com.soshoplus.timeline.utils.xpopup.changePasswordPopup;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import de.adorsys.android.securestoragelibrary.SecureStorageException;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class myProfile extends AppCompatActivity {

    private ActivityMyProfileBinding binding;
    private static String TAG = "my Profile ";
    private String userId, timezone, accessToken;
    /*..........*/
    private simpleProfileCalls calls;
    /*.........*/
    private queries rxJavaQueries;
    private Observable<com.soshoplus.timeline.models.simpleResponse> simpleResponse;
    private BasePopupView basePopupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /*setting up actionbar*/
        setSupportActionBar(binding.transToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userId = SecurePreferences.getStringValue(myProfile.this, "userId", "0");
        timezone = SecurePreferences.getStringValue(myProfile.this, "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(myProfile.this, "accessToken"
                , "0");

        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);

        new Handler().postDelayed(this::loadProfile, 500);

        /*initializing loading dialog*/
        basePopupView = new XPopup.Builder(myProfile.this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .autoDismiss(false)
                .asLoading("Please wait");

        /*logout*/
        binding.logout.setOnClickListener(_view -> {
            new Handler().postDelayed(this::logOutUser, 300);
        });

        /*change password*/
        binding.toChangePassword.setOnClickListener(__view -> {
            new Handler().postDelayed(this::changePassword, 300);
        });
    }

    private void loadProfile() {
        calls = new simpleProfileCalls(myProfile.this);
        calls.getProfile(binding.profilePic, binding.fullName, binding.userEmail);
    }

    /*logout user*/
    private void logOutUser() {
        /*show loading dialog*/
        basePopupView.show();

        simpleResponse = rxJavaQueries.logOutUser(accessToken, BuildConfig.server_key, userId);

        simpleResponse.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<simpleResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(com.soshoplus.timeline.models.simpleResponse simpleResponse) {
                        if (simpleResponse.getApiStatus() == 200) {

                            try {
                                SecurePreferences.clearAllValues(myProfile.this);
                                Log.d(TAG, "onNext: " + "USER LOGGED OUT");

                                basePopupView.delayDismissWith(300, new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(myProfile.this, signIn.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        startActivity(intent);
                                        ((FragmentActivity) myProfile.this).finish();
                                    }
                                });

                            } catch (SecureStorageException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            apiErrors errors = simpleResponse.getErrors();
                            Log.d(TAG, "onNext: " + errors.getErrorId());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: "  + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    /*change password*/
    private void changePassword() {
        new XPopup.Builder(myProfile.this).asCustom(new changePasswordPopup(myProfile.this)).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}