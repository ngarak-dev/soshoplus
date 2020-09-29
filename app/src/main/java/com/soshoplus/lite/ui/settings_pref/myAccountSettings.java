/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui.settings_pref;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.R;
import com.soshoplus.lite.databinding.ActivityMyAccountSettingsBinding;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.models.simpleResponse;
import com.soshoplus.lite.models.userprofile.userData;
import com.soshoplus.lite.models.userprofile.userInfo;
import com.soshoplus.lite.utils.queries;
import com.soshoplus.lite.utils.retrofitInstance;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import dev.utils.app.HandlerUtils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class myAccountSettings extends AppCompatActivity {

    private static String TAG = "My Account Settings";
    private static String gender;
    private ActivityMyAccountSettingsBinding binding;
    private Observable<userInfo> userInfoObservable;
    private Observable<simpleResponse> simpleResponseObservable;
    private BasePopupView basePopupView;

    private static String userId, timezone, accessToken;
    private static String fetch_profile_user_data = "user_data";
    private queries rxJavaQueries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAccountSettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Glide.with(myAccountSettings.this)
                .load(R.drawable.circles_loader).into(binding.circleLoader);

        userId = SecurePreferences.getStringValue(myAccountSettings.this, "userId", "0");
        timezone = SecurePreferences.getStringValue(myAccountSettings.this, "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(myAccountSettings.this, "accessToken", "0");
        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);

        binding.backArrow.setOnClickListener(view_ -> {
            onBackPressed();
        });

        /*initializing loading dialog*/
        basePopupView = new XPopup.Builder(myAccountSettings.this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .autoDismiss(false)
                .asLoading();

        /*getting data*/
        HandlerUtils.postRunnable(this::getMyInfo, 2000);

        /*update data*/
        binding.updateBtn.setOnClickListener(view_ -> {
            HandlerUtils.postRunnable(this::updateData, 2000);
        });
    }

    /*getting data first*/
    private void getMyInfo() {
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

                            binding.circleLoader.setVisibility(View.GONE);
                            /*update UI data*/
                            HandlerUtils.postRunnable(() -> setData(userInfo.getUserData()));
                        } else {
                            apiErrors apiErrors = userInfo.getErrors();
                            Log.d(TAG, "ERROR: " + apiErrors.getErrorId());

                            Toast toast = Toast.makeText(myAccountSettings.this, "Something went wrong ... ", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();

                            onBackPressed();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());

                        Toast toast = Toast.makeText(myAccountSettings.this, "Something went wrong ... ", Toast.LENGTH_LONG);
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

    /*update UI data*/
    private void setData(userData userData) {
        binding.username.setText(userData.getUsername());
        binding.fName.setText(userData.getFirstName());
        binding.lName.setText(userData.getLastName());
        binding.phone.setText(userData.getPhoneNumber());
        binding.email.setText(userData.getEmail());
        binding.workingAt.setText(userData.getWorking());
        binding.website.setText(userData.getWebsite());
        binding.university.setText(userData.getSchool());
        binding.address.setText(userData.getAddress());

        if (userData.getGender().equals("male")) {
            binding.maleRadio.setChecked(true);
            gender = "male";
        } else if (userData.getGender().equals("female")) {
            binding.femaleRadio.setChecked(true);
            gender = "female";
        } else {
            binding.cantSayRadio.setChecked(true);
            gender = "1906";
        }

        binding.myAccountUi.setVisibility(View.VISIBLE);
    }

    public void onGenderRadioClick(View view) {
        boolean checked = ((MaterialRadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.maleRadio:
                if (checked)
                    gender = "male";
                break;
            case R.id.femaleRadio:
                if (checked)
                    gender = "female";
                break;
            case R.id.cant_sayRadio:
                if (checked)
                    gender = "1906";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

        Log.d(TAG, "onGenderRadioClick: " + gender);
    }

    /*update data*/
    private void updateData() {
        /*show progress loader*/
        basePopupView.show();

        simpleResponseObservable = rxJavaQueries.updateUserData(accessToken,
                BuildConfig.server_key, binding.username.getText().toString(), binding.fName.getText().toString(),
                binding.lName.getText().toString(), binding.phone.getText().toString(), binding.email.getText().toString(),
                gender, binding.workingAt.getText().toString(), binding.website.getText().toString(), binding.university.getText().toString(),
                binding.address.getText().toString(), null, null, null, null, null, null);

        simpleResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<simpleResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull simpleResponse simpleResponse) {
                        if (simpleResponse.getApiStatus() == 200) {
                            Log.d(TAG, "onNext: " + simpleResponse.getMessage());

                            basePopupView.dismissWith(new Runnable() {
                                @Override
                                public void run() {

                                    /*move task back*/
                                    new XPopup.Builder(myAccountSettings.this)
                                            .asConfirm(simpleResponse.getMessage(), "", null,
                                                    "Okay", myAccountSettings.this::onBackPressed, null,
                                                    true, 0).show();
                                }
                            });
                        } else {
                            apiErrors apiErrors = simpleResponse.getErrors();
                            Log.d(TAG, "ERROR: " + apiErrors.getErrorId());

                            Toast toast = Toast.makeText(myAccountSettings.this, "Something went wrong ... ", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();

                            onBackPressed();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());

                        Toast toast = Toast.makeText(myAccountSettings.this, "Something went wrong ... ", Toast.LENGTH_SHORT);
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