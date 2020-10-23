/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui.workthrough;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.R;
import com.soshoplus.lite.databinding.ActivityWelcomeBinding;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.models.simpleResponse;
import com.soshoplus.lite.ui.soshoTimeline;
import com.soshoplus.lite.utils.queries;
import com.soshoplus.lite.utils.retrofitInstance;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class welcome extends AppCompatActivity {

    private static String TAG = "Welcome Activity";
    private static String gender;
    private static String userId, timezone, accessToken;
    private ActivityWelcomeBinding binding;
    private BasePopupView basePopupView;
    private Observable<simpleResponse> simpleResponseObservable;
    private queries rxJavaQueries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /*initializing loading dialog*/
        basePopupView = new XPopup.Builder(welcome.this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .autoDismiss(false)
                .asLoading();

        userId = SecurePreferences.getStringValue(welcome.this, "userId", "0");
        timezone = SecurePreferences.getStringValue(welcome.this, "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(welcome.this, "accessToken", "0");
        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);

        binding.toSecondStep.setOnClickListener(view_ -> {
            if (binding.fName.getText().toString().isEmpty()) {
                binding.fNameInLayout.setError("First Name is required");
            } else if (binding.fName.getText().toString().length() < 3) {
                binding.fNameInLayout.setError("Enter a valid name");
            }
            if (binding.lName.getText().toString().isEmpty()) {
                binding.lNameInLayout.setError("Last Name is required");
            } else if (binding.lName.getText().toString().length() < 3) {
                binding.lNameInLayout.setError("Enter a valid name");
            } else if (binding.fName.getText().toString().contains("test")) {
                binding.fNameInLayout.setError("Enter a valid name");
            } else if (binding.fName.getText().toString().contains("test")) {
                binding.lNameInLayout.setError("Enter a valid name");
            } else {
                binding.firstStepCard.setVisibility(View.GONE);
                binding.secondStepCard.setVisibility(View.VISIBLE);
                binding.skip.setVisibility(View.VISIBLE);
            }
        });

        binding.toFirstStep.setOnClickListener(view_ -> {
            binding.firstStepCard.setVisibility(View.VISIBLE);
            binding.secondStepCard.setVisibility(View.GONE);
            binding.skip.setVisibility(View.GONE);
        });

        binding.skip.setOnClickListener(view_ -> {
            submitToServer();
        });

        binding.toSubmit.setOnClickListener(view_ -> {
            submitToServer();
        });
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
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

        Log.d(TAG, "onGenderRadioClick: " + gender);
    }

    private void submitToServer() {
        /*show progress loader*/
        basePopupView.show();

        simpleResponseObservable = rxJavaQueries.updateUserData(accessToken,
                BuildConfig.server_key, null, binding.fName.getText().toString(),
                binding.lName.getText().toString(), null, null,
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

                            basePopupView.dismissWith(() -> {
                                startActivity(new Intent(welcome.this, soshoTimeline.class));
                                finish();
                            });
                        } else {
                            apiErrors apiErrors = simpleResponse.getErrors();
                            Log.d(TAG, "ERROR: " + apiErrors.getErrorId());

                            Toast toast = Toast.makeText(welcome.this, "Something went wrong ... ", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();

                            basePopupView.smartDismiss();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());

                        Toast toast = Toast.makeText(welcome.this, "Something went wrong ... ", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();

                        basePopupView.smartDismiss();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}