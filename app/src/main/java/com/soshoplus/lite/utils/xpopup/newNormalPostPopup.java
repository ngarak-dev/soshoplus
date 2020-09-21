/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.utils.xpopup;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.hendraanggrian.appcompat.widget.SocialEditText;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.soshoplus.lite.R;
import com.soshoplus.lite.calls.timelineCalls;

import net.cachapa.expandablelayout.ExpandableLayout;

public class newNormalPostPopup extends FullScreenPopupView {

    private static String post_color;
    private timelineCalls calls;
    private ProgressBar progressBar;

    public newNormalPostPopup(@NonNull Context context, ProgressBar postProgress) {
        super(context);
        this.progressBar = postProgress;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.new_normal_post_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        calls = new timelineCalls(getContext());

        MaterialButton show_colors = findViewById(R.id.show_colors);
        MaterialButton close = findViewById(R.id.close_btn);
        ExpandableLayout layout = findViewById(R.id.collapsingLayout);
        MaterialButton send = findViewById(R.id.send_post_btn);
        SocialEditText post_contents = findViewById(R.id.post_txt_contents);

        LinearLayout color_0 = findViewById(R.id.color_0);
        LinearLayout color_1 = findViewById(R.id.color_1);
        LinearLayout color_2 = findViewById(R.id.color_2);
        LinearLayout color_3 = findViewById(R.id.color_3);
        LinearLayout color_4 = findViewById(R.id.color_4);
        LinearLayout color_5 = findViewById(R.id.color_5);
        LinearLayout color_6 = findViewById(R.id.color_6);

        close.setOnClickListener(view -> {
            smartDismiss();
        });

        show_colors.setOnClickListener(view -> {
            layout.toggle();
        });

        color_0.setOnClickListener(view -> {
            post_color = "0";
            post_contents.setBackgroundResource(R.drawable.rectangle);
            post_contents.setTextColor(getResources().getColor(R.color.black));
            post_contents.setTextSize(14);
            post_contents.setGravity(Gravity.START);
        });

        color_1.setOnClickListener(view -> {
            post_color = "1";
            post_contents.setBackgroundResource(R.drawable.post_gradient_one);
            post_contents.setTextColor(getResources().getColor(R.color.white_smoke));
            post_contents.setTextSize(20);
            post_contents.setGravity(Gravity.CENTER);
        });

        color_2.setOnClickListener(view -> {
            post_color = "2";
            post_contents.setBackgroundResource(R.drawable.post_gradient_two);
            post_contents.setTextColor(getResources().getColor(R.color.white_smoke));
            post_contents.setTextSize(20);
            post_contents.setGravity(Gravity.CENTER);
        });

        color_3.setOnClickListener(view -> {
            post_color = "3";
            post_contents.setBackgroundResource(R.drawable.post_gradient_three);
            post_contents.setTextColor(getResources().getColor(R.color.white_smoke));
            post_contents.setTextSize(20);
            post_contents.setGravity(Gravity.CENTER);
        });

        color_4.setOnClickListener(view -> {
            post_color = "4";
            post_contents.setBackgroundResource(R.drawable.post_gradient_four);
            post_contents.setTextColor(getResources().getColor(R.color.white_smoke));
            post_contents.setTextSize(20);
            post_contents.setGravity(Gravity.CENTER);
        });

        color_5.setOnClickListener(view -> {
            post_color = "5";
            post_contents.setBackgroundResource(R.drawable.post_gradient_five);
            post_contents.setTextColor(getResources().getColor(R.color.white_smoke));
            post_contents.setTextSize(20);
            post_contents.setGravity(Gravity.CENTER);
        });

        color_6.setOnClickListener(view -> {
            post_color = "6";
            post_contents.setBackgroundResource(R.drawable.post_gradient_six);
            post_contents.setTextColor(getResources().getColor(R.color.white_smoke));
            post_contents.setTextSize(20);
            post_contents.setGravity(Gravity.CENTER);
        });

        send.setOnClickListener(view -> {
            if (post_contents.getText().toString().isEmpty()) {
                post_contents.setError("Post text cant be empty");
                Toast.makeText(getContext(), "Post text cant be empty", Toast.LENGTH_SHORT).show();
            } else if (post_contents.getText().toString().length() < 2) {
                post_contents.setError("Post is too short");
                Toast.makeText(getContext(), "Post is too short", Toast.LENGTH_SHORT).show();
            } else {
                smartDismiss();

                new Handler().postDelayed(() -> {
                    progressBar.setVisibility(View.VISIBLE);
                    calls.createNewPost(post_contents.getText().toString(), progressBar, post_color);
                }, 500);
            }
        });
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
