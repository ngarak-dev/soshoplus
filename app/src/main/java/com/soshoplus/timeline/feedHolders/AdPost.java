/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.feedHolders;

import android.text.Html;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.models.postsfeed.post;
import com.soshoplus.timeline.models.userprofile.userData;

import org.jetbrains.annotations.NotNull;

public class AdPost extends BaseItemProvider<post> {
    
    private static String TAG = "AD POST : ";
    
    SimpleDraweeView profile_pic, media;
    
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
        media = baseViewHolder.findView(R.id.ad_media);
    
        /*Converting Object to json data*/
        Gson gson = new Gson();
        String toJson = gson.toJson(post.getUserData());
        /*getting data from json string using pojo class*/
        userData user_data = gson.fromJson(toJson, userData.class);
    
        baseViewHolder.setText(R.id.ad_full_name, user_data.getName());
        baseViewHolder.setText(R.id.ad_location, post.getLocation());
        baseViewHolder.setText(R.id.ad_description, Html.fromHtml(post.getDescription()));
        baseViewHolder.setText(R.id.ad_headline, post.getHeadline());

        profile_pic.setImageURI(user_data.getAvatar());
        media.setImageURI(post.getAdMedia());
    }
}