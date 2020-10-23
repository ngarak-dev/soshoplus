/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui.settings_pref;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.databinding.ActivityGeneralAccountBinding;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.models.userprofile.userInfo;
import com.soshoplus.lite.utils.queries;
import com.soshoplus.lite.utils.retrofitInstance;
import com.soshoplus.lite.utils.xpopup.changeAboutPopup;
import com.soshoplus.lite.utils.xpopup.changePasswordPopup;
import com.soshoplus.lite.utils.xpopup.socialLinksPopup;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class generalAccount extends AppCompatActivity {

    private static String TAG = "General Settings";
    private static String[] general_strings = {"About me", "Social Links", "My Profile", "Verification", "Blocked Users"};
    private static String[] security_strings = {"Change Password", "Two-factor authentication", "Manage Sessions"};
    private static String userId, timezone, accessToken;
    private static String fetch_profile_user_data = "user_data";
    private ActivityGeneralAccountBinding binding;
    private BasePopupView basePopupView;
    private Observable<userInfo> userInfoObservable;
    private queries rxJavaQueries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGeneralAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.backArrow.setOnClickListener(view_ -> {
            onBackPressed();
        });

        userId = SecurePreferences.getStringValue(generalAccount.this, "userId", "0");
        timezone = SecurePreferences.getStringValue(generalAccount.this, "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(generalAccount.this, "accessToken", "0");
        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);

        /*initializing loading dialog*/
        basePopupView = new XPopup.Builder(generalAccount.this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .autoDismiss(false)
                .asLoading();

        ArrayAdapter<String> generalAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, general_strings);
        binding.generalSettingsList.setAdapter(generalAdapter);

        ArrayAdapter<String> securityAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, security_strings);
        binding.securitySettingsList.setAdapter(securityAdapter);

        /*general list*/
        binding.generalSettingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        basePopupView.show();
                        gettingAbout();
                        break;

                    case 1:
                        basePopupView.show();
                        gettingSocialLinks();
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

    private void gettingAbout() {
        userInfoObservable = rxJavaQueries.getUserData(accessToken,
                BuildConfig.server_key, fetch_profile_user_data, userId);

        userInfoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<userInfo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull userInfo userInfo) {
                        if (userInfo.getApiStatus() == 200) {
                            basePopupView.dismissWith(new Runnable() {
                                @Override
                                public void run() {

                                    new XPopup.Builder(generalAccount.this)
                                            .asCustom(new changeAboutPopup(generalAccount.this,
                                                    userInfo.getUserData().getAbout())).show();
                                }
                            });
                        } else {
                            apiErrors apiErrors = userInfo.getErrors();
                            Log.d(TAG, "ERROR: " + apiErrors.getErrorId());

                            Toast toast = Toast.makeText(generalAccount.this, "Something went wrong ... ", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();

                            onBackPressed();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());

                        Toast toast = Toast.makeText(generalAccount.this, "Something went wrong ... ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();

                        onBackPressed();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void gettingSocialLinks() {
        userInfoObservable = rxJavaQueries.getUserData(accessToken,
                BuildConfig.server_key, fetch_profile_user_data, userId);

        userInfoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<userInfo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull userInfo userInfo) {
                        if (userInfo.getApiStatus() == 200) {
                            basePopupView.dismissWith(new Runnable() {
                                @Override
                                public void run() {

                                    new XPopup.Builder(generalAccount.this)
                                            .asCustom(new socialLinksPopup(generalAccount.this,
                                                    userInfo.getUserData().getFacebook(),
                                                    userInfo.getUserData().getTwitter(),
                                                    userInfo.getUserData().getLinkedin(),
                                                    userInfo.getUserData().getInstagram(),
                                                    userInfo.getUserData().getYoutube())).show();
                                }
                            });
                        } else {
                            apiErrors apiErrors = userInfo.getErrors();
                            Log.d(TAG, "ERROR: " + apiErrors.getErrorId());

                            Toast toast = Toast.makeText(generalAccount.this, "Something went wrong ... ", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();

                            onBackPressed();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());

                        Toast toast = Toast.makeText(generalAccount.this, "Something went wrong ... ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();

                        onBackPressed();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}