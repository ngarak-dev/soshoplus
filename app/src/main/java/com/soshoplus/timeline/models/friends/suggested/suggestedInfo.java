/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.models.friends.suggested;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class suggestedInfo implements Serializable {
    
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("background_image")
    @Expose
    private String backgroundImage;
    @SerializedName("background_image_status")
    @Expose
    private String backgroundImageStatus;
    @SerializedName("relationship_id")
    @Expose
    private String relationshipId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("working")
    @Expose
    private String working;
    @SerializedName("working_link")
    @Expose
    private String workingLink;
    @SerializedName("about")
    @Expose
    private Object about;
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("google")
    @Expose
    private String google;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("linkedin")
    @Expose
    private String linkedin;
    @SerializedName("youtube")
    @Expose
    private String youtube;
    @SerializedName("vk")
    @Expose
    private String vk;
    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("name")
    @Expose
    private String name;
//    @SerializedName("email_code")
//    @Expose
//    private String emailCode;
//    @SerializedName("src")
//    @Expose
//    private String src;
//    @SerializedName("ip_address")
//    @Expose
//    private String ipAddress;
//    @SerializedName("follow_privacy")
//    @Expose
//    private String followPrivacy;
//    @SerializedName("friend_privacy")
//    @Expose
//    private String friendPrivacy;
//    @SerializedName("post_privacy")
//    @Expose
//    private String postPrivacy;
//    @SerializedName("message_privacy")
//    @Expose
//    private String messagePrivacy;
//    @SerializedName("confirm_followers")
//    @Expose
//    private String confirmFollowers;
//    @SerializedName("show_activities_privacy")
//    @Expose
//    private String showActivitiesPrivacy;
//    @SerializedName("birth_privacy")
//    @Expose
//    private String birthPrivacy;
//    @SerializedName("visit_privacy")
//    @Expose
//    private String visitPrivacy;
//    @SerializedName("verified")
//    @Expose
//    private String verified;
//    @SerializedName("lastseen")
//    @Expose
//    private String lastseen;
//    @SerializedName("showlastseen")
//    @Expose
//    private String showlastseen;
//    @SerializedName("emailNotification")
//    @Expose
//    private String emailNotification;
//    @SerializedName("e_liked")
//    @Expose
//    private String eLiked;
//    @SerializedName("e_wondered")
//    @Expose
//    private String eWondered;
//    @SerializedName("e_shared")
//    @Expose
//    private String eShared;
//    @SerializedName("e_followed")
//    @Expose
//    private String eFollowed;
//    @SerializedName("e_commented")
//    @Expose
//    private String eCommented;
//    @SerializedName("e_visited")
//    @Expose
//    private String eVisited;
//    @SerializedName("e_liked_page")
//    @Expose
//    private String eLikedPage;
//    @SerializedName("e_mentioned")
//    @Expose
//    private String eMentioned;
//    @SerializedName("e_joined_group")
//    @Expose
//    private String eJoinedGroup;
//    @SerializedName("e_accepted")
//    @Expose
//    private String eAccepted;
//    @SerializedName("e_profile_wall_post")
//    @Expose
//    private String eProfileWallPost;
//    @SerializedName("e_sentme_msg")
//    @Expose
//    private String eSentmeMsg;
//    @SerializedName("e_last_notif")
//    @Expose
//    private String eLastNotif;
//    @SerializedName("notification_settings")
//    @Expose
//    private String notificationSettings;
//    @SerializedName("status")
//    @Expose
//    private String status;
//    @SerializedName("active")
//    @Expose
//    private String active;
//    @SerializedName("admin")
//    @Expose
//    private String admin;
//    @SerializedName("type")
//    @Expose
//    private String type;
//    @SerializedName("registered")
//    @Expose
//    private String registered;
//    @SerializedName("start_up")
//    @Expose
//    private String startUp;
//    @SerializedName("start_up_info")
//    @Expose
//    private String startUpInfo;
//    @SerializedName("startup_follow")
//    @Expose
//    private String startupFollow;
//    @SerializedName("startup_image")
//    @Expose
//    private String startupImage;
//    @SerializedName("last_email_sent")
//    @Expose
//    private String lastEmailSent;
//    @SerializedName("phone_number")
//    @Expose
//    private String phoneNumber;
//    @SerializedName("sms_code")
//    @Expose
//    private String smsCode;
//    @SerializedName("is_pro")
//    @Expose
//    private String isPro;
//    @SerializedName("pro_time")
//    @Expose
//    private String proTime;
//    @SerializedName("pro_type")
//    @Expose
//    private String proType;
//    @SerializedName("joined")
//    @Expose
//    private String joined;
//    @SerializedName("css_file")
//    @Expose
//    private String cssFile;
//    @SerializedName("timezone")
//    @Expose
//    private String timezone;
//    @SerializedName("referrer")
//    @Expose
//    private String referrer;
//    @SerializedName("ref_user_id")
//    @Expose
//    private String refUserId;
//    @SerializedName("balance")
//    @Expose
//    private String balance;
//    @SerializedName("paypal_email")
//    @Expose
//    private String paypalEmail;
//    @SerializedName("notifications_sound")
//    @Expose
//    private String notificationsSound;
//    @SerializedName("order_posts_by")
//    @Expose
//    private String orderPostsBy;
//    @SerializedName("social_login")
//    @Expose
//    private String socialLogin;
//    @SerializedName("android_m_device_id")
//    @Expose
//    private String androidMDeviceId;
//    @SerializedName("ios_m_device_id")
//    @Expose
//    private String iosMDeviceId;
//    @SerializedName("android_n_device_id")
//    @Expose
//    private String androidNDeviceId;
//    @SerializedName("ios_n_device_id")
//    @Expose
//    private String iosNDeviceId;
//    @SerializedName("web_device_id")
//    @Expose
//    private String webDeviceId;
//    @SerializedName("wallet")
//    @Expose
//    private String wallet;
//    @SerializedName("lat")
//    @Expose
//    private String lat;
//    @SerializedName("lng")
//    @Expose
//    private String lng;
//    @SerializedName("last_location_update")
//    @Expose
//    private String lastLocationUpdate;
//    @SerializedName("share_my_location")
//    @Expose
//    private String shareMyLocation;
//    @SerializedName("last_data_update")
//    @Expose
//    private String lastDataUpdate;
//    @SerializedName("details")
//    @Expose
//    private suggestedDetails suggestedDetails;
//    @SerializedName("sidebar_data")
//    @Expose
//    private String sidebarData;
//    @SerializedName("last_avatar_mod")
//    @Expose
//    private String lastAvatarMod;
//    @SerializedName("last_cover_mod")
//    @Expose
//    private String lastCoverMod;
//    @SerializedName("points")
//    @Expose
//    private String points;
//    @SerializedName("daily_points")
//    @Expose
//    private String dailyPoints;
//    @SerializedName("point_day_expire")
//    @Expose
//    private String pointDayExpire;
//    @SerializedName("last_follow_id")
//    @Expose
//    private String lastFollowId;
//    @SerializedName("share_my_data")
//    @Expose
//    private String shareMyData;
//    @SerializedName("last_login_data")
//    @Expose
//    private Object lastLoginData;
//    @SerializedName("two_factor")
//    @Expose
//    private String twoFactor;
//    @SerializedName("new_email")
//    @Expose
//    private String newEmail;
//    @SerializedName("two_factor_verified")
//    @Expose
//    private String twoFactorVerified;
//    @SerializedName("new_phone")
//    @Expose
//    private String newPhone;
//    @SerializedName("info_file")
//    @Expose
//    private String infoFile;
//    @SerializedName("city")
//    @Expose
//    private String city;
//    @SerializedName("state")
//    @Expose
//    private String state;
//    @SerializedName("zip")
//    @Expose
//    private String zip;
//    @SerializedName("school_completed")
//    @Expose
//    private String schoolCompleted;
//    @SerializedName("weather_unit")
//    @Expose
//    private String weatherUnit;
//    @SerializedName("paystack_ref")
//    @Expose
//    private String paystackRef;
//    @SerializedName("avatar_org")
//    @Expose
//    private String avatarOrg;
//    @SerializedName("cover_org")
//    @Expose
//    private String coverOrg;
//    @SerializedName("cover_full")
//    @Expose
//    private String coverFull;
//    @SerializedName("id")
//    @Expose
//    private String id;
//    @SerializedName("user_platform")
//    @Expose
//    private String userPlatform;
//    @SerializedName("url")
//    @Expose
//    private String url;
//    @SerializedName("name")
//    @Expose
//    private String name;
//    @SerializedName("following_data")
//    @Expose
//    private List<String> followingData = null;
//    @SerializedName("followers_data")
//    @Expose
//    private List<String> followersData = null;
//    @SerializedName("mutual_friends_data")
//    @Expose
//    private String mutualFriendsData;
//    @SerializedName("likes_data")
//    @Expose
//    private String likesData;
//    @SerializedName("groups_data")
//    @Expose
//    private String groupsData;
//    @SerializedName("album_data")
//    @Expose
//    private String albumData;
//    @SerializedName("lastseen_unix_time")
//    @Expose
//    private String lastseenUnixTime;
//    @SerializedName("lastseen_status")
//    @Expose
//    private String lastseenStatus;
//    @SerializedName("password")
//    @Expose
//    private String password;
    private final static long serialVersionUID = 6446806168953463733L;
    
    /**
     * No args constructor for use in serialization
     */
    
    public suggestedInfo () {
    
    }
    
    /**
     * @param lastseen
     * @param proType
     * @param smsCode
     * @param about
     * @param startUpInfo
     * @param registered
     * @param instagram
     * @param pointDayExpire
     * @param type
     * @param cover
     * @param lastseenUnixTime
     * @param twitter
     * @param password
     * @param followPrivacy
     * @param lastFollowId
     * @param balance
     * @param eCommented
     * @param eLikedPage
     * @param weatherUnit
     * @param state
     * @param id
     * @param coverFull
     * @param zip
     * @param refUserId
     * @param followingData
     * @param lng
     * @param active
     * @param google
     * @param confirmFollowers
     * @param paypalEmail
     * @param androidNDeviceId
     * @param eLastNotif
     * @param lastEmailSent
     * @param lastLoginData
     * @param status
     * @param lastLocationUpdate
     * @param lastName
     * @param eFollowed
     * @param eSentmeMsg
     * @param gender
     * @param city
     * @param eProfileWallPost
     * @param paystackRef
     * @param backgroundImageStatus
     * @param sidebarData
     * @param email
     * @param showlastseen
     * @param emailNotification
     * @param website
     * @param address
     * @param wallet
     * @param eMentioned
     * @param eJoinedGroup
     * @param joined
     * @param facebook
     * @param avatar
     * @param relationshipId
     * @param userId
     * @param url
     * @param albumData
     * @param eShared
     * @param startUp
     * @param androidMDeviceId
     * @param twoFactorVerified
     * @param workingLink
     * @param newEmail
     * @param iosMDeviceId
     * @param dailyPoints
     * @param followersData
     * @param language
     * @param lastAvatarMod
     * @param startupImage
     * @param notificationSettings
     * @param points
     * @param startupFollow
     * @param newPhone
     * @param school
     * @param suggestedDetails
     * @param proTime
     * @param lat
     * @param eWondered
     * @param mutualFriendsData
     * @param cssFile
     * @param coverOrg
     * @param ipAddress
     * @param userPlatform
     * @param socialLogin
     * @param birthPrivacy
     * @param firstName
     * @param phoneNumber
     * @param vk
     * @param schoolCompleted
     * @param avatarOrg
     * @param name
     * @param birthday
     * @param youtube
     * @param twoFactor
     * @param eAccepted
     * @param likesData
     * @param backgroundImage
     * @param timezone
     * @param iosNDeviceId
     * @param admin
     * @param linkedin
     * @param countryId
     * @param messagePrivacy
     * @param notificationsSound
     * @param working
     * @param isPro
     * @param infoFile
     * @param friendPrivacy
     * @param eVisited
     * @param src
     * @param orderPostsBy
     * @param showActivitiesPrivacy
     * @param verified
     * @param groupsData
     * @param postPrivacy
     * @param eLiked
     * @param referrer
     * @param emailCode
     * @param webDeviceId
     * @param lastDataUpdate
     * @param lastseenStatus
     * @param visitPrivacy
     * @param lastCoverMod
     * @param username
     * @param shareMyLocation
     * @param shareMyData
     */
    
    public suggestedInfo (String userId, String username, String email, String firstName, String lastName, String avatar, String cover, String backgroundImage, String backgroundImageStatus, String relationshipId, String address, String working, String workingLink, Object about, String school, String gender, String birthday, String countryId, String website, String facebook, String google, String twitter, String linkedin, String youtube, String vk, String instagram, String language, String emailCode, String src, String ipAddress, String followPrivacy, String friendPrivacy, String postPrivacy, String messagePrivacy, String confirmFollowers, String showActivitiesPrivacy, String birthPrivacy, String visitPrivacy, String verified, String lastseen, String showlastseen, String emailNotification, String eLiked, String eWondered, String eShared, String eFollowed, String eCommented, String eVisited, String eLikedPage, String eMentioned, String eJoinedGroup, String eAccepted, String eProfileWallPost, String eSentmeMsg, String eLastNotif, String notificationSettings, String status, String active, String admin, String type, String registered, String startUp, String startUpInfo, String startupFollow, String startupImage, String lastEmailSent, String phoneNumber, String smsCode, String isPro, String proTime, String proType, String joined, String cssFile, String timezone, String referrer, String refUserId, String balance, String paypalEmail, String notificationsSound, String orderPostsBy, String socialLogin, String androidMDeviceId, String iosMDeviceId, String androidNDeviceId, String iosNDeviceId, String webDeviceId, String wallet, String lat, String lng, String lastLocationUpdate, String shareMyLocation, String lastDataUpdate, suggestedDetails suggestedDetails, String sidebarData, String lastAvatarMod, String lastCoverMod, String points, String dailyPoints, String pointDayExpire, String lastFollowId, String shareMyData, Object lastLoginData, String twoFactor, String newEmail, String twoFactorVerified, String newPhone, String infoFile, String city, String state, String zip, String schoolCompleted, String weatherUnit, String paystackRef, String avatarOrg, String coverOrg, String coverFull, String id, String userPlatform, String url, String name, List<String> followingData, List<String> followersData, String mutualFriendsData, String likesData, String groupsData, String albumData, String lastseenUnixTime, String lastseenStatus, String password) {
        super();
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.cover = cover;
        this.backgroundImage = backgroundImage;
        this.backgroundImageStatus = backgroundImageStatus;
        this.relationshipId = relationshipId;
        this.address = address;
        this.working = working;
        this.workingLink = workingLink;
        this.about = about;
        this.school = school;
        this.gender = gender;
        this.birthday = birthday;
        this.countryId = countryId;
        this.website = website;
        this.facebook = facebook;
        this.google = google;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.youtube = youtube;
        this.vk = vk;
        this.instagram = instagram;
        this.language = language;
//        this.emailCode = emailCode;
//        this.src = src;
//        this.ipAddress = ipAddress;
//        this.followPrivacy = followPrivacy;
//        this.friendPrivacy = friendPrivacy;
//        this.postPrivacy = postPrivacy;
//        this.messagePrivacy = messagePrivacy;
//        this.confirmFollowers = confirmFollowers;
//        this.showActivitiesPrivacy = showActivitiesPrivacy;
//        this.birthPrivacy = birthPrivacy;
//        this.visitPrivacy = visitPrivacy;
//        this.verified = verified;
//        this.lastseen = lastseen;
//        this.showlastseen = showlastseen;
//        this.emailNotification = emailNotification;
//        this.eLiked = eLiked;
//        this.eWondered = eWondered;
//        this.eShared = eShared;
//        this.eFollowed = eFollowed;
//        this.eCommented = eCommented;
//        this.eVisited = eVisited;
//        this.eLikedPage = eLikedPage;
//        this.eMentioned = eMentioned;
//        this.eJoinedGroup = eJoinedGroup;
//        this.eAccepted = eAccepted;
//        this.eProfileWallPost = eProfileWallPost;
//        this.eSentmeMsg = eSentmeMsg;
//        this.eLastNotif = eLastNotif;
//        this.notificationSettings = notificationSettings;
//        this.status = status;
//        this.active = active;
//        this.admin = admin;
//        this.type = type;
//        this.registered = registered;
//        this.startUp = startUp;
//        this.startUpInfo = startUpInfo;
//        this.startupFollow = startupFollow;
//        this.startupImage = startupImage;
//        this.lastEmailSent = lastEmailSent;
//        this.phoneNumber = phoneNumber;
//        this.smsCode = smsCode;
//        this.isPro = isPro;
//        this.proTime = proTime;
//        this.proType = proType;
//        this.joined = joined;
//        this.cssFile = cssFile;
//        this.timezone = timezone;
//        this.referrer = referrer;
//        this.refUserId = refUserId;
//        this.balance = balance;
//        this.paypalEmail = paypalEmail;
//        this.notificationsSound = notificationsSound;
//        this.orderPostsBy = orderPostsBy;
//        this.socialLogin = socialLogin;
//        this.androidMDeviceId = androidMDeviceId;
//        this.iosMDeviceId = iosMDeviceId;
//        this.androidNDeviceId = androidNDeviceId;
//        this.iosNDeviceId = iosNDeviceId;
//        this.webDeviceId = webDeviceId;
//        this.wallet = wallet;
//        this.lat = lat;
//        this.lng = lng;
//        this.lastLocationUpdate = lastLocationUpdate;
//        this.shareMyLocation = shareMyLocation;
//        this.lastDataUpdate = lastDataUpdate;
//        this.suggestedDetails = suggestedDetails;
//        this.sidebarData = sidebarData;
//        this.lastAvatarMod = lastAvatarMod;
//        this.lastCoverMod = lastCoverMod;
//        this.points = points;
//        this.dailyPoints = dailyPoints;
//        this.pointDayExpire = pointDayExpire;
//        this.lastFollowId = lastFollowId;
//        this.shareMyData = shareMyData;
//        this.lastLoginData = lastLoginData;
//        this.twoFactor = twoFactor;
//        this.newEmail = newEmail;
//        this.twoFactorVerified = twoFactorVerified;
//        this.newPhone = newPhone;
//        this.infoFile = infoFile;
//        this.city = city;
//        this.state = state;
//        this.zip = zip;
//        this.schoolCompleted = schoolCompleted;
//        this.weatherUnit = weatherUnit;
//        this.paystackRef = paystackRef;
//        this.avatarOrg = avatarOrg;
//        this.coverOrg = coverOrg;
//        this.coverFull = coverFull;
//        this.id = id;
//        this.userPlatform = userPlatform;
//        this.url = url;
        this.name = name;
//        this.followingData = followingData;
//        this.followersData = followersData;
//        this.mutualFriendsData = mutualFriendsData;
//        this.likesData = likesData;
//        this.groupsData = groupsData;
//        this.albumData = albumData;
//        this.lastseenUnixTime = lastseenUnixTime;
//        this.lastseenStatus = lastseenStatus;
//        this.password = password;
    }
    
    public String getUserId () {
        return userId;
    }
    
    public void setUserId (String userId) {
        this.userId = userId;
    }
    
    public suggestedInfo withUserId (String userId) {
        this.userId = userId;
        return this;
    }
    
    public String getUsername () {
        return username;
    }
    
    public void setUsername (String username) {
        this.username = username;
    }
    
    public suggestedInfo withUsername (String username) {
        this.username = username;
        return this;
    }
    
    public String getEmail () {
        return email;
    }
    
    public void setEmail (String email) {
        this.email = email;
    }
    
    public suggestedInfo withEmail (String email) {
        this.email = email;
        return this;
    }
    
    public String getFirstName () {
        return firstName;
    }
    
    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }
    
    public suggestedInfo withFirstName (String firstName) {
        this.firstName = firstName;
        return this;
    }
    
    public String getLastName () {
        return lastName;
    }
    
    public void setLastName (String lastName) {
        this.lastName = lastName;
    }
    
    public suggestedInfo withLastName (String lastName) {
        this.lastName = lastName;
        return this;
    }
    
    public String getAvatar () {
        return avatar;
    }
    
    public void setAvatar (String avatar) {
        this.avatar = avatar;
    }
    
    public suggestedInfo withAvatar (String avatar) {
        this.avatar = avatar;
        return this;
    }
    
    public String getCover () {
        return cover;
    }
    
    public void setCover (String cover) {
        this.cover = cover;
    }
    
    public suggestedInfo withCover (String cover) {
        this.cover = cover;
        return this;
    }
    
    public String getBackgroundImage () {
        return backgroundImage;
    }
    
    public void setBackgroundImage (String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
    
    public suggestedInfo withBackgroundImage (String backgroundImage) {
        this.backgroundImage = backgroundImage;
        return this;
    }
    
    public String getBackgroundImageStatus () {
        return backgroundImageStatus;
    }
    
    public void setBackgroundImageStatus (String backgroundImageStatus) {
        this.backgroundImageStatus = backgroundImageStatus;
    }
    
    public suggestedInfo withBackgroundImageStatus (String backgroundImageStatus) {
        this.backgroundImageStatus = backgroundImageStatus;
        return this;
    }
    
    public String getRelationshipId () {
        return relationshipId;
    }
    
    public void setRelationshipId (String relationshipId) {
        this.relationshipId = relationshipId;
    }
    
    public suggestedInfo withRelationshipId (String relationshipId) {
        this.relationshipId = relationshipId;
        return this;
    }
    
    public String getAddress () {
        return address;
    }
    
    public void setAddress (String address) {
        this.address = address;
    }
    
    public suggestedInfo withAddress (String address) {
        this.address = address;
        return this;
    }
    
    public String getWorking () {
        return working;
    }
    
    public void setWorking (String working) {
        this.working = working;
    }
    
    public suggestedInfo withWorking (String working) {
        this.working = working;
        return this;
    }
    
    public String getWorkingLink () {
        return workingLink;
    }
    
    public void setWorkingLink (String workingLink) {
        this.workingLink = workingLink;
    }
    
    public suggestedInfo withWorkingLink (String workingLink) {
        this.workingLink = workingLink;
        return this;
    }
    
    public Object getAbout () {
        return about;
    }
    
    public void setAbout (Object about) {
        this.about = about;
    }
    
    public suggestedInfo withAbout (Object about) {
        this.about = about;
        return this;
    }
    
    public String getSchool () {
        return school;
    }
    
    public void setSchool (String school) {
        this.school = school;
    }
    
    public suggestedInfo withSchool (String school) {
        this.school = school;
        return this;
    }
    
    public String getGender () {
        return gender;
    }
    
    public void setGender (String gender) {
        this.gender = gender;
    }
    
    public suggestedInfo withGender (String gender) {
        this.gender = gender;
        return this;
    }
    
    public String getBirthday () {
        return birthday;
    }
    
    public void setBirthday (String birthday) {
        this.birthday = birthday;
    }
    
    public suggestedInfo withBirthday (String birthday) {
        this.birthday = birthday;
        return this;
    }
    
    public String getCountryId () {
        return countryId;
    }
    
    public void setCountryId (String countryId) {
        this.countryId = countryId;
    }
    
    public suggestedInfo withCountryId (String countryId) {
        this.countryId = countryId;
        return this;
    }
    
    public String getWebsite () {
        return website;
    }
    
    public void setWebsite (String website) {
        this.website = website;
    }
    
    public suggestedInfo withWebsite (String website) {
        this.website = website;
        return this;
    }
    
    public String getFacebook () {
        return facebook;
    }
    
    public void setFacebook (String facebook) {
        this.facebook = facebook;
    }
    
    public suggestedInfo withFacebook (String facebook) {
        this.facebook = facebook;
        return this;
    }
    
    public String getGoogle () {
        return google;
    }
    
    public void setGoogle (String google) {
        this.google = google;
    }
    
    public suggestedInfo withGoogle (String google) {
        this.google = google;
        return this;
    }
    
    public String getTwitter () {
        return twitter;
    }
    
    public void setTwitter (String twitter) {
        this.twitter = twitter;
    }
    
    public suggestedInfo withTwitter (String twitter) {
        this.twitter = twitter;
        return this;
    }
    
    public String getLinkedin () {
        return linkedin;
    }
    
    public void setLinkedin (String linkedin) {
        this.linkedin = linkedin;
    }
    
    public suggestedInfo withLinkedin (String linkedin) {
        this.linkedin = linkedin;
        return this;
    }
    
    public String getYoutube () {
        return youtube;
    }
    
    public void setYoutube (String youtube) {
        this.youtube = youtube;
    }
    
    public suggestedInfo withYoutube (String youtube) {
        this.youtube = youtube;
        return this;
    }
    
    public String getVk () {
        return vk;
    }
    
    public void setVk (String vk) {
        this.vk = vk;
    }
    
    public suggestedInfo withVk (String vk) {
        this.vk = vk;
        return this;
    }
    
    public String getInstagram () {
        return instagram;
    }
    
    public void setInstagram (String instagram) {
        this.instagram = instagram;
    }
    
    public suggestedInfo withInstagram (String instagram) {
        this.instagram = instagram;
        return this;
    }
    
    public String getLanguage () {
        return language;
    }
    
    public void setLanguage (String language) {
        this.language = language;
    }
    
    public suggestedInfo withLanguage (String language) {
        this.language = language;
        return this;
    }
    
//    public String getEmailCode () {
//        return emailCode;
//    }
//
//    public void setEmailCode (String emailCode) {
//        this.emailCode = emailCode;
//    }
//
//    public suggestedInfo withEmailCode (String emailCode) {
//        this.emailCode = emailCode;
//        return this;
//    }
//
//    public String getSrc () {
//        return src;
//    }
//
//    public void setSrc (String src) {
//        this.src = src;
//    }
//
//    public suggestedInfo withSrc (String src) {
//        this.src = src;
//        return this;
//    }
//
//    public String getIpAddress () {
//        return ipAddress;
//    }
//
//    public void setIpAddress (String ipAddress) {
//        this.ipAddress = ipAddress;
//    }
//
//    public suggestedInfo withIpAddress (String ipAddress) {
//        this.ipAddress = ipAddress;
//        return this;
//    }
//
//    public String getFollowPrivacy () {
//        return followPrivacy;
//    }
//
//    public void setFollowPrivacy (String followPrivacy) {
//        this.followPrivacy = followPrivacy;
//    }
//
//    public suggestedInfo withFollowPrivacy (String followPrivacy) {
//        this.followPrivacy = followPrivacy;
//        return this;
//    }
//
//    public String getFriendPrivacy () {
//        return friendPrivacy;
//    }
//
//    public void setFriendPrivacy (String friendPrivacy) {
//        this.friendPrivacy = friendPrivacy;
//    }
//
//    public suggestedInfo withFriendPrivacy (String friendPrivacy) {
//        this.friendPrivacy = friendPrivacy;
//        return this;
//    }
//
//    public String getPostPrivacy () {
//        return postPrivacy;
//    }
//
//    public void setPostPrivacy (String postPrivacy) {
//        this.postPrivacy = postPrivacy;
//    }
//
//    public suggestedInfo withPostPrivacy (String postPrivacy) {
//        this.postPrivacy = postPrivacy;
//        return this;
//    }
//
//    public String getMessagePrivacy () {
//        return messagePrivacy;
//    }
//
//    public void setMessagePrivacy (String messagePrivacy) {
//        this.messagePrivacy = messagePrivacy;
//    }
//
//    public suggestedInfo withMessagePrivacy (String messagePrivacy) {
//        this.messagePrivacy = messagePrivacy;
//        return this;
//    }
//
//    public String getConfirmFollowers () {
//        return confirmFollowers;
//    }
//
//    public void setConfirmFollowers (String confirmFollowers) {
//        this.confirmFollowers = confirmFollowers;
//    }
//
//    public suggestedInfo withConfirmFollowers (String confirmFollowers) {
//        this.confirmFollowers = confirmFollowers;
//        return this;
//    }
//
//    public String getShowActivitiesPrivacy () {
//        return showActivitiesPrivacy;
//    }
//
//    public void setShowActivitiesPrivacy (String showActivitiesPrivacy) {
//        this.showActivitiesPrivacy = showActivitiesPrivacy;
//    }
//
//    public suggestedInfo withShowActivitiesPrivacy (String showActivitiesPrivacy) {
//        this.showActivitiesPrivacy = showActivitiesPrivacy;
//        return this;
//    }
//
//    public String getBirthPrivacy () {
//        return birthPrivacy;
//    }
//
//    public void setBirthPrivacy (String birthPrivacy) {
//        this.birthPrivacy = birthPrivacy;
//    }
//
//    public suggestedInfo withBirthPrivacy (String birthPrivacy) {
//        this.birthPrivacy = birthPrivacy;
//        return this;
//    }
//
//    public String getVisitPrivacy () {
//        return visitPrivacy;
//    }
//
//    public void setVisitPrivacy (String visitPrivacy) {
//        this.visitPrivacy = visitPrivacy;
//    }
//
//    public suggestedInfo withVisitPrivacy (String visitPrivacy) {
//        this.visitPrivacy = visitPrivacy;
//        return this;
//    }
//
//    public String getVerified () {
//        return verified;
//    }
//
//    public void setVerified (String verified) {
//        this.verified = verified;
//    }
//
//    public suggestedInfo withVerified (String verified) {
//        this.verified = verified;
//        return this;
//    }
//
//    public String getLastseen () {
//        return lastseen;
//    }
//
//    public void setLastseen (String lastseen) {
//        this.lastseen = lastseen;
//    }
//
//    public suggestedInfo withLastseen (String lastseen) {
//        this.lastseen = lastseen;
//        return this;
//    }
//
//    public String getShowlastseen () {
//        return showlastseen;
//    }
//
//    public void setShowlastseen (String showlastseen) {
//        this.showlastseen = showlastseen;
//    }
//
//    public suggestedInfo withShowlastseen (String showlastseen) {
//        this.showlastseen = showlastseen;
//        return this;
//    }
//
//    public String getEmailNotification () {
//        return emailNotification;
//    }
//
//    public void setEmailNotification (String emailNotification) {
//        this.emailNotification = emailNotification;
//    }
//
//    public suggestedInfo withEmailNotification (String emailNotification) {
//        this.emailNotification = emailNotification;
//        return this;
//    }
//
//    public String getELiked () {
//        return eLiked;
//    }
//
//    public void setELiked (String eLiked) {
//        this.eLiked = eLiked;
//    }
//
//    public suggestedInfo withELiked (String eLiked) {
//        this.eLiked = eLiked;
//        return this;
//    }
//
//    public String getEWondered () {
//        return eWondered;
//    }
//
//    public void setEWondered (String eWondered) {
//        this.eWondered = eWondered;
//    }
//
//    public suggestedInfo withEWondered (String eWondered) {
//        this.eWondered = eWondered;
//        return this;
//    }
//
//    public String getEShared () {
//        return eShared;
//    }
//
//    public void setEShared (String eShared) {
//        this.eShared = eShared;
//    }
//
//    public suggestedInfo withEShared (String eShared) {
//        this.eShared = eShared;
//        return this;
//    }
//
//    public String getEFollowed () {
//        return eFollowed;
//    }
//
//    public void setEFollowed (String eFollowed) {
//        this.eFollowed = eFollowed;
//    }
//
//    public suggestedInfo withEFollowed (String eFollowed) {
//        this.eFollowed = eFollowed;
//        return this;
//    }
//
//    public String getECommented () {
//        return eCommented;
//    }
//
//    public void setECommented (String eCommented) {
//        this.eCommented = eCommented;
//    }
//
//    public suggestedInfo withECommented (String eCommented) {
//        this.eCommented = eCommented;
//        return this;
//    }
//
//    public String getEVisited () {
//        return eVisited;
//    }
//
//    public void setEVisited (String eVisited) {
//        this.eVisited = eVisited;
//    }
//
//    public suggestedInfo withEVisited (String eVisited) {
//        this.eVisited = eVisited;
//        return this;
//    }
//
//    public String getELikedPage () {
//        return eLikedPage;
//    }
//
//    public void setELikedPage (String eLikedPage) {
//        this.eLikedPage = eLikedPage;
//    }
//
//    public suggestedInfo withELikedPage (String eLikedPage) {
//        this.eLikedPage = eLikedPage;
//        return this;
//    }
//
//    public String getEMentioned () {
//        return eMentioned;
//    }
//
//    public void setEMentioned (String eMentioned) {
//        this.eMentioned = eMentioned;
//    }
//
//    public suggestedInfo withEMentioned (String eMentioned) {
//        this.eMentioned = eMentioned;
//        return this;
//    }
//
//    public String getEJoinedGroup () {
//        return eJoinedGroup;
//    }
//
//    public void setEJoinedGroup (String eJoinedGroup) {
//        this.eJoinedGroup = eJoinedGroup;
//    }
//
//    public suggestedInfo withEJoinedGroup (String eJoinedGroup) {
//        this.eJoinedGroup = eJoinedGroup;
//        return this;
//    }
//
//    public String getEAccepted () {
//        return eAccepted;
//    }
//
//    public void setEAccepted (String eAccepted) {
//        this.eAccepted = eAccepted;
//    }
//
//    public suggestedInfo withEAccepted (String eAccepted) {
//        this.eAccepted = eAccepted;
//        return this;
//    }
//
//    public String getEProfileWallPost () {
//        return eProfileWallPost;
//    }
//
//    public void setEProfileWallPost (String eProfileWallPost) {
//        this.eProfileWallPost = eProfileWallPost;
//    }
//
//    public suggestedInfo withEProfileWallPost (String eProfileWallPost) {
//        this.eProfileWallPost = eProfileWallPost;
//        return this;
//    }
//
//    public String getESentmeMsg () {
//        return eSentmeMsg;
//    }
//
//    public void setESentmeMsg (String eSentmeMsg) {
//        this.eSentmeMsg = eSentmeMsg;
//    }
//
//    public suggestedInfo withESentmeMsg (String eSentmeMsg) {
//        this.eSentmeMsg = eSentmeMsg;
//        return this;
//    }
//
//    public String getELastNotif () {
//        return eLastNotif;
//    }
//
//    public void setELastNotif (String eLastNotif) {
//        this.eLastNotif = eLastNotif;
//    }
//
//    public suggestedInfo withELastNotif (String eLastNotif) {
//        this.eLastNotif = eLastNotif;
//        return this;
//    }
//
//    public String getNotificationSettings () {
//        return notificationSettings;
//    }
//
//    public void setNotificationSettings (String notificationSettings) {
//        this.notificationSettings = notificationSettings;
//    }
//
//    public suggestedInfo withNotificationSettings (String notificationSettings) {
//        this.notificationSettings = notificationSettings;
//        return this;
//    }
//
//    public String getStatus () {
//        return status;
//    }
//
//    public void setStatus (String status) {
//        this.status = status;
//    }
//
//    public suggestedInfo withStatus (String status) {
//        this.status = status;
//        return this;
//    }
//
//    public String getActive () {
//        return active;
//    }
//
//    public void setActive (String active) {
//        this.active = active;
//    }
//
//    public suggestedInfo withActive (String active) {
//        this.active = active;
//        return this;
//    }
//
//    public String getAdmin () {
//        return admin;
//    }
//
//    public void setAdmin (String admin) {
//        this.admin = admin;
//    }
//
//    public suggestedInfo withAdmin (String admin) {
//        this.admin = admin;
//        return this;
//    }
//
//    public String getType () {
//        return type;
//    }
//
//    public void setType (String type) {
//        this.type = type;
//    }
//
//    public suggestedInfo withType (String type) {
//        this.type = type;
//        return this;
//    }
//
//    public String getRegistered () {
//        return registered;
//    }
//
//    public void setRegistered (String registered) {
//        this.registered = registered;
//    }
//
//    public suggestedInfo withRegistered (String registered) {
//        this.registered = registered;
//        return this;
//    }
//
//    public String getStartUp () {
//        return startUp;
//    }
//
//    public void setStartUp (String startUp) {
//        this.startUp = startUp;
//    }
//
//    public suggestedInfo withStartUp (String startUp) {
//        this.startUp = startUp;
//        return this;
//    }
//
//    public String getStartUpInfo () {
//        return startUpInfo;
//    }
//
//    public void setStartUpInfo (String startUpInfo) {
//        this.startUpInfo = startUpInfo;
//    }
//
//    public suggestedInfo withStartUpInfo (String startUpInfo) {
//        this.startUpInfo = startUpInfo;
//        return this;
//    }
//
//    public String getStartupFollow () {
//        return startupFollow;
//    }
//
//    public void setStartupFollow (String startupFollow) {
//        this.startupFollow = startupFollow;
//    }
//
//    public suggestedInfo withStartupFollow (String startupFollow) {
//        this.startupFollow = startupFollow;
//        return this;
//    }
//
//    public String getStartupImage () {
//        return startupImage;
//    }
//
//    public void setStartupImage (String startupImage) {
//        this.startupImage = startupImage;
//    }
//
//    public suggestedInfo withStartupImage (String startupImage) {
//        this.startupImage = startupImage;
//        return this;
//    }
//
//    public String getLastEmailSent () {
//        return lastEmailSent;
//    }
//
//    public void setLastEmailSent (String lastEmailSent) {
//        this.lastEmailSent = lastEmailSent;
//    }
//
//    public suggestedInfo withLastEmailSent (String lastEmailSent) {
//        this.lastEmailSent = lastEmailSent;
//        return this;
//    }
//
//    public String getPhoneNumber () {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber (String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public suggestedInfo withPhoneNumber (String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//        return this;
//    }
//
//    public String getSmsCode () {
//        return smsCode;
//    }
//
//    public void setSmsCode (String smsCode) {
//        this.smsCode = smsCode;
//    }
//
//    public suggestedInfo withSmsCode (String smsCode) {
//        this.smsCode = smsCode;
//        return this;
//    }
//
//    public String getIsPro () {
//        return isPro;
//    }
//
//    public void setIsPro (String isPro) {
//        this.isPro = isPro;
//    }
//
//    public suggestedInfo withIsPro (String isPro) {
//        this.isPro = isPro;
//        return this;
//    }
//
//    public String getProTime () {
//        return proTime;
//    }
//
//    public void setProTime (String proTime) {
//        this.proTime = proTime;
//    }
//
//    public suggestedInfo withProTime (String proTime) {
//        this.proTime = proTime;
//        return this;
//    }
//
//    public String getProType () {
//        return proType;
//    }
//
//    public void setProType (String proType) {
//        this.proType = proType;
//    }
//
//    public suggestedInfo withProType (String proType) {
//        this.proType = proType;
//        return this;
//    }
//
//    public String getJoined () {
//        return joined;
//    }
//
//    public void setJoined (String joined) {
//        this.joined = joined;
//    }
//
//    public suggestedInfo withJoined (String joined) {
//        this.joined = joined;
//        return this;
//    }
//
//    public String getCssFile () {
//        return cssFile;
//    }
//
//    public void setCssFile (String cssFile) {
//        this.cssFile = cssFile;
//    }
//
//    public suggestedInfo withCssFile (String cssFile) {
//        this.cssFile = cssFile;
//        return this;
//    }
//
//    public String getTimezone () {
//        return timezone;
//    }
//
//    public void setTimezone (String timezone) {
//        this.timezone = timezone;
//    }
//
//    public suggestedInfo withTimezone (String timezone) {
//        this.timezone = timezone;
//        return this;
//    }
//
//    public String getReferrer () {
//        return referrer;
//    }
//
//    public void setReferrer (String referrer) {
//        this.referrer = referrer;
//    }
//
//    public suggestedInfo withReferrer (String referrer) {
//        this.referrer = referrer;
//        return this;
//    }
//
//    public String getRefUserId () {
//        return refUserId;
//    }
//
//    public void setRefUserId (String refUserId) {
//        this.refUserId = refUserId;
//    }
//
//    public suggestedInfo withRefUserId (String refUserId) {
//        this.refUserId = refUserId;
//        return this;
//    }
//
//    public String getBalance () {
//        return balance;
//    }
//
//    public void setBalance (String balance) {
//        this.balance = balance;
//    }
//
//    public suggestedInfo withBalance (String balance) {
//        this.balance = balance;
//        return this;
//    }
//
//    public String getPaypalEmail () {
//        return paypalEmail;
//    }
//
//    public void setPaypalEmail (String paypalEmail) {
//        this.paypalEmail = paypalEmail;
//    }
//
//    public suggestedInfo withPaypalEmail (String paypalEmail) {
//        this.paypalEmail = paypalEmail;
//        return this;
//    }
//
//    public String getNotificationsSound () {
//        return notificationsSound;
//    }
//
//    public void setNotificationsSound (String notificationsSound) {
//        this.notificationsSound = notificationsSound;
//    }
//
//    public suggestedInfo withNotificationsSound (String notificationsSound) {
//        this.notificationsSound = notificationsSound;
//        return this;
//    }
//
//    public String getOrderPostsBy () {
//        return orderPostsBy;
//    }
//
//    public void setOrderPostsBy (String orderPostsBy) {
//        this.orderPostsBy = orderPostsBy;
//    }
//
//    public suggestedInfo withOrderPostsBy (String orderPostsBy) {
//        this.orderPostsBy = orderPostsBy;
//        return this;
//    }
//
//    public String getSocialLogin () {
//        return socialLogin;
//    }
//
//    public void setSocialLogin (String socialLogin) {
//        this.socialLogin = socialLogin;
//    }
//
//    public suggestedInfo withSocialLogin (String socialLogin) {
//        this.socialLogin = socialLogin;
//        return this;
//    }
//
//    public String getAndroidMDeviceId () {
//        return androidMDeviceId;
//    }
//
//    public void setAndroidMDeviceId (String androidMDeviceId) {
//        this.androidMDeviceId = androidMDeviceId;
//    }
//
//    public suggestedInfo withAndroidMDeviceId (String androidMDeviceId) {
//        this.androidMDeviceId = androidMDeviceId;
//        return this;
//    }
//
//    public String getIosMDeviceId () {
//        return iosMDeviceId;
//    }
//
//    public void setIosMDeviceId (String iosMDeviceId) {
//        this.iosMDeviceId = iosMDeviceId;
//    }
//
//    public suggestedInfo withIosMDeviceId (String iosMDeviceId) {
//        this.iosMDeviceId = iosMDeviceId;
//        return this;
//    }
//
//    public String getAndroidNDeviceId () {
//        return androidNDeviceId;
//    }
//
//    public void setAndroidNDeviceId (String androidNDeviceId) {
//        this.androidNDeviceId = androidNDeviceId;
//    }
//
//    public suggestedInfo withAndroidNDeviceId (String androidNDeviceId) {
//        this.androidNDeviceId = androidNDeviceId;
//        return this;
//    }
//
//    public String getIosNDeviceId () {
//        return iosNDeviceId;
//    }
//
//    public void setIosNDeviceId (String iosNDeviceId) {
//        this.iosNDeviceId = iosNDeviceId;
//    }
//
//    public suggestedInfo withIosNDeviceId (String iosNDeviceId) {
//        this.iosNDeviceId = iosNDeviceId;
//        return this;
//    }
//
//    public String getWebDeviceId () {
//        return webDeviceId;
//    }
//
//    public void setWebDeviceId (String webDeviceId) {
//        this.webDeviceId = webDeviceId;
//    }
//
//    public suggestedInfo withWebDeviceId (String webDeviceId) {
//        this.webDeviceId = webDeviceId;
//        return this;
//    }
//
//    public String getWallet () {
//        return wallet;
//    }
//
//    public void setWallet (String wallet) {
//        this.wallet = wallet;
//    }
//
//    public suggestedInfo withWallet (String wallet) {
//        this.wallet = wallet;
//        return this;
//    }
//
//    public String getLat () {
//        return lat;
//    }
//
//    public void setLat (String lat) {
//        this.lat = lat;
//    }
//
//    public suggestedInfo withLat (String lat) {
//        this.lat = lat;
//        return this;
//    }
//
//    public String getLng () {
//        return lng;
//    }
//
//    public void setLng (String lng) {
//        this.lng = lng;
//    }
//
//    public suggestedInfo withLng (String lng) {
//        this.lng = lng;
//        return this;
//    }
//
//    public String getLastLocationUpdate () {
//        return lastLocationUpdate;
//    }
//
//    public void setLastLocationUpdate (String lastLocationUpdate) {
//        this.lastLocationUpdate = lastLocationUpdate;
//    }
//
//    public suggestedInfo withLastLocationUpdate (String lastLocationUpdate) {
//        this.lastLocationUpdate = lastLocationUpdate;
//        return this;
//    }
//
//    public String getShareMyLocation () {
//        return shareMyLocation;
//    }
//
//    public void setShareMyLocation (String shareMyLocation) {
//        this.shareMyLocation = shareMyLocation;
//    }
//
//    public suggestedInfo withShareMyLocation (String shareMyLocation) {
//        this.shareMyLocation = shareMyLocation;
//        return this;
//    }
//
//    public String getLastDataUpdate () {
//        return lastDataUpdate;
//    }
//
//    public void setLastDataUpdate (String lastDataUpdate) {
//        this.lastDataUpdate = lastDataUpdate;
//    }
//
//    public suggestedInfo withLastDataUpdate (String lastDataUpdate) {
//        this.lastDataUpdate = lastDataUpdate;
//        return this;
//    }
//
//    public String getSidebarData () {
//        return sidebarData;
//    }
//
//    public void setSidebarData (String sidebarData) {
//        this.sidebarData = sidebarData;
//    }
//
//    public suggestedInfo withSidebarData (String sidebarData) {
//        this.sidebarData = sidebarData;
//        return this;
//    }
//
//    public String getLastAvatarMod () {
//        return lastAvatarMod;
//    }
//
//    public void setLastAvatarMod (String lastAvatarMod) {
//        this.lastAvatarMod = lastAvatarMod;
//    }
//
//    public suggestedInfo withLastAvatarMod (String lastAvatarMod) {
//        this.lastAvatarMod = lastAvatarMod;
//        return this;
//    }
//
//    public String getLastCoverMod () {
//        return lastCoverMod;
//    }
//
//    public void setLastCoverMod (String lastCoverMod) {
//        this.lastCoverMod = lastCoverMod;
//    }
//
//    public suggestedInfo withLastCoverMod (String lastCoverMod) {
//        this.lastCoverMod = lastCoverMod;
//        return this;
//    }
//
//    public String getPoints () {
//        return points;
//    }
//
//    public void setPoints (String points) {
//        this.points = points;
//    }
//
//    public suggestedInfo withPoints (String points) {
//        this.points = points;
//        return this;
//    }
//
//    public String getDailyPoints () {
//        return dailyPoints;
//    }
//
//    public void setDailyPoints (String dailyPoints) {
//        this.dailyPoints = dailyPoints;
//    }
//
//    public suggestedInfo withDailyPoints (String dailyPoints) {
//        this.dailyPoints = dailyPoints;
//        return this;
//    }
//
//    public String getPointDayExpire () {
//        return pointDayExpire;
//    }
//
//    public void setPointDayExpire (String pointDayExpire) {
//        this.pointDayExpire = pointDayExpire;
//    }
//
//    public suggestedInfo withPointDayExpire (String pointDayExpire) {
//        this.pointDayExpire = pointDayExpire;
//        return this;
//    }
//
//    public String getLastFollowId () {
//        return lastFollowId;
//    }
//
//    public void setLastFollowId (String lastFollowId) {
//        this.lastFollowId = lastFollowId;
//    }
//
//    public suggestedInfo withLastFollowId (String lastFollowId) {
//        this.lastFollowId = lastFollowId;
//        return this;
//    }
//
//    public String getShareMyData () {
//        return shareMyData;
//    }
//
//    public void setShareMyData (String shareMyData) {
//        this.shareMyData = shareMyData;
//    }
//
//    public suggestedInfo withShareMyData (String shareMyData) {
//        this.shareMyData = shareMyData;
//        return this;
//    }
//
//    public Object getLastLoginData () {
//        return lastLoginData;
//    }
//
//    public void setLastLoginData (Object lastLoginData) {
//        this.lastLoginData = lastLoginData;
//    }
//
//    public suggestedInfo withLastLoginData (Object lastLoginData) {
//        this.lastLoginData = lastLoginData;
//        return this;
//    }
//
//    public String getTwoFactor () {
//        return twoFactor;
//    }
//
//    public void setTwoFactor (String twoFactor) {
//        this.twoFactor = twoFactor;
//    }
//
//    public suggestedInfo withTwoFactor (String twoFactor) {
//        this.twoFactor = twoFactor;
//        return this;
//    }
//
//    public String getNewEmail () {
//        return newEmail;
//    }
//
//    public void setNewEmail (String newEmail) {
//        this.newEmail = newEmail;
//    }
//
//    public suggestedInfo withNewEmail (String newEmail) {
//        this.newEmail = newEmail;
//        return this;
//    }
//
//    public String getTwoFactorVerified () {
//        return twoFactorVerified;
//    }
//
//    public void setTwoFactorVerified (String twoFactorVerified) {
//        this.twoFactorVerified = twoFactorVerified;
//    }
//
//    public suggestedInfo withTwoFactorVerified (String twoFactorVerified) {
//        this.twoFactorVerified = twoFactorVerified;
//        return this;
//    }
//
//    public String getNewPhone () {
//        return newPhone;
//    }
//
//    public void setNewPhone (String newPhone) {
//        this.newPhone = newPhone;
//    }
//
//    public suggestedInfo withNewPhone (String newPhone) {
//        this.newPhone = newPhone;
//        return this;
//    }
//
//    public String getInfoFile () {
//        return infoFile;
//    }
//
//    public void setInfoFile (String infoFile) {
//        this.infoFile = infoFile;
//    }
//
//    public suggestedInfo withInfoFile (String infoFile) {
//        this.infoFile = infoFile;
//        return this;
//    }
//
//    public String getCity () {
//        return city;
//    }
//
//    public void setCity (String city) {
//        this.city = city;
//    }
//
//    public suggestedInfo withCity (String city) {
//        this.city = city;
//        return this;
//    }
//
//    public String getState () {
//        return state;
//    }
//
//    public void setState (String state) {
//        this.state = state;
//    }
//
//    public suggestedInfo withState (String state) {
//        this.state = state;
//        return this;
//    }
//
//    public String getZip () {
//        return zip;
//    }
//
//    public void setZip (String zip) {
//        this.zip = zip;
//    }
//
//    public suggestedInfo withZip (String zip) {
//        this.zip = zip;
//        return this;
//    }
//
//    public String getSchoolCompleted () {
//        return schoolCompleted;
//    }
//
//    public void setSchoolCompleted (String schoolCompleted) {
//        this.schoolCompleted = schoolCompleted;
//    }
//
//    public suggestedInfo withSchoolCompleted (String schoolCompleted) {
//        this.schoolCompleted = schoolCompleted;
//        return this;
//    }
//
//    public String getWeatherUnit () {
//        return weatherUnit;
//    }
//
//    public void setWeatherUnit (String weatherUnit) {
//        this.weatherUnit = weatherUnit;
//    }
//
//    public suggestedInfo withWeatherUnit (String weatherUnit) {
//        this.weatherUnit = weatherUnit;
//        return this;
//    }
//
//    public String getPaystackRef () {
//        return paystackRef;
//    }
//
//    public void setPaystackRef (String paystackRef) {
//        this.paystackRef = paystackRef;
//    }
//
//    public suggestedInfo withPaystackRef (String paystackRef) {
//        this.paystackRef = paystackRef;
//        return this;
//    }
//
//    public String getAvatarOrg () {
//        return avatarOrg;
//    }
//
//    public void setAvatarOrg (String avatarOrg) {
//        this.avatarOrg = avatarOrg;
//    }
//
//    public suggestedInfo withAvatarOrg (String avatarOrg) {
//        this.avatarOrg = avatarOrg;
//        return this;
//    }
//
//    public String getCoverOrg () {
//        return coverOrg;
//    }
//
//    public void setCoverOrg (String coverOrg) {
//        this.coverOrg = coverOrg;
//    }
//
//    public suggestedInfo withCoverOrg (String coverOrg) {
//        this.coverOrg = coverOrg;
//        return this;
//    }
//
//    public String getCoverFull () {
//        return coverFull;
//    }
//
//    public void setCoverFull (String coverFull) {
//        this.coverFull = coverFull;
//    }
//
//    public suggestedInfo withCoverFull (String coverFull) {
//        this.coverFull = coverFull;
//        return this;
//    }
//
//    public String getId () {
//        return id;
//    }
//
//    public void setId (String id) {
//        this.id = id;
//    }
//
//    public suggestedInfo withId (String id) {
//        this.id = id;
//        return this;
//    }
//
//    public String getUserPlatform () {
//        return userPlatform;
//    }
//
//    public void setUserPlatform (String userPlatform) {
//        this.userPlatform = userPlatform;
//    }
//
//    public suggestedInfo withUserPlatform (String userPlatform) {
//        this.userPlatform = userPlatform;
//        return this;
//    }
//
//    public String getUrl () {
//        return url;
//    }
//
//    public void setUrl (String url) {
//        this.url = url;
//    }
//
//    public suggestedInfo withUrl (String url) {
//        this.url = url;
//        return this;
//    }
    
    public String getName () {
        return name;
    }
    
    public void setName (String name) {
        this.name = name;
    }
    
    public suggestedInfo withName (String name) {
        this.name = name;
        return this;
    }
    
//    public List<String> getFollowingData () {
//        return followingData;
//    }
//
//    public void setFollowingData (List<String> followingData) {
//        this.followingData = followingData;
//    }
//
//    public suggestedInfo withFollowingData (List<String> followingData) {
//        this.followingData = followingData;
//        return this;
//    }
//
//    public List<String> getFollowersData () {
//        return followersData;
//    }
//
//    public void setFollowersData (List<String> followersData) {
//        this.followersData = followersData;
//    }
//
//    public suggestedInfo withFollowersData (List<String> followersData) {
//        this.followersData = followersData;
//        return this;
//    }
//
//    public String getMutualFriendsData () {
//        return mutualFriendsData;
//    }
//
//    public void setMutualFriendsData (String mutualFriendsData) {
//        this.mutualFriendsData = mutualFriendsData;
//    }
//
//    public suggestedInfo withMutualFriendsData (String mutualFriendsData) {
//        this.mutualFriendsData = mutualFriendsData;
//        return this;
//    }
//
//    public String getLikesData () {
//        return likesData;
//    }
//
//    public void setLikesData (String likesData) {
//        this.likesData = likesData;
//    }
//
//    public suggestedInfo withLikesData (String likesData) {
//        this.likesData = likesData;
//        return this;
//    }
//
//    public String getGroupsData () {
//        return groupsData;
//    }
//
//    public void setGroupsData (String groupsData) {
//        this.groupsData = groupsData;
//    }
//
//    public suggestedInfo withGroupsData (String groupsData) {
//        this.groupsData = groupsData;
//        return this;
//    }
//
//    public String getAlbumData () {
//        return albumData;
//    }
//
//    public void setAlbumData (String albumData) {
//        this.albumData = albumData;
//    }
//
//    public suggestedInfo withAlbumData (String albumData) {
//        this.albumData = albumData;
//        return this;
//    }
//
//    public String getLastseenUnixTime () {
//        return lastseenUnixTime;
//    }
//
//    public void setLastseenUnixTime (String lastseenUnixTime) {
//        this.lastseenUnixTime = lastseenUnixTime;
//    }
//
//    public suggestedInfo withLastseenUnixTime (String lastseenUnixTime) {
//        this.lastseenUnixTime = lastseenUnixTime;
//        return this;
//    }
//
//    public String getLastseenStatus () {
//        return lastseenStatus;
//    }
//
//    public void setLastseenStatus (String lastseenStatus) {
//        this.lastseenStatus = lastseenStatus;
//    }
//
//    public suggestedInfo withLastseenStatus (String lastseenStatus) {
//        this.lastseenStatus = lastseenStatus;
//        return this;
//    }
//
//    public String getPassword () {
//        return password;
//    }
//
//    public void setPassword (String password) {
//        this.password = password;
//    }
//
//    public suggestedInfo withPassword (String password) {
//        this.password = password;
//        return this;
//    }
//
//    public suggestedDetails getSuggestedDetails () {
//        return suggestedDetails;
//    }
//
//    public void setSuggestedDetails (suggestedDetails suggestedDetails) {
//        this.suggestedDetails = suggestedDetails;
//    }
}