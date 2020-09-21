/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.utils.xpopup;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.soshoplus.lite.R;
import com.soshoplus.lite.ui.user_profile.userProfile;

public class fullImageViewPopup extends ImageViewerPopupView {

    public fullImageViewPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.post_image_full;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        TextView full_name = findViewById(R.id.full_name);
        TextView time_ago = findViewById(R.id.time_ago);

        TextView no_likes = findViewById(R.id.no_likes);
        TextView no_comments = findViewById(R.id.no_comments);

        MaterialButton like = findViewById(R.id.like_btn);
        MaterialButton comment = findViewById(R.id.comment_btn);

        /*getting post image info*/
        userProfile.getInfo(full_name, time_ago, no_likes, no_comments,
                like, comment);
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
