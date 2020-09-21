/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.utils.xpopup;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.CenterPopupView;
import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.R;
import com.soshoplus.lite.models.apiErrors;
import com.soshoplus.lite.models.simpleResponse;
import com.soshoplus.lite.utils.queries;
import com.soshoplus.lite.utils.retrofitInstance;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class changePasswordPopup extends CenterPopupView {

    private static String TAG = "Change password popup";

    private TextInputEditText old_pass;
    private TextInputEditText new_pass;
    private MaterialButton send_btn;
    private BasePopupView popupView;
    /*........*/
    private String userId, timezone, accessToken;
    private queries rxJavaQueries;
    private Observable<simpleResponse> simpleResponse;

    public changePasswordPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.change_password_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        userId = SecurePreferences.getStringValue(getContext(), "userId", "0");
        timezone = SecurePreferences.getStringValue(getContext(), "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(getContext(), "accessToken"
                , "0");

        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);

        /*initializing loading dialog*/
        popupView = new XPopup.Builder(getContext())
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .autoDismiss(false)
                .asLoading("Updating..");

         old_pass = findViewById(R.id.old_password);
         new_pass = findViewById(R.id.new_password);
         send_btn = findViewById(R.id.send);

         send_btn.setOnClickListener(view -> {
             if (old_pass.getText().toString().isEmpty()) {
                 old_pass.setError("Password is empty");
             }
             else if (new_pass.getText().toString().isEmpty()) {
                 new_pass.setError("Password is empty");
             }
             else if (new_pass.getText().toString().length() < 6) {
                 new_pass.setError("Weak password");
             }
             else {
                 sendChangePassword(old_pass.getText().toString(), new_pass.getText().toString());
             }
         });
    }

    private void sendChangePassword(String oldPassword, String newPassword) {
        dismissWith(() -> popupView.show());

        simpleResponse = rxJavaQueries.changePassword(accessToken, BuildConfig.server_key, oldPassword, newPassword, userId);

        simpleResponse.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<simpleResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(com.soshoplus.lite.models.simpleResponse simpleResponse) {
                        if (simpleResponse.getApiStatus() == 200) {
                            Log.d(TAG, "onNext: " + "password changed");

                            popupView.dismiss();
                            Toast.makeText(getContext(), "Password Changed", Toast.LENGTH_LONG).show();
                        }
                        else {
                            apiErrors errors = simpleResponse.getErrors();
                            Log.d(TAG, "onNext: " + errors.getErrorText());

                            popupView.dismiss();
                            Toast.makeText(getContext(), errors.getErrorText(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        popupView.dismiss();
                        Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    @Override
    protected int getMaxWidth () {
        return super.getMaxWidth();
    }

    @Override
    protected int getMaxHeight () {
        return super.getMaxHeight();
    }

    @Override
    protected int getPopupWidth () {
        return 0;
    }

    @Override
    protected int getPopupHeight () {
        return 0;
    }
}
