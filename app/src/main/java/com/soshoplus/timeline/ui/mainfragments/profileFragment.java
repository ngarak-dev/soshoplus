/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.mainfragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.soshoplus.timeline.BuildConfig;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.calls.simpleProfileCalls;
import com.soshoplus.timeline.databinding.FragmentProfileBinding;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.simpleResponse;
import com.soshoplus.timeline.ui.auth.signIn;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;
import com.soshoplus.timeline.utils.xpopup.changePasswordPopup;

import org.jetbrains.annotations.NotNull;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import de.adorsys.android.securestoragelibrary.SecureStorageException;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class profileFragment extends Fragment {
    
    public profileFragment () {
        // Required empty public constructor
    }

    private static String TAG = "profileFragment ";
    private FragmentProfileBinding profileBinding;
    private String userId, timezone, accessToken;
    /*..........*/
    private simpleProfileCalls calls;
    /*.........*/
    private queries rxJavaQueries;
    private Observable<simpleResponse> simpleResponse;
    private BasePopupView basePopupView;

    @Override
    public View onCreateView (@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    
    @Override
    public void onViewCreated (@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileBinding = FragmentProfileBinding.bind(view);
        profileBinding.getRoot();

        userId = SecurePreferences.getStringValue(requireContext(), "userId", "0");
        timezone = SecurePreferences.getStringValue(requireContext(), "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(requireContext(), "accessToken"
                , "0");

        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);

        new Handler().postDelayed(this::loadProfile, 500);

        /*initializing loading dialog*/
        basePopupView = new XPopup.Builder(requireContext())
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .autoDismiss(false)
                .asLoading("Please wait");

        /*logout*/
        profileBinding.logout.setOnClickListener(_view -> {
            new Handler().postDelayed(this::logOutUser, 300);
        });

        /*change password*/
        profileBinding.toChangePassword.setOnClickListener(__view -> {
            new Handler().postDelayed(this::changePassword, 300);
        });
    }

    private void loadProfile() {
        calls = new simpleProfileCalls(requireContext());
        calls.getProfile(profileBinding.profilePic, profileBinding.fullName, profileBinding.userEmail);
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
                    public void onNext(com.soshoplus.timeline.models.@NonNull simpleResponse simpleResponse) {
                        if (simpleResponse.getApiStatus() == 200) {

                            try {
                                SecurePreferences.clearAllValues(requireContext());
                                Log.d(TAG, "onNext: " + "USER LOGGED OUT");

                                basePopupView.delayDismissWith(300, new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(requireContext(), signIn.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        startActivity(intent);
                                        ((FragmentActivity) requireContext()).finish();
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
        new XPopup.Builder(requireContext()).asCustom(new changePasswordPopup(requireContext())).show();
    }
}
