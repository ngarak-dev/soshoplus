/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils.xpopup;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.lxj.xpopup.core.BottomPopupView;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.calls.previewProfileCalls;

public class previewProfilePopup extends BottomPopupView {
    
    private Context m_context;
    private previewProfileCalls calls;
    private static String user_id;
    
    public previewProfilePopup (@NonNull Context context, String userId) {
        super(context);
        m_context = context;
        user_id = userId;
    }
    
    @Override
    protected int getImplLayoutId () {
        return R.layout.preview_profile_layout;
    }
    
    @Override
    protected void onCreate () {
        super.onCreate();
        
        ProgressBar progressBar_cover = findViewById(R.id.progressBar_cover);
        ImageView cover_photo = findViewById(R.id.cover_photo);
        ImageView profile_pic = findViewById(R.id.profile_pic);
        TextView username = findViewById(R.id.username);
        ImageView verified_badge = findViewById(R.id.verified_badge);
        ImageView level_badge = findViewById(R.id.level_badge);

        TextView no_followers = findViewById(R.id.number_of_followers);
        TextView no_following = findViewById(R.id.number_of_following);
        
        MaterialButton follow = findViewById(R.id.follow_btn);
        
        TextView about_me = findViewById(R.id.about_me);
        ProgressBar progressBar_follow = findViewById(R.id.progressBar_follow);
        
        calls = new previewProfileCalls(m_context);
        calls.previewProfile(cover_photo, progressBar_cover, profile_pic,
                username, verified_badge, level_badge, no_followers
                , no_following, follow, about_me, progressBar_follow, user_id);
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
