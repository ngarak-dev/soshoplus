package com.soshoplus.timeline.ui.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.soshoplus.timeline.databinding.ActivitySigninBinding;
import com.soshoplus.timeline.models.token;
import com.soshoplus.timeline.models.tokenErrors;
import com.soshoplus.timeline.utils.queries;
import com.soshoplus.timeline.utils.retrofitInstance;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signin extends AppCompatActivity {

    private ActivitySigninBinding signinBinding;
    private Call<token> tokenCall;
    private queries loginQuery;

    private static String server_key = "a41ab77c99ab5c9f46b66a894d97cce9";
    private static String TAG = "SignIn Activity ";

    private String username, password;
    boolean validate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signinBinding  = ActivitySigninBinding.inflate(getLayoutInflater());
        View view = signinBinding.getRoot();
        setContentView(view);

        username = Objects.requireNonNull(signinBinding.username.getText()).toString();
        password = Objects.requireNonNull(signinBinding.password.getText()).toString();

        signinBinding.btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    Toast.makeText(signin.this, "Check your username or password", Toast.LENGTH_SHORT).show();
                    return;
                }
                callSignIn();
            }

            private boolean validate() {

                if (username.isEmpty()) {
                    signinBinding.usernameInLayout.setError("Username is empty");
                    validate = false;
                }

                if (password.isEmpty()) {
                    signinBinding.passwordInLayout.setError("Password is empty");
                    validate = false;
                }

                return validate;
            }
        });
    }

    private void callSignIn() {
        //Initializing Retrofit Instance for Login
        loginQuery = retrofitInstance.getRetrofitInst().create(queries.class);

        tokenCall = loginQuery.getToken(server_key, username, password);

        tokenCall.enqueue(new Callback<token>() {
            @Override
            public void onResponse(@NonNull Call<token> call, @NonNull Response<token>  response) {
                if (response.body() != null) {

                    if (response.body().getApiStatus() == 200) {
                        Log.d(TAG, "onResponse: " + response.body().getApiStatus());
                        Log.d(TAG, "onResponse: " + response.body().getAccessToken());
                        Log.d(TAG, "onResponse: " + response.body().getTimezone());
                        Log.d(TAG, "onResponse: " + response.body().getUserId());
                    }

                    else {
                        tokenErrors tokenErrors = response.body().getErrors();
                        Log.d(TAG, "onResponse: " + tokenErrors.getErrorId());
                        Log.d(TAG, "onResponse: " + tokenErrors.getErrorText());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<token> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
