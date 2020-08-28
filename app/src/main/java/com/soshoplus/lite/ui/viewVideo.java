/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;

import androidx.appcompat.app.AppCompatActivity;

import com.soshoplus.lite.databinding.ActivityViewVideoBinding;

import org.jetbrains.annotations.NotNull;

public class viewVideo extends AppCompatActivity {
    
    private ActivityViewVideoBinding videoBinding;
    private static String url;
    private static String TAG = "Video View Activity";
    
    private static final String PLAYBACK_TIME = "play_time";
    private int current_position = 0;
    
    private MediaController mediaController;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoBinding = ActivityViewVideoBinding.inflate(getLayoutInflater());
        View view = videoBinding.getRoot();
        setContentView(view);
    
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        url = bundle.getString("link");
        Log.d("TAG", "onCreate: " + url);
    
        /*checking current position of video*/
        if (savedInstanceState != null) {
            current_position = savedInstanceState.getInt(PLAYBACK_TIME);
        }
        
        /*showing progress*/
        videoBinding.progressCircular.setVisibility(View.VISIBLE);
        /*initializing player*/
        initializePlayer();
    
        /*Adding media controller*/
        mediaController = new MediaController(viewVideo.this);
        mediaController.setAnchorView(videoBinding.videoView);
        videoBinding.videoView.setMediaController(mediaController);
        
        /*Listeners*/
        videoBinding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared (MediaPlayer mp) {
                /*removing progress*/
                videoBinding.progressCircular.setVisibility(View.INVISIBLE);
                
                if (current_position > 0) {
                    videoBinding.videoView.seekTo(current_position);
                } else {
                    // Skipping to 1 shows the first frame of the video.
                    videoBinding.videoView.seekTo(1);
                }
                
                /*starting video*/
                videoBinding.videoView.start();
            }
        });
        
        videoBinding.videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError (MediaPlayer mp, int what, int extra) {
                Log.d(TAG, "onError: " + what + extra);
                return false;
            }
        });
        
        videoBinding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion (MediaPlayer mp) {
                mp.release();
            }
        });
    }
    
    private void initializePlayer () {
        /*url for video*/
        videoBinding.videoView.setVideoURI(Uri.parse(url));
        videoBinding.videoView.requestFocus();
    }
    
    @Override
    protected void onStart () {
        super.onStart();
        initializePlayer();
    }
    
    @Override
    protected void onStop () {
        super.onStop();
        videoBinding.videoView.stopPlayback();
    }
    
    @Override
    protected void onPause () {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            videoBinding.videoView.pause();
        }
    }
    
    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, videoBinding.videoView.getCurrentPosition());
    }
}