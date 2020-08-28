/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.utils.xpopup;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.soshoplus.lite.R;
import com.soshoplus.lite.calls.hashTagsPostsCalls;

public class shareHashPopup extends BottomPopupView {

    private hashTagsPostsCalls calls;
    private static String postId, postUrl, fullName;

    public shareHashPopup(@NonNull Context context, String post_Id, String url, String name) {
        super(context);
        calls = new hashTagsPostsCalls(context);
        postId = post_Id;
        postUrl = url;
        fullName = name;
    }
    
    @Override
    protected int getImplLayoutId () {
        return R.layout.share_post_layout;
    }
    
    @Override
    protected void onCreate () {
        super.onCreate();
        
        findViewById(R.id.share_on_social_media).setOnClickListener(view ->
                calls.shareOnOtherApps(postUrl, fullName));
    
        findViewById(R.id.share_on_timeline).setOnClickListener(view -> {
            /*dismiss dialog while performing sharing*/
            dismissWith(() -> {
                calls.shareOnTimeline(postId);
            });
        });
        
        findViewById(R.id.share_on_group).setOnClickListener(view -> {
    
        });
        
        findViewById(R.id.share_on_page).setOnClickListener(view -> {
    
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
//        return super.getPopupWidth();
        return 0;
    }
    
    @Override
    protected int getPopupHeight () {
//        return super.getPopupHeight();
        return 0;
    }
}
