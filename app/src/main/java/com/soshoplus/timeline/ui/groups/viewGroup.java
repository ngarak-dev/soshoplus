/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.groups;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.calls.groupCalls;
import com.soshoplus.timeline.databinding.ActivityViewGroupBinding;

public class viewGroup extends AppCompatActivity {
    
    private ActivityViewGroupBinding viewGroupBinding;
    private groupCalls calls;
    private static String group_id, no_members, group_info, group_url;
    private boolean isJoined;
    private BasePopupView popupView;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewGroupBinding = ActivityViewGroupBinding.inflate(getLayoutInflater());
        View view = viewGroupBinding.getRoot();
        setContentView(view);

        /*setting up actionbar*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        group_id = intent.getStringExtra("group_id");
        no_members = intent.getStringExtra("no_members");
        group_info = intent.getStringExtra("group_info");
        group_url = intent.getStringExtra("group_url");
        isJoined = intent.getBooleanExtra("is_joined", false);

        /*get group Info*/
        HandlerCompat.createAsync(Looper.getMainLooper()).postDelayed(this::getGroupInfo, 300);
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

        if (isJoined) {
            joinedMenu(menu);
        }
        else {
            recommendedMenu(menu);
        }

        return true;
    }

    private void joinedMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.joined_group_menu, menu);
    }

    private void recommendedMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.recom_group_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.group_info:
                showGroupInfo();
                return true;

            case R.id.share_group:
                shareGroup();

            default:
                return super.onOptionsItemSelected(item);
        }
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