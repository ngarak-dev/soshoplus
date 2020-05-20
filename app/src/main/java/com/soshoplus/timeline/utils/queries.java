/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils;

import com.soshoplus.timeline.models.accessToken;
import com.soshoplus.timeline.models.userprofile.userInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface queries {
    @FormUrlEncoded
    @POST("auth")
    Call<accessToken> signIn (@Field("server_key") String server_key, @Field("username") String username, @Field("password") String password);
    
    @FormUrlEncoded
    @POST("create-account")
    Call<accessToken> createAccount (@Field("server_key") String server_key, @Field("email") String email, @Field("username") String username, @Field("password") String password, @Field("confirm_password") String confirm_password, @Field("phone_num") String phone_num);
    
    @FormUrlEncoded
    @POST("get-user-data")
    Call<userInfo> getUserData (@Query("access_token") String access_token,
                                @Field("server_key") String server_key,
                                @Field("fetch") String fetch, @Field("user_id") String user_id);
}
