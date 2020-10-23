/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.R;
import com.soshoplus.lite.adapters.previewImagesAdapter;
import com.soshoplus.lite.databinding.ActivityImagePostBinding;
import com.soshoplus.lite.utils.RedBookPresenter;
import com.ypx.imagepicker.ImagePicker;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.bean.MimeType;
import com.ypx.imagepicker.bean.PickerError;
import com.ypx.imagepicker.data.OnImagePickCompleteListener;
import com.ypx.imagepicker.data.OnImagePickCompleteListener2;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class imageVideoPost extends AppCompatActivity {

    private static String TAG = "Image Post : ";
    private static int type;
    private static String accessToken;
    private previewImagesAdapter imagesAdapter;
    private ArrayList<ImageItem> photos = new ArrayList<>();
    private ActivityImagePostBinding postBinding;
    private BasePopupView popupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postBinding = ActivityImagePostBinding.inflate(getLayoutInflater());
        View view = postBinding.getRoot();
        setContentView(view);

        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("type");

        accessToken = SecurePreferences.getStringValue(imageVideoPost.this, "accessToken", "0");

        popupView = new XPopup.Builder(imageVideoPost.this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .autoDismiss(false)
                .asLoading("Creating a post... ");

        postBinding.closeBtn.setOnClickListener(view_ -> {
            onBackPressed();
        });

        postBinding.pickImage.setOnClickListener(view_ -> {

            if (type == 1) {
                ImagePicker.withCrop(new RedBookPresenter())
                        .setMaxCount(1)
                        .showCamera(true)
                        .setColumnCount(4)
                        .mimeTypes(Set.of(MimeType.JPEG, MimeType.BMP, MimeType.PNG))
                        .assignGapState(true)
                        .pick(this, (OnImagePickCompleteListener) items -> {
                            for (ImageItem img : items) {
                                Log.d(TAG, "onImagePickComplete: " + img.getCropUrl());
                            }
                            onPhotosReturned(items);
                        });
            } else {
                ImagePicker.withCrop(new RedBookPresenter())
                        .setMaxCount(1)
                        .setVideoSinglePick(true)
                        .showCamera(true)
                        .mimeTypes(MimeType.ofVideo())
                        .pick(this, new OnImagePickCompleteListener2() {
                            @Override
                            public void onPickFailed(PickerError error) {
                                Log.d(TAG, "onPickFailed: " + error.getMessage());
                            }

                            @Override
                            public void onImagePickComplete(ArrayList<ImageItem> items) {
                                for (ImageItem videoItem : items) {
                                    Log.d(TAG, "onImagePickComplete: " + videoItem.getVideoImageUri());
                                }
                                onPhotosReturned(items);
                            }
                        });
            }
        });

        postBinding.sendPostBtn.setOnClickListener(view_ -> {
            if (type == 1) {
                if (photos.size() == 0) {
                    Toast toast = Toast.makeText(imageVideoPost.this, "Select an image to post ... ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                } else {
                    sendPost();
                }
            } else {
                if (photos.size() == 0) {
                    Toast toast = Toast.makeText(imageVideoPost.this, "Select a video to post ... ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                } else {
                    sendPost();
                }
            }
        });

        imagesAdapter = new previewImagesAdapter(R.layout.preview_image_layout, photos);
        postBinding.previewImageRV.setHasFixedSize(true);
        postBinding.previewImageRV.setAdapter(imagesAdapter);

        /*remove img click listener*/
        imagesAdapter.setOnItemChildClickListener((adapter, view_, position) -> {
            if (view_.getId() == R.id.remove_img) {

                if (imagesAdapter.getData().size() == 0) {
                    imagesAdapter.getData().removeAll(photos);
                } else {
                    imagesAdapter.getData().remove(position);
                }

                imagesAdapter.notifyDataSetChanged();
                Log.d(TAG, "onCreate: " + photos.size());
            }
        });
    }

    private void sendPost() {
        postBinding.postProgress.setVisibility(View.VISIBLE);
        popupView.show();

        new Handler().postDelayed(() -> {
            AsyncTask.execute(() -> {
                createNewMediaPost(postBinding.postTxtContents.getText().toString(), new File(photos.get(0).getCropUrl()));
            });

        }, 500);
    }

    private void createNewMediaPost(String postText, File file) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("server_key", BuildConfig.server_key)
                .addFormDataPart("postText", postText)
                .addFormDataPart("postFile", file.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File(file.getAbsolutePath())))
                .build();

        Request request = new Request.Builder()
                .url("https://soshoplus.com/api/new_post?access_token=" + accessToken)
                .method("POST", body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onFailure: ");

                HandlerCompat.createAsync(Looper.getMainLooper()).post(() -> {
                    Toast toast = Toast.makeText(imageVideoPost.this, "Failed to create post ... ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();

                    postBinding.postProgress.setVisibility(View.GONE);
                    popupView.smartDismiss();

                    /*move task back*/
                    onBackPressed();
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if (response.isSuccessful()) {
                    HandlerCompat.createAsync(Looper.getMainLooper()).post(() -> {
                        Toast toast = Toast.makeText(imageVideoPost.this, "Post created successful ... ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();

                        postBinding.postProgress.setVisibility(View.GONE);
                        popupView.smartDismiss();

                        /*move task back*/
                        onBackPressed();
                    });
                } else {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }
        });
    }

    private void onPhotosReturned(ArrayList<ImageItem> items) {
        photos.addAll(items);
        imagesAdapter.notifyDataSetChanged();
        postBinding.previewImageRV.scrollToPosition(photos.size() - 1);
    }
}