/*
 * Ngara K
 * Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 */

package com.soshoplus.timeline.utils;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface queries {
    @FormUrlEncoded
    @POST("auth")
    Call<com.soshoplus.timeline.models.accessToken> signIn (@Field("server_key") String server_key, @Field("username") String username, @Field("password") String password);
    
    @FormUrlEncoded
    @POST("create-account")
    Call<com.soshoplus.timeline.models.accessToken> createAccount (@Field("server_key") String server_key, @Field("email") String email, @Field("username") String username, @Field("password") String password, @Field("confirm_password") String confirm_password, @Field("phone_num") String phone_num);
}
