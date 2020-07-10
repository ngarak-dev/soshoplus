/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils.xpopup;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.calls.timelineCalls;

public class sharePopup extends BottomPopupView {
    
    private Context m_context;
    private timelineCalls calls;
    public sharePopup (@NonNull Context context) {
        super(context);
        m_context = context;
    
        calls = new timelineCalls(m_context);
    }
    
    @Override
    protected int getImplLayoutId () {
        return R.layout.share_post_layout;
    }
    
    @Override
    protected void onCreate () {
        super.onCreate();
        
        findViewById(R.id.share_on_social_media).setOnClickListener(view -> calls.shareOnOtherApps());
    
        findViewById(R.id.share_on_timeline).setOnClickListener(view -> {
            /*dismiss dialog while performing sharing*/
            dismissWith(() -> {
                calls.shareOnTimeline();
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
