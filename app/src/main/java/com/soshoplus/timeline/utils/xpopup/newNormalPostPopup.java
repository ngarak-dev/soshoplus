/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils.xpopup;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.calls.timelineCalls;

import net.cachapa.expandablelayout.ExpandableLayout;

public class newNormalPostPopup extends FullScreenPopupView {

    private timelineCalls calls;
    private ProgressBar progressBar;

    public newNormalPostPopup(@NonNull Context context, ProgressBar postProgress) {
        super(context);
        this.progressBar = postProgress;
    }
    
    @Override
    protected int getImplLayoutId () {
        return R.layout.new_normal_post_layout;
    }
    
    @Override
    protected void onCreate () {
        super.onCreate();

        calls = new timelineCalls(getContext());

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

        send.setOnClickListener(view -> {
            if (post_contents.getText().toString().isEmpty()) {
                post_contents.setError("Post text cant be empty");
                Toast.makeText(getContext(), "Post text cant be empty", Toast.LENGTH_SHORT).show();
            }

            else if (post_contents.getText().toString().length() < 2 ) {
                post_contents.setError("Post is too short");
                Toast.makeText(getContext(), "Post is too short", Toast.LENGTH_SHORT).show();
            }

            else {
                smartDismiss();

                new Handler().postDelayed(() -> {
                    progressBar.setVisibility(View.VISIBLE);
                    calls.createNewPost(post_contents.getText().toString(), progressBar);
                }, 500);
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
        return 0;
    }
    
    @Override
    protected int getPopupHeight () {
        return 0;
    }
}
