/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.utils;

import com.soshoplus.lite.models.accessToken;
import com.soshoplus.lite.models.block_unblock;
import com.soshoplus.lite.models.follow_unfollow;
import com.soshoplus.lite.models.friends.friends;
import com.soshoplus.lite.models.friends.suggested.suggestedList;
import com.soshoplus.lite.models.groups.addingUser;
import com.soshoplus.lite.models.groups.group;
import com.soshoplus.lite.models.groups.groupList;
import com.soshoplus.lite.models.groups.join.join_unjoin;
import com.soshoplus.lite.models.postAction;
import com.soshoplus.lite.models.postsfeed.commentsList;
import com.soshoplus.lite.models.postsfeed.postList;
import com.soshoplus.lite.models.postsfeed.reactions.like_dislike;
import com.soshoplus.lite.models.postsfeed.sharepost.shareResponse;
import com.soshoplus.lite.models.simpleResponse;
import com.soshoplus.lite.models.userprofile.userInfo;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface queries {

    /*SignIn*/
    @FormUrlEncoded
    @POST("auth")
    Observable<accessToken> signIn(@Field("server_key") String server_key, @Field(
            "username") String username, @Field("password") String password);

    /*Creating Account*/
    @FormUrlEncoded
    @POST("create-account")
    Observable<accessToken> createAccount(@Field("server_key") String server_key,
                                          @Field("email") String email, @Field("username") String username, @Field("password") String password, @Field("confirm_password") String confirm_password, @Field("phone_num") String phone_num);

    /*Get User shareData*/
    @FormUrlEncoded
    @POST("get-user-data")
    Observable<userInfo> getUserData(@Query("access_token") String access_token,
                                     @Field("server_key") String server_key,
                                     @Field("fetch") String fetch,
                                     @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("get-user-data-username")
    Observable<userInfo> getUserDataByUsername(@Query("access_token") String access_token,
                                               @Field("server_key") String server_key,
                                               @Field("fetch") String fetch,
                                               @Field("username") String username);

    /*Timeline Posts*/
    @FormUrlEncoded
    @POST("posts")
    Observable<postList> getTimelinePosts(@Query("access_token") String access_token,
                                          @Field("server_key") String server_key,
                                          @Field("type") String type,
                                          @Field("hash") String hashtag,
                                          @Field("limit") String limit,
                                          @Field("after_post_id") String after_post_id);

    /*GROUPS*/
    /*Recommended Groups*/
    @FormUrlEncoded
    @POST("fetch-recommended")
    Observable<groupList> getRecommended(@Query("access_token") String access_token,
                                         @Field("server_key") String server_key,
                                         @Field("type") String type, @Field("limit") String limit);

    /*Joined Groups*/
    @FormUrlEncoded
    @POST("get-my-groups")
    Observable<groupList> getJoinedGroups(@Query("access_token") String access_token,
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
    Observable<group> getGroupInfo(@Query("access_token") String access_token,
                                   @Field("server_key") String server_key,
                                   @Field("group_id") String group_id);

    /*Get Followers (Friends)*/
    @FormUrlEncoded
    @POST("get-friends")
    Observable<friends> getFriendsFollowers(@Query("access_token") String access_token,
                                            @Field("server_key") String server_key,
                                            @Field("type") String fetch,
                                            @Field("user_id") String user_id,
                                            @Field("limit") String limit);

    /*Get Followers (Friends)*/
    @FormUrlEncoded
    @POST("get-friends")
    Observable<friends> getFriendsFollowing(@Query("access_token") String access_token,
                                            @Field("server_key") String server_key,
                                            @Field("type") String fetch,
                                            @Field("user_id") String user_id,
                                            @Field("limit") String limit);

    /*Get Recommended users*/
    @FormUrlEncoded
    @POST("fetch-recommended")
    Observable<suggestedList> getPeopleYouMayKnow(@Query("access_token") String access_token,
                                                  @Field("server_key") String server_key,
                                                  @Field("type") String fetch,
                                                  @Field("limit") String limit);

    /*Like a post*/
    @FormUrlEncoded
    @POST("post-actions")
    Observable<like_dislike> like_dislikePost(@Query("access_token") String access_token,
                                              @Field("server_key") String server_key,
                                              @Field("post_id") String post_id,
                                              @Field("action") String action);

    /*Share Post on Timeline*/
    @FormUrlEncoded
    @POST("posts")
    Observable<shareResponse> sharePostInTimeline(@Query("access_token") String access_token,
                                                  @Field("server_key") String server_key,
                                                  @Field("type") String type,
                                                  @Field("id") String id,
                                                  @Field("user_id") String user_id,
                                                  @Field("text") String text);

    /*Follow user*/
    @FormUrlEncoded
    @POST("follow-user")
    Observable<follow_unfollow> followUser(@Query("access_token") String access_token,
                                           @Field("server_key") String server_key,
                                           @Field("user_id") String user_id);

    /*Joining a group*/
    @FormUrlEncoded
    @POST("join-group")
    Observable<join_unjoin> joinGroup(@Query("access_token") String access_token,
                                      @Field("server_key") String server_key,
                                      @Field("group_id") String group_id);

    /*block user*/
    @FormUrlEncoded
    @POST("block-user")
    Observable<block_unblock> blockUser(@Query("access_token") String access_token,
                                        @Field("server_key") String server_key,
                                        @Field("user_id") String user_id,
                                        @Field("block_action") String block_action);

    /*post filter
     * fetching posts with images only
     * then get only images*/
    @FormUrlEncoded
    @POST("get-user-albums")
    Observable<postList> getUserImages(@Query("access_token") String access_token,
                                       @Field("server_key") String server_key,
                                       @Field("user_id") String user_id,
                                       @Field("type") String type);

    /*POST OPTIONS*/
    /*report post*/
    /*save post*/
    @FormUrlEncoded
    @POST("post-actions")
    Observable<postAction> postAction(@Query("access_token") String access_token,
                                      @Field("server_key") String server_key,
                                      @Field("post_id") String post_id,
                                      @Field("action") String action);

    /*Group posts*/
    @FormUrlEncoded
    @POST("posts")
    Observable<postList> getGroupPosts(@Query("access_token") String access_token,
                                       @Field("server_key") String server_key,
                                       @Field("id") String group_id,
                                       @Field("type") String type,
                                       @Field("limit") String limit,
                                       @Field("after_post_id") String after_post_id);

    /*add member to group*/
    @FormUrlEncoded
    @POST("group_add")
    Observable<addingUser> addMemberToGroup(@Query("access_token") String access_token,
                                            @Field("server_key") String server_key,
                                            @Field("group_id") String group_id,
                                            @Field("user_id") String user_id);

    /*sign out*/
    @FormUrlEncoded
    @POST("delete-access-token")
    Observable<simpleResponse> logOutUser(@Query("access_token") String access_token,
                                          @Field("server_key") String server_key,
                                          @Field("user_id") String user_id);

    /*update password*/
    @FormUrlEncoded
    @POST("update-user-data")
    Observable<simpleResponse> changePassword(@Query("access_token") String access_token,
                                              @Field("server_key") String server_key,
                                              @Field("current_password") String current_password,
                                              @Field("new_password") String new_password,
                                              @Field("user_id") String user_id);

    /*creating a new post*/
    @FormUrlEncoded
    @POST("new_post")
    Observable<simpleResponse> createPost(@Query("access_token") String access_token,
                                          @Field("server_key") String server_key,
                                          @Field("user_id") String user_id,
                                          @Field("postText") String postText,
                                          @Field("post_color") String color,
                                          @Field("postFile") String postFile,
                                          @Field("postMap") String postMap);

    /*Post comments*/
    @FormUrlEncoded
    @POST("comments")
    Observable<commentsList> getPostComments(@Query("access_token") String access_token,
                                             @Field("server_key") String server_key,
                                             @Field("type") String type,
                                             @Field("post_id") String post_id);

    /*Comments Reply*/
    @FormUrlEncoded
    @POST("comments")
    Observable<commentsList> commentsActions(@Query("access_token") String access_token,
                                             @Field("server_key") String server_key,
                                             @Field("type") String type,
                                             @Field("comment_id") String comment_id,
                                             @Field("post_id") String post_id,
                                             @Field("text") String text);

    @FormUrlEncoded
    @POST("comments")
    Observable<simpleResponse> simpleCommentActions(@Query("access_token") String access_token,
                                                    @Field("server_key") String server_key,
                                                    @Field("type") String type,
                                                    @Field("comment_id") String comment_id,
                                                    @Field("post_id") String post_id,
                                                    @Field("text") String text);

    @FormUrlEncoded
    @POST("update-user-data")
    Observable<simpleResponse> updateUserData(@Query("access_token") String access_token,
                                              @Field("server_key") String server_key,
                                              @Field("username") String username,
                                              @Field("first_name") String first_name,
                                              @Field("last_name") String last_name,
                                              @Field("phone_number") String phone_number,
                                              @Field("email") String email,
                                              @Field("gender") String gender,
                                              @Field("working") String working,
                                              @Field("website") String website,
                                              @Field("school") String school,
                                              @Field("address") String address,
                                              @Field("about") String about,
                                              @Field("facebook") String facebook,
                                              @Field("twitter") String twitter,
                                              @Field("linkedin") String linkedin,
                                              @Field("instagram") String instagram,
                                              @Field("youtube") String youtube);

}
