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
import com.soshoplus.timeline.utils.retrofitCalls;

public class viewGroup extends AppCompatActivity {
    
    private ActivityViewGroupBinding viewGroupBinding;
    private ShapeableImageView profile_pic;
    private ImageView cover_pic;
    private Chip members, privacy, category;
    private retrofitCalls calls;
    
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
}