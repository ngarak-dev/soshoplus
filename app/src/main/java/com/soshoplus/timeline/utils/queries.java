/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils;

import com.soshoplus.timeline.models.accessToken;
import com.soshoplus.timeline.models.friends.friends;
import com.soshoplus.timeline.models.friends.suggested.suggestedList;
import com.soshoplus.timeline.models.groups.group;
import com.soshoplus.timeline.models.groups.groupList;
import com.soshoplus.timeline.models.userprofile.userInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface queries {
    /*Sign In*/
    @FormUrlEncoded
    @POST("auth")
    Call<accessToken> signIn (@Field("server_key") String server_key, @Field("username") String username, @Field("password") String password);
    /*Create Account*/
    @FormUrlEncoded
    @POST("create-account")
    Call<accessToken> createAccount (@Field("server_key") String server_key, @Field("email") String email, @Field("username") String username, @Field("password") String password, @Field("confirm_password") String confirm_password, @Field("phone_num") String phone_num);
    /*Get User Data*/
    @FormUrlEncoded
    @POST("get-user-data")
    Call<userInfo> getUserData (@Query("access_token") String access_token,
                                @Field("server_key") String server_key,
                                @Field("fetch") String fetch, @Field("user_id") String user_id);
    
    /*GROUPS*/
    /*Recommended Groups*/
    @FormUrlEncoded
    @POST("fetch-recommended")
    Call<groupList> getRecommended(@Query("access_token") String access_token,
                                   @Field("server_key") String server_key,
                                   @Field("type") String type, @Field("limit") String limit);
    
    /*Joined Groups*/
    @FormUrlEncoded
    @POST("get-my-groups")
    Call<groupList> getJoinedGroups(@Query("access_token") String access_token,
                                @Field("server_key") String server_key,
                                @Field("type") String fetch, @Field("user_id") String user_id);
    
    /*My Groups*/
    /*TODO Implement this on new Version*/
//    @FormUrlEncoded
//    @POST("get-my-groups")
//    Call<Object> getMyGroups(@Query("access_token") String access_token,
//                             @Field("server_key") String server_key,
//                             @Field("type") String fetch);
    /*Group Info*/
    @FormUrlEncoded
    @POST("get-group-data")
    Call<group> getGroupInfo(@Query("access_token") String access_token,
                             @Field("server_key") String server_key,
                             @Field("group_id") String group_id);
    
    /*Get Followers (Friends)*/
    @FormUrlEncoded
    @POST("get-friends")
    Call<friends> getFriendsFollowers(@Query("access_token") String access_token,
                                      @Field("server_key") String server_key,
                                      @Field("type") String fetch,
                                      @Field("user_id") String user_id,
                                      @Field("limit") String limit);
    
    /*Get Followers (Friends)*/
    @FormUrlEncoded
    @POST("get-friends")
    Call<friends> getFriendsFollowing(@Query("access_token") String access_token,
                                      @Field("server_key") String server_key,
                                      @Field("type") String fetch,
                                      @Field("user_id") String user_id,
                                      @Field("limit") String limit);
    
    /*Get Recommended users*/
    @FormUrlEncoded
    @POST("fetch-recommended")
    Call<suggestedList> getPeopleYouMayKnow (@Query("access_token") String access_token,
                                             @Field("server_key") String server_key,
                                             @Field("type") String fetch,
                                             @Field("limit") String limit);
}
