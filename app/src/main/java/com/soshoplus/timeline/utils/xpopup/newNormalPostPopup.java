/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils.xpopup;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.soshoplus.timeline.R;

import net.cachapa.expandablelayout.ExpandableLayout;

public class newNormalPostPopup extends FullScreenPopupView {

    public newNormalPostPopup(@NonNull Context context) {
        super(context);
    }
    
    @Override
    protected int getImplLayoutId () {
        return R.layout.new_normal_post_layout;
    }
    
    @Override
    protected void onCreate () {
        super.onCreate();

        MaterialButton show_colors = findViewById(R.id.show_colors);
        MaterialButton close = findViewById(R.id.close_btn);
        ExpandableLayout layout = findViewById(R.id.collapsingLayout);
        MaterialButton send = findViewById(R.id.send_post_btn);
        TextInputEditText post_contents = findViewById(R.id.post_txt_contents);

        close.setOnClickListener(view -> {
            smartDismiss();
        });

        show_colors.setOnClickListener(view -> {
            layout.toggle();
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
        return 0;
    }
    
    @Override
    protected int getPopupHeight () {
        return 0;
    }
}
