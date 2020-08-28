/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.adapters;

import android.os.Looper;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.os.HandlerCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.friends.suggested.suggestedInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.transform.RoundedCornersTransformation;

public class suggestedFriendsAdapter extends BaseQuickAdapter<suggestedInfo, BaseViewHolder> {
    
    private static String TAG = "Suggested Friends Adapter";

    public suggestedFriendsAdapter (int layoutResId, @Nullable List<suggestedInfo> data) {
        super(layoutResId, data);
    }
    
    @Override
    protected void convert (@NotNull BaseViewHolder baseViewHolder, suggestedInfo suggestedInfo) {
        if (suggestedInfo == null) {
            return;
        }

        HandlerCompat.createAsync(Looper.getMainLooper()).post(() -> {
            
            baseViewHolder.setText(R.id.full_name, suggestedInfo.getName());

            TextView aboutMe = baseViewHolder.findView(R.id.about_me);

            ImageLoader imageLoader = Coil.imageLoader(getContext());
            ImageView profile_pic = baseViewHolder.findView(R.id.profile_pic);
            
            if (suggestedInfo.getAbout() == null) {
                aboutMe.setText("Hello there, I am using soshoplus");
            } else {
                aboutMe.setText(Html.fromHtml(suggestedInfo.getAbout()));
            }

            ImageRequest imageRequest = new ImageRequest.Builder(getContext())
                    .data(suggestedInfo.getAvatar())
                    .placeholder(R.color.light_grey)
                    .crossfade(true)
                    .transformations(new RoundedCornersTransformation(10))
                    .target(profile_pic)
                    .build();
            imageLoader.enqueue(imageRequest);
            
        });
    }
}
