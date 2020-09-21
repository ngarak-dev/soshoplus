/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.utils.xpopup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
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
import com.soshoplus.lite.utils.constants;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@SuppressLint("ViewConstructor")
public class changeAboutPopup extends CenterPopupView {

    private static String TAG = "Change about me popup";
    private static String about_me;

    private TextInputEditText aboutMe;
    private MaterialButton send_btn;
    private MaterialButton cancel_btn;

    private BasePopupView basePopupView;
    private Observable<simpleResponse> simpleResponseObservable;

    public changeAboutPopup(@NonNull Context context, String about) {
        super(context);
        about_me = about;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.about_me_pop_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        aboutMe = findViewById(R.id.about_me);
        send_btn = findViewById(R.id.send);
        cancel_btn = findViewById(R.id.cancel);

        aboutMe.setText(about_me);

        /*initializing loading dialog*/
        basePopupView = new XPopup.Builder(getContext())
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .autoDismiss(false)
                .asLoading();

        send_btn.setOnClickListener(view -> {
            if (aboutMe.getText().toString().length() < 8) {
                aboutMe.setError("Minimum length is 8");
            } else {
                updateAboutMe(aboutMe.getText().toString());
            }
        });

        cancel_btn.setOnClickListener(view -> {
            smartDismiss();
        });
    }

    private void updateAboutMe(String aboutMeText) {
        dismissWith(() -> {
            basePopupView.show();

            simpleResponseObservable = constants.rxJavaQueries.updateUserData(constants.accessToken,
                    BuildConfig.server_key, null, null, null, null,
                    null, null, null, null, null, null, aboutMeText,
                    null, null, null, null, null);

            simpleResponseObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<simpleResponse>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                            Log.d(TAG, "onSubscribe: ");
                        }

                        @Override
                        public void onNext(@io.reactivex.rxjava3.annotations.NonNull simpleResponse simpleResponse) {
                            if (simpleResponse.getApiStatus() == 200) {
                                Log.d(TAG, "onNext: " + simpleResponse.getMessage());

                                basePopupView.dismissWith(() -> new XPopup.Builder(getContext())
                                        .asConfirm(simpleResponse.getMessage(), "", null,
                                                "Okay", () -> {
                                                    /*dismiss*/
                                                    smartDismiss();
                                                }, null, true, 0).show());
                            } else {
                                apiErrors apiErrors = simpleResponse.getErrors();
                                Log.d(TAG, "ERROR: " + apiErrors.getErrorId());

                                Toast toast = Toast.makeText(getContext(), "Something went wrong ... ", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();

                                basePopupView.dismiss();
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            Log.d(TAG, "onError: " + e.getMessage());

                            Toast toast = Toast.makeText(getContext(), "Something went wrong ... ", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();

                            basePopupView.dismiss();
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: ");
                        }
                    });
        });
    }

    @Override
    protected int getMaxWidth() {
        return super.getMaxWidth();
    }

    @Override
    protected int getMaxHeight() {
        return super.getMaxHeight();
    }

    @Override
    protected int getPopupWidth() {
        return 0;
    }

    @Override
    protected int getPopupHeight() {
        return 0;
    }
}
