/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.soshoplus.timeline.R;
import com.soshoplus.timeline.adapters.previewImagesAdapter;
import com.soshoplus.timeline.calls.timelineCalls;
import com.soshoplus.timeline.databinding.ActivityImagePostBinding;
import com.soshoplus.timeline.ui.auth.signIn;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

public class imagePost extends AppCompatActivity {

    private static String TAG = "Image Post : ";

    private static final int GALLERY_REQUEST_CODE = 7502;
    private static final int CAMERA_REQUEST_CODE = 7500;
    /*..........*/
    private previewImagesAdapter imagesAdapter;
    private ArrayList<MediaFile> photos = new ArrayList<>();
    private EasyImage easyImage;

    private ActivityImagePostBinding postBinding;
    private timelineCalls calls;
    private BasePopupView popupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postBinding = ActivityImagePostBinding.inflate(getLayoutInflater());
        View view = postBinding.getRoot();
        setContentView(view);

        popupView = new XPopup.Builder(imagePost.this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .autoDismiss(false)
                .asLoading("Creating a post... ");

        easyImage = new EasyImage.Builder(imagePost.this)
                .setChooserTitle("Pick Images")
                .setCopyImagesToPublicGalleryFolder(true)
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .setFolderName("soshoplus")
                .allowMultiple(true)
                .build();

        calls = new timelineCalls(imagePost.this);

        postBinding.closeBtn.setOnClickListener(view_ -> {
            onBackPressed();
        });

        postBinding.pickImage.setOnClickListener(view_ -> {
            easyImage.openGallery(imagePost.this);
        });

        postBinding.captureImage.setOnClickListener(view_ -> {
            /** Inaweza kukataa kwenye simu zingine*/
            String[] necessaryPermissions = new String[]{Manifest.permission.CAMERA};
            if (arePermissionsGranted(necessaryPermissions)) {
                new Handler().postDelayed(() -> {
                    easyImage.openCameraForImage(imagePost.this);
                }, 500);

            } else {
                requestPermissionsCompat(necessaryPermissions, CAMERA_REQUEST_CODE);
            }
        });

        postBinding.sendPostBtn.setOnClickListener(view_ -> {
            Log.d(TAG, "POSITION 0: " + photos.get(0).getFile());

            new Handler().postDelayed(() -> {

                new AsyncTask<String, Void, Boolean>() {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        postBinding.postProgress.setVisibility(View.VISIBLE);
                        popupView.show();
                    }

                    @Override
                    protected Boolean doInBackground(String... strings) {

                        try {
                            calls.createNewMediaPost(postBinding.postTxtContents.getText().toString(),
                                    photos.get(0).getFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d(TAG, "EXCEPTION: " + e.getMessage());
                        }

                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        postBinding.postProgress.setVisibility(View.GONE);
                        popupView.smartDismiss();

                        /*move task back*/
                        onBackPressed();
                    }
                }.execute();

            }, 500);
        });

        imagesAdapter = new previewImagesAdapter(R.layout.preview_image_layout, photos);
        postBinding.previewImageRV.setHasFixedSize(true);
        postBinding.previewImageRV.setAdapter(imagesAdapter);

        /*remove img click listener*/
        imagesAdapter.setOnItemChildClickListener((adapter, view_, position) -> {
            if (view_.getId() == R.id.remove_img) {

                if (imagesAdapter.getData().size() == 0) {
                    imagesAdapter.getData().removeAll(photos);
                }
                else {
                    imagesAdapter.getData().remove(position);
                }

                imagesAdapter.notifyDataSetChanged();
                Log.d(TAG, "onCreate: " + photos.size());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        easyImage.handleActivityResult(requestCode, resultCode, data, imagePost.this, new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(@NotNull MediaFile[] imageFiles, @NotNull MediaSource source) {
                for (MediaFile imageFile : imageFiles) {
                    Log.d(TAG, "Images : " + imageFile.getFile().toString());
                }
                onPhotosReturned(imageFiles);
            }

            @Override
            public void onImagePickerError(@NonNull Throwable error, @NonNull MediaSource source) {
                //Some error handling
                error.printStackTrace();
            }

            @Override
            public void onCanceled(@NonNull MediaSource source) {
                //Not necessary to remove any files manually anymore
            }
        });
    }

    /*permissions*/
    private boolean arePermissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(imagePost.this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    private void requestPermissionsCompat(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(imagePost.this, permissions, requestCode);
    }

    private void onPhotosReturned(MediaFile[] imageFiles) {
        photos.addAll(Arrays.asList(imageFiles));
        imagesAdapter.notifyDataSetChanged();
        postBinding.previewImageRV.scrollToPosition(photos.size() - 1);
    }
}