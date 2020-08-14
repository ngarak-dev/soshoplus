/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.groups;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.onurkagan.ksnack_lib.KSnack.KSnack;
import com.soshoplus.timeline.BuildConfig;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.calls.groupCalls;
import com.soshoplus.timeline.databinding.ActivityViewGroupBinding;
import com.soshoplus.timeline.models.apiErrors;
import com.soshoplus.timeline.models.groups.join.join_unjoin;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;
import com.soshoplus.timeline.utils.xpopup.friendsToAddPopup;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class viewGroup extends AppCompatActivity {

    private static String TAG = "View group Calls";
    private ActivityViewGroupBinding viewGroupBinding;
    private groupCalls calls;
    private static String group_id, no_members, group_info, group_url;
    private boolean isJoined;
    private BasePopupView popupView;

    /*bottom popup*/
    private String[] joinedOptions = {"Group info", "Share", "Add friend", "Leave group"};
    private int [] joinedIcons = {R.drawable.ic_info, R.drawable.ic_share, R.drawable.ic_add, R.drawable.ic_minus};
    private String[] recOptions  ={"Group info", "Share"};
    private int [] recIcons = {R.drawable.ic_info, R.drawable.ic_share};

    /*.......*/
    private queries rxJavaQueries;
    private String accessToken, userId, timezone;
    private Observable<join_unjoin> unJoinObservable;
    private KSnack snack;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewGroupBinding = ActivityViewGroupBinding.inflate(getLayoutInflater());
        View view = viewGroupBinding.getRoot();
        setContentView(view);

        /*setting up actionbar*/
        setSupportActionBar(viewGroupBinding.transToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userId = SecurePreferences.getStringValue(this, "userId", "0");
        timezone = SecurePreferences.getStringValue(this, "timezone", "UTC");
        accessToken = SecurePreferences.getStringValue(this, "accessToken"
                , "0");

        /*initializing query*/
        rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);

        /*initializing snack*/
        snack = new KSnack(this);

        Intent intent = getIntent();
        group_id = intent.getStringExtra("group_id");
        no_members = intent.getStringExtra("no_members");
        group_info = intent.getStringExtra("group_info");
        group_url = intent.getStringExtra("group_url");
        isJoined = intent.getBooleanExtra("is_joined", false);

        /*get group Info*/
        HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed(this::getGroupInfo, 500);
    }

    /*get group info*/
    private void getGroupInfo () {
        calls = new groupCalls(this);
        calls.getGroupInfo(viewGroupBinding.groupProfilePic, viewGroupBinding.groupCover,
                viewGroupBinding.noMembers,viewGroupBinding.groupPrivacy,
                viewGroupBinding.groupCategory, group_id, no_members, viewGroupBinding.joinBtn);

        /*get group posts*/
        HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed(this::getGroupPosts, 500);
    }

    private void getGroupPosts() {
        calls = new groupCalls(this);
        calls.getGroupPosts(viewGroupBinding.groupPostList, group_id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.group_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.group_options:
                if (isJoined) {
                    new Handler().postDelayed(this::joinedMenu, 300);
                }
                else {
                    new Handler().postDelayed(this::recommendedMenu, 300);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void recommendedMenu() {
        popupView = new XPopup.Builder(this).asCenterList(null, recOptions, (position, text) -> {
            switch (position) {
                case 0:
                    popupView.delayDismissWith(1000, this::showGroupInfo);
                    break;
                case 1:
                    popupView.delayDismissWith(1000, this::shareGroup);
                    break;
            }
        });
        popupView.show();
    }

    private void joinedMenu() {
        popupView = new XPopup.Builder(this).asCenterList(null, joinedOptions, (position, text) -> {
            switch (position) {
                case 0:
                    popupView.delayDismissWith(1000, this::showGroupInfo);
                    break;
                case 1:
                    popupView.delayDismissWith(1000, this::shareGroup);
                    break;
                case 2:
                    popupView.delayDismissWith(1000, this::addFriendToGroup);
                    break;
                case 3:
                    popupView.delayDismissWith(1000, this::leaveGroup);
                    break;
                default:
            }
        });
        popupView.show();
    }

    private void leaveGroup() {
        snack.setMessage("Leaving group ....");
        snack.show();

        unJoinObservable =
                rxJavaQueries.joinGroup(accessToken, BuildConfig.server_key, group_id);

        unJoinObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<join_unjoin>() {
                    @Override
                    public void onSubscribe (@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext (@io.reactivex.rxjava3.annotations.NonNull join_unjoin join_unjoin) {
                        if (join_unjoin.getApiStatus() == 200) {

                            if (join_unjoin.getJoinStatus().equals("left")) {
                                snack.setMessage("You have left the group");
                                snack.setDuration(3000);

                                /*go back*/
                                onBackPressed();
                            }
                        }
                        else {
                            apiErrors errors = join_unjoin.getErrors();
                            Log.d(TAG, "onNext: " + errors.getErrorId());

                            snack.setMessage("Oops !\nSomething went " +
                                    "wrong");
                            snack.setAction("DISMISS", view -> {
                                snack.dismiss();
                            });
                        }
                        snack.show();
                        snack.setDuration(3000);
                    }

                    @Override
                    public void onError (@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());

                        snack.setMessage("Oops !\nSomething went " +
                                "wrong");
                        snack.show();
                        snack.setDuration(3000);
                    }

                    @Override
                    public void onComplete () {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void addFriendToGroup() {
        new XPopup.Builder(viewGroup.this).asCustom(new friendsToAddPopup(viewGroup.this, group_id)).show();
    }

    private void shareGroup() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Soshoplus Group");
        intent.putExtra(Intent.EXTRA_TEXT, group_url);
        startActivity(Intent.createChooser(intent, "Share with "));
    }

    private void showGroupInfo() {
        popupView = new XPopup.Builder(this).asConfirm("About this group", group_info, null, "Dismiss", () -> {
                popupView.delayDismiss(300);
        }, null, true, 0).show();
    }
}