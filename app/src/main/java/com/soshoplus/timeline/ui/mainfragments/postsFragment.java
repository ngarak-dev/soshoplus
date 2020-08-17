/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui.mainfragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.lxj.xpopup.XPopup;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.calls.timelineCalls;
import com.soshoplus.timeline.databinding.FragmentPostsBinding;
import com.soshoplus.timeline.ui.imagePost;
import com.soshoplus.timeline.utils.xpopup.newNormalPostPopup;

public class postsFragment extends Fragment {

    private static final String PHOTOS_KEY = "photos_list";
    private static final int CHOOSER_PERMISSIONS_REQUEST_CODE = 7459;
    private static final int CAMERA_REQUEST_CODE = 7500;
    private static final int CAMERA_VIDEO_REQUEST_CODE = 7501;
    private static final int GALLERY_REQUEST_CODE = 7502;
    private static final int DOCUMENTS_REQUEST_CODE = 7503;
    /*........*/
    private FragmentPostsBinding postsBinding;
    private timelineCalls calls;

    public postsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        postsBinding = FragmentPostsBinding.bind(view);
        postsBinding.getRoot();

        /*timeline feed*/
        new Handler().postDelayed(this::getTimelineFeed, 1000);

        postsBinding.postTypes.addPost.setOnClickListener(view_ -> {
            postsBinding.postTypes.collapsingLayout.toggle();
        });

        /*normal post*/
        postsBinding.postTypes.newTxtPost.setOnClickListener(view_ -> {
            new XPopup.Builder(requireContext())
                    .asCustom(new newNormalPostPopup(requireContext(), postsBinding.postProgress)).show();
        });

        postsBinding.postTypes.colorPost.setOnClickListener(view_ -> {
            new XPopup.Builder(requireContext())
                    .asCustom(new newNormalPostPopup(requireContext(), postsBinding.postProgress)).show();
        });

        /*post with images*/
        postsBinding.postTypes.imagePost.setOnClickListener(view_ -> {

            /** Inaweza kukataa kwenye simu zingine*/
            String[] necessaryPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (arePermissionsGranted(necessaryPermissions)) {
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(requireContext(), imagePost.class));
                }, 500);

            } else {
                requestPermissionsCompat(necessaryPermissions, GALLERY_REQUEST_CODE);
            }

        });
    }

    private void getTimelineFeed () {
    calls = new timelineCalls(requireContext());
    calls.getTimelineFeed(postsBinding.timelinePostsList,
            postsBinding.progressBarTimeline,
            postsBinding.timelineErrorLayout, postsBinding.tryAgain);
    }

    /*permissions*/
    private boolean arePermissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    private void requestPermissionsCompat(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(requireActivity(), permissions, requestCode);
    }

    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == CHOOSER_PERMISSIONS_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            /*TODO Fungua Chooser*/
//        } else if (requestCode == CAMERA_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            /*TODO Fungua Kamera*/
//        } else if (requestCode == CAMERA_VIDEO_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            /*TODO Fungua Kamera ya Video*/
//        } else if (requestCode == GALLERY_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            /*TODO Fungua Gallery*/
//        } else if (requestCode == DOCUMENTS_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            /*TODO Fungua documents chooser*/
//        }
//    }
}