/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui.user_profile;

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
import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.calls.simpleProfileCalls;
import com.soshoplus.lite.databinding.ActivityMyProfileBinding;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.models.simpleResponse;
import com.soshoplus.lite.ui.auth.signIn;
import com.soshoplus.lite.utils.constants;
import com.soshoplus.lite.utils.xpopup.changePasswordPopup;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import de.adorsys.android.securestoragelibrary.SecureStorageException;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class myProfile extends AppCompatActivity {

    private static String TAG = "my Profile ";
    private ActivityMyProfileBinding binding;
    private simpleProfileCalls calls;

    private Observable<com.soshoplus.lite.models.simpleResponse> simpleResponse;
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

        simpleResponse = constants.rxJavaQueries.logOutUser(constants.accessToken, BuildConfig.server_key, constants.userId);

        simpleResponse.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<simpleResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(com.soshoplus.lite.models.simpleResponse simpleResponse) {
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
                        } else {
                            apiErrors errors = simpleResponse.getErrors();
                            Log.d(TAG, "onNext: " + errors.getErrorId());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
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