/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui;

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

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.soshoplus.lite.BuildConfig;
import com.soshoplus.lite.databinding.ActivityAudioRecorderPostBinding;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import dev.utils.LogPrintUtils;
import dev.utils.app.permission.PermissionUtils;
import me.ngarak.recorder.OnRecordListener;
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
    private static int type;
    private static String recordPath;
    private ActivityAudioRecorderPostBinding binding;
    private BasePopupView popupView;

    private SimpleExoPlayer exoPlayer;
    private boolean playWhenReady = false;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private static String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAudioRecorderPostBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        permissionRequest();

        accessToken = SecurePreferences.getStringValue(audioRecorderPost.this, "accessToken", "0");

        if (PermissionUtils.isGranted(Manifest.permission.RECORD_AUDIO)) {
            binding.recordButton.setEnabled(true);
            binding.pickAudio.setEnabled(true);
        } else {
            permissionRequest();
        }

        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("type");

        if (type == 1) {
            binding.recordView.setVisibility(View.GONE);
            binding.recordButton.setVisibility(View.GONE);
            binding.pickAudio.setVisibility(View.VISIBLE);
        } else {
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

                binding.mediaPlayer.setVisibility(View.VISIBLE);
                binding.removeAudio.setVisibility(View.VISIBLE);

                initializePlayer();
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
            releasePlayer();
            recordPath = null;

            if (type == 1) {
                binding.pickAudio.setVisibility(View.VISIBLE);
            } else {
                binding.recordView.setVisibility(View.VISIBLE);
                binding.recordButton.setVisibility(View.VISIBLE);
            }

            binding.mediaPlayer.setVisibility(View.GONE);
            binding.removeAudio.setVisibility(View.GONE);

            Log.d(TAG, "onCreate: " + recordPath);
        });

        binding.sendPostBtn.setOnClickListener(view_ -> {
            if (recordPath == null) {
                Toast toast = Toast.makeText(audioRecorderPost.this, "Record audio first to post ... ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
            } else {
                sendPost();
            }
        });

        binding.pickAudio.setOnClickListener(view_ -> {
            Intent intent_upload = new Intent();
            intent_upload.setType("audio/*");
            intent_upload.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent_upload, 1);
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
                    Toast toast = Toast.makeText(audioRecorderPost.this, "Failed to create post ... ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();

                    binding.postProgress.setVisibility(View.GONE);
                    popupView.smartDismiss();

                    /*move task back*/
                    onBackPressed();
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    HandlerCompat.createAsync(Looper.getMainLooper()).post(() -> {
                        Toast toast = Toast.makeText(audioRecorderPost.this, "Post created successful ... ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();

                        binding.postProgress.setVisibility(View.GONE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    recordPath = getAudioPath(uri);

                    if (recordPath == null) {
                        Toast toast = Toast.makeText(audioRecorderPost.this, "Failed to pick audio ... ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
                    } else {
                        Log.d(TAG, "AUDIO PATH: " + recordPath);
                        hidePickBtn();

                        initializePlayer();
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
        binding.mediaPlayer.setVisibility(View.VISIBLE);
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

    private void initializePlayer() {
        exoPlayer = new SimpleExoPlayer.Builder(audioRecorderPost.this).build();
        binding.mediaPlayer.setPlayer(exoPlayer);

        Uri uri = Uri.parse(recordPath);
        MediaSource mediaSource = buildMediaSource(uri);

        exoPlayer.setPlayWhenReady(playWhenReady);
        exoPlayer.seekTo(currentWindow, playbackPosition);
        exoPlayer.prepare(mediaSource, false, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(audioRecorderPost.this, "soshoplay");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (Util.SDK_INT >= 24) {
//            initializePlayer();
//        }
//    }
//

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if ((Util.SDK_INT < 24 || exoPlayer == null)) {
//            initializePlayer();
//        }
//    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            playWhenReady = exoPlayer.getPlayWhenReady();
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}