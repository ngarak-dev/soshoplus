/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.utils.xpopup;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.ImageViewerPopupView;
import com.soshoplus.lite.R;
import com.soshoplus.lite.ui.user_profile.userProfile;

public class adFullImageViewPopup extends ImageViewerPopupView {

    public adFullImageViewPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.ad_post_image_full;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        TextView full_name = findViewById(R.id.ad_full_name);
        TextView location = findViewById(R.id.ad_location);
        TextView description = findViewById(R.id.ad_description);
        TextView headline = findViewById(R.id.ad_headline);

        /*getting ad info*/
        userProfile.getADInfo(full_name, location, description,
                headline);
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
