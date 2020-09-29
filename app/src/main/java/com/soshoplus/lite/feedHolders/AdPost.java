/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.feedHolders;

import android.text.Html;
import android.text.util.Linkify;
import android.widget.ImageView;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.hendraanggrian.appcompat.widget.SocialTextView;
import com.soshoplus.lite.R;
import com.soshoplus.lite.models.postsfeed.post;
import com.soshoplus.lite.models.userprofile.userData;

import org.jetbrains.annotations.NotNull;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.transform.CircleCropTransformation;
import io.noties.markwon.Markwon;
import io.noties.markwon.linkify.LinkifyPlugin;

public class AdPost extends BaseItemProvider<post> {

    private static String TAG = "AD POST : ";

    ImageView profile_pic, media;
    SocialTextView ad_desc;

    @Override
    public int getItemViewType() {
        return post.ADS;
    }

    @Override
    public int getLayoutId() {
        return R.layout.timeline_feed_ad;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, post post) {

        ImageLoader imageLoader = Coil.imageLoader(getContext());

        profile_pic = baseViewHolder.findView(R.id.ad_profile_pic);
        media = baseViewHolder.findView(R.id.ad_media);

        ad_desc = baseViewHolder.findView(R.id.ad_description);

        /*Converting Object to json data*/
        Gson gson = new Gson();
        String toJson = gson.toJson(post.getUserData());
        /*getting data from json string using pojo class*/
        userData user_data = gson.fromJson(toJson, userData.class);

        Markwon markwon = Markwon.builder(getContext())
                .usePlugin(LinkifyPlugin.create(
                        Linkify.EMAIL_ADDRESSES | Linkify.PHONE_NUMBERS | Linkify.WEB_URLS
                ))
                .build();

        baseViewHolder.setText(R.id.ad_full_name, user_data.getName());
        baseViewHolder.setText(R.id.ad_location, post.getLocation());
        markwon.setMarkdown(ad_desc, post.getDescription());
        baseViewHolder.setText(R.id.ad_headline, post.getHeadline());

        ImageRequest imageRequest = new ImageRequest.Builder(getContext())
                .data(user_data.getAvatar())
                .placeholder(R.color.light_grey)
                .crossfade(true)
                .transformations(new CircleCropTransformation())
                .target(profile_pic)
                .build();
        imageLoader.enqueue(imageRequest);

        imageRequest = new ImageRequest.Builder(getContext())
                .data(post.getAdMedia())
                .placeholder(R.color.light_grey)
                .crossfade(true)
                .target(media)
                .build();
        imageLoader.enqueue(imageRequest);
    }
}
