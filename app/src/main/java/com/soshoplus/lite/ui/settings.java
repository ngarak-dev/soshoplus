/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui;

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
import com.soshoplus.lite.databinding.ActivitySettingsBinding;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.models.simpleResponse;
import com.soshoplus.lite.ui.auth.signIn;
import com.soshoplus.lite.ui.settings_pref.earnings;
import com.soshoplus.lite.ui.settings_pref.generalAccount;
import com.soshoplus.lite.ui.settings_pref.helpSupport;
import com.soshoplus.lite.ui.settings_pref.invitationsLinks;
import com.soshoplus.lite.ui.settings_pref.myInformation;
import com.soshoplus.lite.ui.settings_pref.notifications;
import com.soshoplus.lite.ui.settings_pref.privacy;
import com.soshoplus.lite.utils.constants;
import com.soshoplus.lite.utils.queries;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import de.adorsys.android.securestoragelibrary.SecureStorageException;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class settings extends AppCompatActivity {

    private static String TAG = "Settings Activity";
    private ActivitySettingsBinding binding;
    private static String [] strings = {"General Account", "Privacy", "Notifications", "Invitations Links", "My Information",
            "Earnings", "Help & Support", "Log out"};

    private queries rxJavaQueries;
    private Observable<simpleResponse> simpleResponse;
    private BasePopupView basePopupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivitySettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /*initializing loading dialog*/
        basePopupView = new XPopup.Builder(settings.this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .autoDismiss(false)
                .asLoading("Please wait");

        binding.backArrow.setOnClickListener(view_ -> {
            onBackPressed();
        });

        ArrayAdapter<String> arrayAdapter  = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strings);
        binding.settingsList.setAdapter(arrayAdapter);

        binding.settingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(settings.this, generalAccount.class));
                        break;

                    case 1:
                        startActivity(new Intent(settings.this, privacy.class));
                        break;

                    case 2:
                        startActivity(new Intent(settings.this, notifications.class));
                        break;

                    case 3:
                        startActivity(new Intent(settings.this, invitationsLinks.class));
                        break;

                    case 4:
                        startActivity(new Intent(settings.this, myInformation.class));
                        break;

                    case 5:
                        startActivity(new Intent(settings.this, earnings.class));
                        break;

                    case 6:
                        startActivity(new Intent(settings.this, helpSupport.class));
                        break;

                    case 7:
                        new Handler().postDelayed(this::logOutUser, 300);
                        break;
                }
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
                                        SecurePreferences.clearAllValues(settings.this);
                                        Log.d(TAG, "onNext: " + "USER LOGGED OUT");

                                        basePopupView.delayDismissWith(300, new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(settings.this, signIn.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });

                                    } catch (SecureStorageException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    apiErrors errors = simpleResponse.getErrors();
                                    Log.d(TAG, "onNext: " + errors.getErrorId());

                                    basePopupView.smartDismiss();
                                    Toast toast = Toast.makeText(settings.this, "Failed ... ", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                                    toast.show();
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d(TAG, "onError: "  + e.getMessage());

                                basePopupView.smartDismiss();
                                Toast toast = Toast.makeText(settings.this, "Failed ... ", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                                toast.show();
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete: ");
                            }
                        });
            }
        });
    }
}