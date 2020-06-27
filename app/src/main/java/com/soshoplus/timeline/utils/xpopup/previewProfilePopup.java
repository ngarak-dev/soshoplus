/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils.xpopup;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.databinding.PreviewProfileLayoutBinding;
import com.soshoplus.timeline.utils.retrofitCalls;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class previewProfilePopup extends BottomPopupView {
    
    private Context m_context;
    retrofitCalls calls;
    
    public previewProfilePopup (@NonNull Context context) {
        super(context);
        m_context = context;
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
        ShapeableImageView profile_pic = findViewById(R.id.profile_pic);
        TextView username = findViewById(R.id.username);
        ImageView verified_badge = findViewById(R.id.verified_badge);
        ImageView level_badge = findViewById(R.id.level_badge);
        
        TextView no_posts = findViewById(R.id.no_of_posts);
        TextView no_followers = findViewById(R.id.number_of_followers);
        TextView no_following = findViewById(R.id.number_of_following);
        
        Chip follow = findViewById(R.id.follow_btn);
        Chip message = findViewById(R.id.message_btn);
        Chip more = findViewById(R.id.more_btn);
        
        TextView about_me = findViewById(R.id.about_me);
        TextView gender = findViewById(R.id.gender);
        TextView birthday = findViewById(R.id.birthday);
        TextView working = findViewById(R.id.working_at);
        TextView school = findViewById(R.id.school);
        TextView living = findViewById(R.id.living);
        TextView located = findViewById(R.id.located);
    
        calls = new retrofitCalls(m_context);
        calls.previewProfile(cover_photo,
                progressBar_cover,
                profile_pic, username,
                verified_badge, level_badge, no_posts, no_followers,
                no_following, follow, message, more, about_me, gender,
                birthday, working, school, living, located);
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
