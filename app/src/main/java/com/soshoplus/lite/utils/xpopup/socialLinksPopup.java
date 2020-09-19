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
public class socialLinksPopup extends CenterPopupView {

    private static String TAG = "Social Links popup";
    private final String facebook;
    private final String twitter;
    private final String linkedin;
    private final String instagram;
    private final String youtube;

    private MaterialButton send_btn, cancel_btn;
    private TextInputEditText facebook_, twitter_, linkedin_, instagram_, youtube_;

    private BasePopupView basePopupView;
    private Observable<simpleResponse> simpleResponseObservable;

    public socialLinksPopup(@NonNull Context context, String facebook, String twitter,
                            String linkedin, String instagram, String youtube) {
        super(context);
        this.facebook = facebook;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.instagram = instagram;
        this.youtube = youtube;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.social_links_pop_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        facebook_ = findViewById(R.id.facebook);
        twitter_ = findViewById(R.id.twitter);
        linkedin_ = findViewById(R.id.linkedin);
        instagram_ = findViewById(R.id.instagram);
        youtube_ = findViewById(R.id.youtube);

        facebook_.setText(facebook);
        twitter_.setText(twitter);
        linkedin_.setText(linkedin);
        instagram_.setText(instagram);
        youtube_.setText(youtube);

        send_btn  = findViewById(R.id.send);
        cancel_btn = findViewById(R.id.cancel);

        /*initializing loading dialog*/
        basePopupView = new XPopup.Builder(getContext())
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .autoDismiss(false)
                .asLoading();

        send_btn.setOnClickListener(view -> {
            updateSocialLinks();
        });

        cancel_btn.setOnClickListener(view -> {
           smartDismiss();
        });
    }

    private void updateSocialLinks () {
        dismissWith(() -> {
            basePopupView.show();

            simpleResponseObservable = constants.rxJavaQueries.updateUserData(constants.accessToken,
                    BuildConfig.server_key, null, null, null, null,
                    null, null, null, null, null, null, null,
                    facebook_.getText().toString(), twitter_.getText().toString(), linkedin_.getText().toString(),
                    instagram_.getText().toString(), youtube_.getText().toString());

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
                            }

                            else {
                                apiErrors apiErrors = simpleResponse.getErrors();
                                Log.d(TAG, "ERROR: " + apiErrors.getErrorId());

                                Toast toast = Toast.makeText(getContext(), "Something went wrong ... ", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                                toast.show();

                                basePopupView.dismiss();
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            Log.d(TAG, "onError: " + e.getMessage());

                            Toast toast = Toast.makeText(getContext(), "Something went wrong ... ", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
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
