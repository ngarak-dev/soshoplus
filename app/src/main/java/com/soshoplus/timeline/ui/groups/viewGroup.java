/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.groups;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.soshoplus.timeline.databinding.ActivityViewGroupBinding;

public class viewGroup extends AppCompatActivity {
    
    private ActivityViewGroupBinding viewGroupBinding;
    private ShapeableImageView profile_pic;
    private ImageView cover_pic;
    private Chip members, privacy, category;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewGroupBinding = ActivityViewGroupBinding.inflate(getLayoutInflater());
        View view = viewGroupBinding.getRoot();
        setContentView(view);
        
        /*declaration*/
//        profile_pic = viewGroupBinding.groupProfilePic;
//        cover_pic = viewGroupBinding.groupCover;
//        members = viewGroupBinding.noMembers;
//        privacy = viewGroupBinding.groupPrivacy;
//        category = viewGroupBinding.groupCategory;
//
//        /*get group Info*/
//        getGroupInfo();
    }
    
//    /*get group info*/
//    private void getGroupInfo () {
//        calls = new retrofitCalls(this);
//        calls.getGroupInfo(profile_pic, cover_pic, members, privacy, category);
//    }
    
    //    /*get group info*/
//    public void getGroupInfo (ShapeableImageView profile_pic, ImageView cover_pic, Chip members, Chip privacy, Chip category) {
//        groupCall = queries.getGroupInfo(accessToken, serverKey, group_id);
//        groupCall.enqueue(new Callback<group>() {
//            @Override
//            public void onResponse (@NotNull Call<group> call, @NotNull like_dislike<group> response) {
//                if (response.body() != null) {
//                    if (response.body().getApiStatus() == 200) {
//
//                        if (groupInfo == null) {
//                            groupInfo = response.body().getGroupInfo();
//                        }
//
//                        Log.d(TAG, "onResponse: " + groupInfo.getMembers() + groupInfo.getType() +
//                                groupInfo.getName() + groupInfo.getJoinPrivacy() + groupInfo.getPrivacy());
//
//                        /*JoinPrivacy
//                            1: Join moja kwa moja
//                            2: Hapa ni request
//                        * Privacy
//                            1: Public
//                            2: Private
//                        * */
//
//                        /*load group info*/
//                        members.setText(groupInfo.getMembers() + " Members");
//                        /*setting group privacy*/
//                        if (groupInfo.getPrivacy().equals("1")) {
//                            privacy.setText("Public");
//                        } else {
//                            privacy.setText("Private");
//                        }
//                        /*setting category*/
//                        category.setText(groupInfo.getCategory());
//                        /*group cover*/
//                        Picasso.get().load(groupInfo.getCover()).fit().centerCrop().placeholder(R.drawable.ic_image_placeholder).into(cover_pic, new com.squareup.picasso.Callback() {
//                            @Override
//                            public void onSuccess () {
//                                Log.d(TAG, "onSuccess: " + "Image loaded");
//                            }
//
//                            @Override
//                            public void onError (Exception e) {
//                                Log.d(TAG, "onError: " + e.getMessage());
//                                cover_pic.setImageResource(R.drawable.ic_image_placeholder);
//                                /*TODO reload icon*/
//                            }
//                        });
//                        /*group profile pic*/
//                        profile_pic.setShapeAppearanceModel(profile_pic
//                                .getShapeAppearanceModel()
//                                .toBuilder()
//                                .setAllCorners(CornerFamily.ROUNDED, 20)
//                                .build());
//                        Picasso.get().load(groupInfo.getAvatar()).placeholder(R.drawable.ic_image_placeholder).into(profile_pic, new com.squareup.picasso.Callback() {
//                            @Override
//                            public void onSuccess () {
//                                Log.d(TAG, "onSuccess: " + "Image loaded");
//                            }
//
//                            @Override
//                            public void onError (Exception e) {
//                                Log.d(TAG, "onError: " + e.getMessage());
//                                profile_pic.setImageResource(R.drawable.ic_image_placeholder);
//                                /*TODO reload icon*/
//                            }
//                        });
//
//                    }
//                    else {
//                        /*TODO Error from API itself*/
//                        apiErrors apiErrors = response.body().getErrors();
//                        Log.d(TAG, "onResponse: " + apiErrors.getErrorId());
//                        Log.d(TAG, "onResponse: " + apiErrors.getErrorText());
//                    }
//                }
//                else {
//                    Log.i(TAG, "onResponse: " + "is null");
//                    /*TODO response is null*/
//                }
//            }
//
//            @Override
//            public void onFailure (@NotNull Call<group> call, @NotNull Throwable t) {
//                Log.i(TAG, "onFailure: " + t.getMessage());
//                /*TODO failed to get group info*/
//            }
//        });
//    }
//
}