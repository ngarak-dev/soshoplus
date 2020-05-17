package com.soshoplus.timeline.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.soshoplus.timeline.ui.auth.signin;
import com.soshoplus.timeline.ui.auth.signup;
import com.soshoplus.timeline.databinding.ActivityGetstartedBinding;

public class getstarted extends AppCompatActivity {

    private ActivityGetstartedBinding getstartedBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getstartedBinding  = ActivityGetstartedBinding.inflate(getLayoutInflater());
        View view = getstartedBinding.getRoot();
        setContentView(view);

        getstartedBinding.btnToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getstarted.this, signin.class));
            }
        });

        getstartedBinding.btnToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getstarted.this, signup.class));
            }
        });

    }
}
