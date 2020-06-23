/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils.xpopup;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.utils.retrofitCalls;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class sharePopup extends BottomPopupView {
    
    Context m_context;
    public sharePopup (@NonNull Context context) {
        super(context);
        m_context = context;
    }
    
    @Override
    protected int getImplLayoutId () {
        return R.layout.share_post_layout;
    }
    
    @Override
    protected void onCreate () {
        super.onCreate();
        
        findViewById(R.id.share_on_social_media).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick (View view) {
                ExecutorService service = Executors.newSingleThreadExecutor();
                service.execute(new Runnable() {
                    @Override
                    public void run () {
                        retrofitCalls retrofitCalls = new retrofitCalls(m_context);
                        retrofitCalls.shareOnOtherApps();
                    }
                });
                service.shutdown();
            }
        });
    
        findViewById(R.id.share_on_timeline).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick (View view) {
            
            }
        });
        
        findViewById(R.id.share_on_group).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick (View view) {
        
            }
        });
        
        findViewById(R.id.share_on_page).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick (View view) {
        
            }
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
