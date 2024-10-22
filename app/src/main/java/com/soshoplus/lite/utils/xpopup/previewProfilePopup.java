/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.utils.xpopup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.lxj.xpopup.core.CenterPopupView;
import com.soshoplus.lite.R;
import com.soshoplus.lite.calls.previewProfileCalls;

@SuppressLint("ViewConstructor")
public class previewProfilePopup extends CenterPopupView {

    private static String user_id;
    private previewProfileCalls calls;

    public previewProfilePopup(@NonNull Context context, String userId) {
        super(context);
        user_id = userId;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.preview_profile_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        ImageView cover_photo = findViewById(R.id.cover_photo);
        ImageView profile_pic = findViewById(R.id.profile_pic);
        TextView username = findViewById(R.id.username);
        ImageView verified_badge = findViewById(R.id.verified_badge);
        ImageView level_badge = findViewById(R.id.level_badge);

        TextView no_followers = findViewById(R.id.number_of_followers);
        TextView no_following = findViewById(R.id.number_of_following);

        MaterialButton follow = findViewById(R.id.follow_btn);
        TextView follows_me = findViewById(R.id.following_me);

        MaterialButton back = findViewById(R.id.back_arrow);

        TextView about_me = findViewById(R.id.about_me);
        ProgressBar progressBar_follow = findViewById(R.id.progressBar_follow);

        new Handler().postDelayed(() -> {

            calls = new previewProfileCalls(getContext());
            calls.previewProfile(cover_photo, profile_pic,
                    username, verified_badge, level_badge, no_followers
                    , no_following, follow, about_me, progressBar_follow, user_id
                    , follows_me);

        }, 500);

        back.setOnClickListener(view -> {
            smartDismiss();
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
