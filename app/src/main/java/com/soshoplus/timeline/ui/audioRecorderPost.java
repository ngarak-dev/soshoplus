/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.ui;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.loader.content.CursorLoader;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.ngarak.recorder.OnRecordListener;
import com.soshoplus.timeline.BuildConfig;
import com.soshoplus.timeline.databinding.ActivityAudioRecorderPostBinding;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import dev.utils.LogPrintUtils;
import dev.utils.app.permission.PermissionUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class audioRecorderPost extends AppCompatActivity {

    private static String TAG = "Audio Record";
    private String accessToken;
    private static int type;
    private ActivityAudioRecorderPostBinding binding;
    private static String recordPath;
    private BasePopupView popupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAudioRecorderPostBinding.inflate(getLayoutInflater());
        View view  = binding.getRoot();
        setContentView(view);

        accessToken = SecurePreferences.getStringValue(audioRecorderPost.this, "accessToken"
                , "0");

        permissionRequest();

        if (PermissionUtils.isGranted(Manifest.permission.RECORD_AUDIO)) {
            binding.recordButton.setEnabled(true);
            binding.pickAudio.setEnabled(true);
        }
        else {
            permissionRequest();
        }

        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("type");

        if (type == 1) {
            binding.recordView.setVisibility(View.GONE);
            binding.recordButton.setVisibility(View.GONE);
            binding.pickAudio.setVisibility(View.VISIBLE);
        }
        else {
            binding.recordView.setVisibility(View.VISIBLE);
            binding.recordButton.setVisibility(View.VISIBLE);
            binding.pickAudio.setVisibility(View.GONE);
        }

        popupView = new XPopup.Builder(audioRecorderPost.this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .autoDismiss(false)
                .asLoading("Creating a post... ");

        binding.recordButton.setRecordView(binding.recordView);

        binding.recordView.setOnRecordListener(new OnRecordListener() {
            @Override
            public void onStart() {
                Log.d(TAG, "onStart: ");
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: ");
            }

            @Override
            public void onFinish(long recordTime, @NotNull String audioPath) {
                Log.d(TAG, "onFinish: " + recordTime);
                Log.d(TAG, "onFinish: " + audioPath);

                recordPath = audioPath;
                Log.d(TAG, "RECORD PATH : " + recordPath);

                binding.recordView.setVisibility(View.GONE);
                binding.recordButton.setVisibility(View.GONE);

                binding.recording.setVisibility(View.VISIBLE);
                binding.removeAudio.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLessThanSecond() {
                Log.d(TAG, "onLessThanSecond: ");
            }

            @Override
            public void onTickListener(long recordTime) {
                LogPrintUtils.dTag(TAG, "onTickListener: " + recordTime);
            }
        });

        binding.recordButton.setListenForRecord(true);

        binding.closeBtn.setOnClickListener(view_ -> {
            onBackPressed();
        });

        binding.removeAudio.setOnClickListener(view_ -> {
            recordPath = null;

            if (type == 1) {
                binding.pickAudio.setVisibility(View.VISIBLE);
            }
            else {
                binding.recordView.setVisibility(View.VISIBLE);
                binding.recordButton.setVisibility(View.VISIBLE);
            }

            binding.recording.setVisibility(View.GONE);
            binding.removeAudio.setVisibility(View.GONE);

            Log.d(TAG, "onCreate: " + recordPath);
        });

        binding.sendPostBtn.setOnClickListener(view_ -> {
            if (recordPath == null) {
                Toast toast = Toast.makeText(audioRecorderPost.this, "Record audio first to post ... ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                toast.show();
            }
            else {
                sendPost();
            }
        });

        binding.pickAudio.setOnClickListener(view_ -> {
            Intent intent_upload = new Intent();
            intent_upload.setType("audio/*");
            intent_upload.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent_upload,1);
        });
    }

    private void sendPost() {
        binding.postProgress.setVisibility(View.VISIBLE);
        popupView.show();

        new Handler().postDelayed(() -> {
            AsyncTask.execute(() -> {
                createNewMediaPost(binding.postTxtContents.getText().toString(), new File(recordPath));
            });
        }, 500);
    }

    private void createNewMediaPost(String postText, File file) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("server_key", BuildConfig.server_key)
                .addFormDataPart("postText", postText)
                .addFormDataPart("postFile",file.getName(),
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
                    Toast toast = Toast.makeText(audioRecorderPost.this, "Failed to create post ... ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                    toast.show();

                    binding.postProgress.setVisibility(View.GONE);
                    popupView.smartDismiss();

                    /*move task back*/
                    onBackPressed();
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if (response.isSuccessful()) {
                    HandlerCompat.createAsync(Looper.getMainLooper()).post(() -> {
                        Toast toast = Toast.makeText(audioRecorderPost.this, "Post created successful ... ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                        toast.show();

                        binding.postProgress.setVisibility(View.GONE);
                        popupView.smartDismiss();

                        /*move task back*/
                        onBackPressed();
                    });

                }
                else {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                if (data != null) {
                    Uri uri = data.getData();
                    recordPath = getAudioPath(uri);

                    if (recordPath == null) {
                        Toast toast = Toast.makeText(audioRecorderPost.this, "Failed to pick audio ... ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                        toast.show();
                    }
                    else {
                        Log.d(TAG, "AUDIO PATH: " + recordPath);
                        hidePickBtn();
                    }
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getAudioPath(Uri uri) {
        String[] mediaData = {MediaStore.Audio.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, mediaData, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);
    }

    private void hidePickBtn() {
        binding.pickAudio.setVisibility(View.GONE);
        binding.removeAudio.setVisibility(View.VISIBLE);
        binding.recording.setVisibility(View.VISIBLE);

//        binding.recording.setCompoundDrawablesRelative(getResources().getDrawable(R.drawable.ic_audio_post), null, null, null);
        binding.recording.setText("Audio file added");
    }

    private void permissionRequest() {
        PermissionUtils.permission(Manifest.permission.RECORD_AUDIO).callBack(new PermissionUtils.PermissionCallBack() {
            @Override
            public void onGranted() {
                requestStorage();
            }

            @Override
            public void onDenied(List<String> grantedList, List<String> deniedList, List<String> notFoundList) {
                permissionRequest();
            }
        }).request(audioRecorderPost.this);
    }

    private void requestStorage() {
        PermissionUtils.permission(Manifest.permission.READ_EXTERNAL_STORAGE).callBack(new PermissionUtils.PermissionCallBack() {
            @Override
            public void onGranted() {
                binding.recordButton.setEnabled(true);
                binding.pickAudio.setEnabled(true);
                LogPrintUtils.dTag(TAG, "GRANTED PERMISSION");
            }

            @Override
            public void onDenied(List<String> grantedList, List<String> deniedList, List<String> notFoundList) {
                PermissionUtils.getDeniedPermissionStatus(audioRecorderPost.this, true, Manifest.permission_group.STORAGE);
                PermissionUtils.getDeniedPermissionStatus(audioRecorderPost.this, true, Manifest.permission_group.MICROPHONE);
            }
        }).request(audioRecorderPost.this);
    }
}