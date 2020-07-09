/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils.xpopup;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.ImageViewerPopupView;
import com.soshoplus.timeline.R;

public class fullImageViewPopup extends ImageViewerPopupView {
    
    public fullImageViewPopup (@NonNull Context context) {
        super(context);
    }
    
    @Override
    protected int getImplLayoutId () {
        return R.layout.post_image_full;
    }
    
    @Override
    protected void onCreate () {
        super.onCreate();
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
