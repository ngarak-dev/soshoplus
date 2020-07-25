/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.feedHolders;

import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.gson.Gson;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.postsfeed.post;
import com.soshoplus.timeline.models.userprofile.userData;
import com.soshoplus.timeline.utils.glide.glideImageLoader;

import org.jetbrains.annotations.NotNull;

public class AdPost extends BaseItemProvider<post> {
    
    private static String TAG = "AD POST : ";
    
    SimpleDraweeView profile_pic, media;
    TextView full_name, location, description, headline;
    ProgressBar progressBar;
    
    @Override
    public int getItemViewType () {
        return post.ADS;
    }
    
    @Override
    public int getLayoutId () {
        return R.layout.timeline_feed_ad;
    }
    
    @Override
    public void convert (@NotNull BaseViewHolder baseViewHolder, post post) {
        profile_pic = baseViewHolder.findView(R.id.ad_profile_pic);
        full_name = baseViewHolder.findView(R.id.ad_full_name);
        location = baseViewHolder.findView(R.id.ad_location);
        description = baseViewHolder.findView(R.id.ad_description);
        headline = baseViewHolder.findView(R.id.ad_headline);
        media = baseViewHolder.findView(R.id.ad_media);
        progressBar = baseViewHolder.findView(R.id.progressBar);
    
        /*Converting Object to json data*/
        Gson gson = new Gson();
        String toJson = gson.toJson(post.getUserData());
        /*getting data from json string using pojo class*/
        userData user_data = gson.fromJson(toJson, userData.class);
    
        profile_pic.setImageURI(user_data.getAvatar());
    
        full_name.setText(user_data.getName());
        location.setText(post.getLocation());
        description.setText(Html.fromHtml(post.getDescription()));
        headline.setText(post.getHeadline());
        
        media.setImageURI(post.getAdMedia());
        
        /*click listeners*/
        addChildClickViewIds(R.id.ad_media);
    }
}
