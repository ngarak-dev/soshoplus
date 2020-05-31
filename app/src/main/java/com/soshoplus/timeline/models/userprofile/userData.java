/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.models.userprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.soshoplus.timeline.models.apiErrors;

import java.io.Serializable;
import java.util.ArrayList;

public class userData implements Serializable {
    
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
    private String about;
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
    @SerializedName("ip_address")
    @Expose
    private String ipAddress;
    @SerializedName("follow_privacy")
    @Expose
    private String followPrivacy;
    @SerializedName("friend_privacy")
    @Expose
    private String friendPrivacy;
    @SerializedName("post_privacy")
    @Expose
    private String postPrivacy;
    @SerializedName("message_privacy")
    @Expose
    private String messagePrivacy;
    @SerializedName("confirm_followers")
    @Expose
    private String confirmFollowers;
    @SerializedName("show_activities_privacy")
    @Expose
    private String showActivitiesPrivacy;
    @SerializedName("birth_privacy")
    @Expose
    private String birthPrivacy;
    @SerializedName("visit_privacy")
    @Expose
    private String visitPrivacy;
    @SerializedName("verified")
    @Expose
    private String verified;
    @SerializedName("lastseen")
    @Expose
    private String lastseen;
    @SerializedName("emailNotification")
    @Expose
    private String emailNotification;
    @SerializedName("e_liked")
    @Expose
    private String eLiked;
    @SerializedName("e_wondered")
    @Expose
    private String eWondered;
    @SerializedName("e_shared")
    @Expose
    private String eShared;
    @SerializedName("e_followed")
    @Expose
    private String eFollowed;
    @SerializedName("e_commented")
    @Expose
    private String eCommented;
    @SerializedName("e_visited")
    @Expose
    private String eVisited;
    @SerializedName("e_liked_page")
    @Expose
    private String eLikedPage;
    @SerializedName("e_mentioned")
    @Expose
    private String eMentioned;
    @SerializedName("e_joined_group")
    @Expose
    private String eJoinedGroup;
    @SerializedName("e_accepted")
    @Expose
    private String eAccepted;
    @SerializedName("e_profile_wall_post")
    @Expose
    private String eProfileWallPost;
    @SerializedName("e_sentme_msg")
    @Expose
    private String eSentmeMsg;
    @SerializedName("e_last_notif")
    @Expose
    private String eLastNotif;
    @SerializedName("notification_settings")
    @Expose
    private String notificationSettings;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("admin")
    @Expose
    private String admin;
    @SerializedName("registered")
    @Expose
    private String registered;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("is_pro")
    @Expose
    private String isPro;
    @SerializedName("pro_type")
    @Expose
    private String proType;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("referrer")
    @Expose
    private String referrer;
    @SerializedName("ref_user_id")
    @Expose
    private String refUserId;
    @SerializedName("balance")
    @Expose
    private String balance;
    @SerializedName("paypal_email")
    @Expose
    private String paypalEmail;
    @SerializedName("notifications_sound")
    @Expose
    private String notificationsSound;
    @SerializedName("order_posts_by")
    @Expose
    private String orderPostsBy;
    @SerializedName("android_m_device_id")
    @Expose
    private String androidMDeviceId;
    @SerializedName("ios_m_device_id")
    @Expose
    private String iosMDeviceId;
    @SerializedName("android_n_device_id")
    @Expose
    private String androidNDeviceId;
    @SerializedName("ios_n_device_id")
    @Expose
    private String iosNDeviceId;
    @SerializedName("web_device_id")
    @Expose
    private String webDeviceId;
    @SerializedName("wallet")
    @Expose
    private String wallet;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("last_location_update")
    @Expose
    private String lastLocationUpdate;
    @SerializedName("share_my_location")
    @Expose
    private String shareMyLocation;
    @SerializedName("last_data_update")
    @Expose
    private String lastDataUpdate;
    @SerializedName("details")
    @Expose
    private details details;
    @SerializedName("last_avatar_mod")
    @Expose
    private String lastAvatarMod;
    @SerializedName("last_cover_mod")
    @Expose
    private String lastCoverMod;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("daily_points")
    @Expose
    private String dailyPoints;
    @SerializedName("point_day_expire")
    @Expose
    private String pointDayExpire;
    @SerializedName("last_follow_id")
    @Expose
    private String lastFollowId;
    @SerializedName("share_my_data")
    @Expose
    private String shareMyData;
    @SerializedName("last_login_data")
    @Expose
    private Object lastLoginData;
    @SerializedName("two_factor")
    @Expose
    private String twoFactor;
    @SerializedName("new_email")
    @Expose
    private String newEmail;
    @SerializedName("two_factor_verified")
    @Expose
    private String twoFactorVerified;
    @SerializedName("new_phone")
    @Expose
    private String newPhone;
    @SerializedName("info_file")
    @Expose
    private String infoFile;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("school_completed")
    @Expose
    private String schoolCompleted;
    @SerializedName("weather_unit")
    @Expose
    private String weatherUnit;
    @SerializedName("paystack_ref")
    @Expose
    private String paystackRef;
    @SerializedName("user_platform")
    @Expose
    private String userPlatform;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mutual_friends_data")
    @Expose
    private ArrayList mutualFriendsData;
    @SerializedName("lastseen_unix_time")
    @Expose
    private String lastseenUnixTime;
    @SerializedName("lastseen_status")
    @Expose
    private String lastseenStatus;
    @SerializedName("is_following")
    @Expose
    private int isFollowing;
    @SerializedName("can_follow")
    @Expose
    private int canFollow;
    @SerializedName("is_following_me")
    @Expose
    private int isFollowingMe;
    @SerializedName("gender_text")
    @Expose
    private String genderText;
    @SerializedName("lastseen_time_text")
    @Expose
    private String lastseenTimeText;
    @SerializedName("is_blocked")
    @Expose
    private boolean isBlocked;
    @SerializedName("errors")
    @Expose
    private apiErrors errors;
    private final static long serialVersionUID = -6169675304553903972L;
    
    /**
     * No args constructor for use in serialization
     */
    public userData () {
    }
    
    /**
     * @param userId
     * @param username
     * @param email
     * @param firstName
     * @param lastName
     * @param avatar
     * @param cover
     * @param backgroundImage
     * @param relationshipId
     * @param address
     * @param working
     * @param workingLink
     * @param about
     * @param school
     * @param gender
     * @param birthday
     * @param countryId
     * @param website
     * @param facebook
     * @param google
     * @param twitter
     * @param linkedin
     * @param youtube
     * @param vk
     * @param instagram
     * @param language
     * @param ipAddress
     * @param followPrivacy
     * @param friendPrivacy
     * @param postPrivacy
     * @param messagePrivacy
     * @param confirmFollowers
     * @param showActivitiesPrivacy
     * @param birthPrivacy
     * @param visitPrivacy
     * @param verified
     * @param lastseen
     * @param emailNotification
     * @param eLiked
     * @param eWondered
     * @param eShared
     * @param eFollowed
     * @param eCommented
     * @param eVisited
     * @param eLikedPage
     * @param eMentioned
     * @param eJoinedGroup
     * @param eAccepted
     * @param eProfileWallPost
     * @param eSentmeMsg
     * @param eLastNotif
     * @param notificationSettings
     * @param status
     * @param active
     * @param admin
     * @param registered
     * @param phoneNumber
     * @param isPro
     * @param proType
     * @param timezone
     * @param referrer
     * @param refUserId
     * @param balance
     * @param paypalEmail
     * @param notificationsSound
     * @param orderPostsBy
     * @param androidMDeviceId
     * @param iosMDeviceId
     * @param androidNDeviceId
     * @param iosNDeviceId
     * @param webDeviceId
     * @param wallet
     * @param lat
     * @param lng
     * @param lastLocationUpdate
     * @param shareMyLocation
     * @param lastDataUpdate
     * @param details
     * @param lastAvatarMod
     * @param lastCoverMod
     * @param points
     * @param dailyPoints
     * @param pointDayExpire
     * @param lastFollowId
     * @param shareMyData
     * @param lastLoginData
     * @param twoFactor
     * @param newEmail
     * @param twoFactorVerified
     * @param newPhone
     * @param infoFile
     * @param city
     * @param state
     * @param zip
     * @param schoolCompleted
     * @param weatherUnit
     * @param paystackRef
     * @param userPlatform
     * @param url
     * @param name
     * @param mutualFriendsData
     * @param lastseenUnixTime
     * @param lastseenStatus
     * @param isFollowing
     * @param canFollow
     * @param isFollowingMe
     * @param genderText
     * @param lastseenTimeText
     * @param isBlocked
     * @param errors
     */
    public userData (String userId, String username, String email, String firstName, String lastName, String avatar, String cover, String backgroundImage, String relationshipId, String address, String working, String workingLink, String about, String school, String gender, String birthday, String countryId, String website, String facebook, String google, String twitter, String linkedin, String youtube, String vk, String instagram, String language, String ipAddress, String followPrivacy, String friendPrivacy, String postPrivacy, String messagePrivacy, String confirmFollowers, String showActivitiesPrivacy, String birthPrivacy, String visitPrivacy, String verified, String lastseen, String emailNotification, String eLiked, String eWondered, String eShared, String eFollowed, String eCommented, String eVisited, String eLikedPage, String eMentioned, String eJoinedGroup, String eAccepted, String eProfileWallPost, String eSentmeMsg, String eLastNotif, String notificationSettings, String status, String active, String admin, String registered, String phoneNumber, String isPro, String proType, String timezone, String referrer, String refUserId, String balance, String paypalEmail, String notificationsSound, String orderPostsBy, String androidMDeviceId, String iosMDeviceId, String androidNDeviceId, String iosNDeviceId, String webDeviceId, String wallet, String lat, String lng, String lastLocationUpdate, String shareMyLocation, String lastDataUpdate, details details, String lastAvatarMod, String lastCoverMod, String points, String dailyPoints, String pointDayExpire, String lastFollowId, String shareMyData, Object lastLoginData, String twoFactor, String newEmail, String twoFactorVerified, String newPhone, String infoFile, String city, String state, String zip, String schoolCompleted, String weatherUnit, String paystackRef, String userPlatform, String url, String name, ArrayList mutualFriendsData, String lastseenUnixTime, String lastseenStatus, int isFollowing, int canFollow, int isFollowingMe, String genderText, String lastseenTimeText, boolean isBlocked, apiErrors errors) {
        super();
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.cover = cover;
        this.backgroundImage = backgroundImage;
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
        this.ipAddress = ipAddress;
        this.followPrivacy = followPrivacy;
        this.friendPrivacy = friendPrivacy;
        this.postPrivacy = postPrivacy;
        this.messagePrivacy = messagePrivacy;
        this.confirmFollowers = confirmFollowers;
        this.showActivitiesPrivacy = showActivitiesPrivacy;
        this.birthPrivacy = birthPrivacy;
        this.visitPrivacy = visitPrivacy;
        this.verified = verified;
        this.lastseen = lastseen;
        this.emailNotification = emailNotification;
        this.eLiked = eLiked;
        this.eWondered = eWondered;
        this.eShared = eShared;
        this.eFollowed = eFollowed;
        this.eCommented = eCommented;
        this.eVisited = eVisited;
        this.eLikedPage = eLikedPage;
        this.eMentioned = eMentioned;
        this.eJoinedGroup = eJoinedGroup;
        this.eAccepted = eAccepted;
        this.eProfileWallPost = eProfileWallPost;
        this.eSentmeMsg = eSentmeMsg;
        this.eLastNotif = eLastNotif;
        this.notificationSettings = notificationSettings;
        this.status = status;
        this.active = active;
        this.admin = admin;
        this.registered = registered;
        this.phoneNumber = phoneNumber;
        this.isPro = isPro;
        this.proType = proType;
        this.timezone = timezone;
        this.referrer = referrer;
        this.refUserId = refUserId;
        this.balance = balance;
        this.paypalEmail = paypalEmail;
        this.notificationsSound = notificationsSound;
        this.orderPostsBy = orderPostsBy;
        this.androidMDeviceId = androidMDeviceId;
        this.iosMDeviceId = iosMDeviceId;
        this.androidNDeviceId = androidNDeviceId;
        this.iosNDeviceId = iosNDeviceId;
        this.webDeviceId = webDeviceId;
        this.wallet = wallet;
        this.lat = lat;
        this.lng = lng;
        this.lastLocationUpdate = lastLocationUpdate;
        this.shareMyLocation = shareMyLocation;
        this.lastDataUpdate = lastDataUpdate;
        this.details = details;
        this.lastAvatarMod = lastAvatarMod;
        this.lastCoverMod = lastCoverMod;
        this.points = points;
        this.dailyPoints = dailyPoints;
        this.pointDayExpire = pointDayExpire;
        this.lastFollowId = lastFollowId;
        this.shareMyData = shareMyData;
        this.lastLoginData = lastLoginData;
        this.twoFactor = twoFactor;
        this.newEmail = newEmail;
        this.twoFactorVerified = twoFactorVerified;
        this.newPhone = newPhone;
        this.infoFile = infoFile;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.schoolCompleted = schoolCompleted;
        this.weatherUnit = weatherUnit;
        this.paystackRef = paystackRef;
        this.userPlatform = userPlatform;
        this.url = url;
        this.name = name;
        this.mutualFriendsData = mutualFriendsData;
        this.lastseenUnixTime = lastseenUnixTime;
        this.lastseenStatus = lastseenStatus;
        this.isFollowing = isFollowing;
        this.canFollow = canFollow;
        this.isFollowingMe = isFollowingMe;
        this.genderText = genderText;
        this.lastseenTimeText = lastseenTimeText;
        this.isBlocked = isBlocked;
        this.errors = errors;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public userData withUserId(String userId) {
        this.userId = userId;
        return this;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public userData withUsername(String username) {
        this.username = username;
        return this;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public userData withEmail(String email) {
        this.email = email;
        return this;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public userData withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public userData withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    public userData withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }
    
    public String getCover() {
        return cover;
    }
    
    public void setCover(String cover) {
        this.cover = cover;
    }
    
    public userData withCover(String cover) {
        this.cover = cover;
        return this;
    }
    
    public String getBackgroundImage() {
        return backgroundImage;
    }
    
    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
    
    public userData withBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
        return this;
    }
    
    public String getRelationshipId() {
        return relationshipId;
    }
    
    public void setRelationshipId(String relationshipId) {
        this.relationshipId = relationshipId;
    }
    
    public userData withRelationshipId(String relationshipId) {
        this.relationshipId = relationshipId;
        return this;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public userData withAddress(String address) {
        this.address = address;
        return this;
    }
    
    public String getWorking() {
        return working;
    }
    
    public void setWorking(String working) {
        this.working = working;
    }
    
    public userData withWorking(String working) {
        this.working = working;
        return this;
    }
    
    public String getWorkingLink() {
        return workingLink;
    }
    
    public void setWorkingLink(String workingLink) {
        this.workingLink = workingLink;
    }
    
    public userData withWorkingLink(String workingLink) {
        this.workingLink = workingLink;
        return this;
    }
    
    public String getAbout() {
        return about;
    }
    
    public void setAbout(String about) {
        this.about = about;
    }
    
    public userData withAbout(String about) {
        this.about = about;
        return this;
    }
    
    public String getSchool() {
        return school;
    }
    
    public void setSchool(String school) {
        this.school = school;
    }
    
    public userData withSchool(String school) {
        this.school = school;
        return this;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public userData withGender(String gender) {
        this.gender = gender;
        return this;
    }
    
    public String getBirthday() {
        return birthday;
    }
    
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    
    public userData withBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }
    
    public String getCountryId() {
        return countryId;
    }
    
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
    
    public userData withCountryId(String countryId) {
        this.countryId = countryId;
        return this;
    }
    
    public String getWebsite() {
        return website;
    }
    
    public void setWebsite(String website) {
        this.website = website;
    }
    
    public userData withWebsite(String website) {
        this.website = website;
        return this;
    }
    
    public String getFacebook() {
        return facebook;
    }
    
    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
    
    public userData withFacebook(String facebook) {
        this.facebook = facebook;
        return this;
    }
    
    public String getGoogle() {
        return google;
    }
    
    public void setGoogle(String google) {
        this.google = google;
    }
    
    public userData withGoogle(String google) {
        this.google = google;
        return this;
    }
    
    public String getTwitter() {
        return twitter;
    }
    
    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
    
    public userData withTwitter(String twitter) {
        this.twitter = twitter;
        return this;
    }
    
    public String getLinkedin() {
        return linkedin;
    }
    
    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }
    
    public userData withLinkedin(String linkedin) {
        this.linkedin = linkedin;
        return this;
    }
    
    public String getYoutube() {
        return youtube;
    }
    
    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }
    
    public userData withYoutube(String youtube) {
        this.youtube = youtube;
        return this;
    }
    
    public String getVk() {
        return vk;
    }
    
    public void setVk(String vk) {
        this.vk = vk;
    }
    
    public userData withVk(String vk) {
        this.vk = vk;
        return this;
    }
    
    public String getInstagram() {
        return instagram;
    }
    
    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }
    
    public userData withInstagram(String instagram) {
        this.instagram = instagram;
        return this;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public userData withLanguage(String language) {
        this.language = language;
        return this;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public userData withIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }
    
    public String getFollowPrivacy() {
        return followPrivacy;
    }
    
    public void setFollowPrivacy(String followPrivacy) {
        this.followPrivacy = followPrivacy;
    }
    
    public userData withFollowPrivacy(String followPrivacy) {
        this.followPrivacy = followPrivacy;
        return this;
    }
    
    public String getFriendPrivacy() {
        return friendPrivacy;
    }
    
    public void setFriendPrivacy(String friendPrivacy) {
        this.friendPrivacy = friendPrivacy;
    }
    
    public userData withFriendPrivacy(String friendPrivacy) {
        this.friendPrivacy = friendPrivacy;
        return this;
    }
    
    public String getPostPrivacy() {
        return postPrivacy;
    }
    
    public void setPostPrivacy(String postPrivacy) {
        this.postPrivacy = postPrivacy;
    }
    
    public userData withPostPrivacy(String postPrivacy) {
        this.postPrivacy = postPrivacy;
        return this;
    }
    
    public String getMessagePrivacy() {
        return messagePrivacy;
    }
    
    public void setMessagePrivacy(String messagePrivacy) {
        this.messagePrivacy = messagePrivacy;
    }
    
    public userData withMessagePrivacy(String messagePrivacy) {
        this.messagePrivacy = messagePrivacy;
        return this;
    }
    
    public String getConfirmFollowers() {
        return confirmFollowers;
    }
    
    public void setConfirmFollowers(String confirmFollowers) {
        this.confirmFollowers = confirmFollowers;
    }
    
    public userData withConfirmFollowers(String confirmFollowers) {
        this.confirmFollowers = confirmFollowers;
        return this;
    }
    
    public String getShowActivitiesPrivacy() {
        return showActivitiesPrivacy;
    }
    
    public void setShowActivitiesPrivacy(String showActivitiesPrivacy) {
        this.showActivitiesPrivacy = showActivitiesPrivacy;
    }
    
    public userData withShowActivitiesPrivacy(String showActivitiesPrivacy) {
        this.showActivitiesPrivacy = showActivitiesPrivacy;
        return this;
    }
    
    public String getBirthPrivacy() {
        return birthPrivacy;
    }
    
    public void setBirthPrivacy(String birthPrivacy) {
        this.birthPrivacy = birthPrivacy;
    }
    
    public userData withBirthPrivacy(String birthPrivacy) {
        this.birthPrivacy = birthPrivacy;
        return this;
    }
    
    public String getVisitPrivacy() {
        return visitPrivacy;
    }
    
    public void setVisitPrivacy(String visitPrivacy) {
        this.visitPrivacy = visitPrivacy;
    }
    
    public userData withVisitPrivacy(String visitPrivacy) {
        this.visitPrivacy = visitPrivacy;
        return this;
    }
    
    public String getVerified() {
        return verified;
    }
    
    public void setVerified(String verified) {
        this.verified = verified;
    }
    
    public userData withVerified(String verified) {
        this.verified = verified;
        return this;
    }
    
    public String getLastseen() {
        return lastseen;
    }
    
    public void setLastseen(String lastseen) {
        this.lastseen = lastseen;
    }
    
    public userData withLastseen(String lastseen) {
        this.lastseen = lastseen;
        return this;
    }
    
    public String getEmailNotification() {
        return emailNotification;
    }
    
    public void setEmailNotification(String emailNotification) {
        this.emailNotification = emailNotification;
    }
    
    public userData withEmailNotification(String emailNotification) {
        this.emailNotification = emailNotification;
        return this;
    }
    
    public String getELiked() {
        return eLiked;
    }
    
    public void setELiked(String eLiked) {
        this.eLiked = eLiked;
    }
    
    public userData withELiked(String eLiked) {
        this.eLiked = eLiked;
        return this;
    }
    
    public String getEWondered() {
        return eWondered;
    }
    
    public void setEWondered(String eWondered) {
        this.eWondered = eWondered;
    }
    
    public userData withEWondered(String eWondered) {
        this.eWondered = eWondered;
        return this;
    }
    
    public String getEShared() {
        return eShared;
    }
    
    public void setEShared(String eShared) {
        this.eShared = eShared;
    }
    
    public userData withEShared(String eShared) {
        this.eShared = eShared;
        return this;
    }
    
    public String getEFollowed() {
        return eFollowed;
    }
    
    public void setEFollowed(String eFollowed) {
        this.eFollowed = eFollowed;
    }
    
    public userData withEFollowed(String eFollowed) {
        this.eFollowed = eFollowed;
        return this;
    }
    
    public String getECommented() {
        return eCommented;
    }
    
    public void setECommented(String eCommented) {
        this.eCommented = eCommented;
    }
    
    public userData withECommented(String eCommented) {
        this.eCommented = eCommented;
        return this;
    }
    
    public String getEVisited() {
        return eVisited;
    }
    
    public void setEVisited(String eVisited) {
        this.eVisited = eVisited;
    }
    
    public userData withEVisited(String eVisited) {
        this.eVisited = eVisited;
        return this;
    }
    
    public String getELikedPage() {
        return eLikedPage;
    }
    
    public void setELikedPage(String eLikedPage) {
        this.eLikedPage = eLikedPage;
    }
    
    public userData withELikedPage(String eLikedPage) {
        this.eLikedPage = eLikedPage;
        return this;
    }
    
    public String getEMentioned() {
        return eMentioned;
    }
    
    public void setEMentioned(String eMentioned) {
        this.eMentioned = eMentioned;
    }
    
    public userData withEMentioned(String eMentioned) {
        this.eMentioned = eMentioned;
        return this;
    }
    
    public String getEJoinedGroup() {
        return eJoinedGroup;
    }
    
    public void setEJoinedGroup(String eJoinedGroup) {
        this.eJoinedGroup = eJoinedGroup;
    }
    
    public userData withEJoinedGroup(String eJoinedGroup) {
        this.eJoinedGroup = eJoinedGroup;
        return this;
    }
    
    public String getEAccepted() {
        return eAccepted;
    }
    
    public void setEAccepted(String eAccepted) {
        this.eAccepted = eAccepted;
    }
    
    public userData withEAccepted(String eAccepted) {
        this.eAccepted = eAccepted;
        return this;
    }
    
    public String getEProfileWallPost() {
        return eProfileWallPost;
    }
    
    public void setEProfileWallPost(String eProfileWallPost) {
        this.eProfileWallPost = eProfileWallPost;
    }
    
    public userData withEProfileWallPost(String eProfileWallPost) {
        this.eProfileWallPost = eProfileWallPost;
        return this;
    }
    
    public String getESentmeMsg() {
        return eSentmeMsg;
    }
    
    public void setESentmeMsg(String eSentmeMsg) {
        this.eSentmeMsg = eSentmeMsg;
    }
    
    public userData withESentmeMsg(String eSentmeMsg) {
        this.eSentmeMsg = eSentmeMsg;
        return this;
    }
    
    public String getELastNotif() {
        return eLastNotif;
    }
    
    public void setELastNotif(String eLastNotif) {
        this.eLastNotif = eLastNotif;
    }
    
    public userData withELastNotif(String eLastNotif) {
        this.eLastNotif = eLastNotif;
        return this;
    }
    
    public String getNotificationSettings() {
        return notificationSettings;
    }
    
    public void setNotificationSettings(String notificationSettings) {
        this.notificationSettings = notificationSettings;
    }
    
    public userData withNotificationSettings(String notificationSettings) {
        this.notificationSettings = notificationSettings;
        return this;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public userData withStatus(String status) {
        this.status = status;
        return this;
    }
    
    public String getActive() {
        return active;
    }
    
    public void setActive(String active) {
        this.active = active;
    }
    
    public userData withActive(String active) {
        this.active = active;
        return this;
    }
    
    public String getAdmin() {
        return admin;
    }
    
    public void setAdmin(String admin) {
        this.admin = admin;
    }
    
    public userData withAdmin(String admin) {
        this.admin = admin;
        return this;
    }
    
    public String getRegistered() {
        return registered;
    }
    
    public void setRegistered(String registered) {
        this.registered = registered;
    }
    
    public userData withRegistered(String registered) {
        this.registered = registered;
        return this;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public userData withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
    
    public String getIsPro() {
        return isPro;
    }
    
    public void setIsPro(String isPro) {
        this.isPro = isPro;
    }
    
    public userData withIsPro(String isPro) {
        this.isPro = isPro;
        return this;
    }
    
    public String getProType() {
        return proType;
    }
    
    public void setProType(String proType) {
        this.proType = proType;
    }
    
    public userData withProType(String proType) {
        this.proType = proType;
        return this;
    }
    
    public String getTimezone() {
        return timezone;
    }
    
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
    
    public userData withTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }
    
    public String getReferrer() {
        return referrer;
    }
    
    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }
    
    public userData withReferrer(String referrer) {
        this.referrer = referrer;
        return this;
    }
    
    public String getRefUserId() {
        return refUserId;
    }
    
    public void setRefUserId(String refUserId) {
        this.refUserId = refUserId;
    }
    
    public userData withRefUserId(String refUserId) {
        this.refUserId = refUserId;
        return this;
    }
    
    public String getBalance() {
        return balance;
    }
    
    public void setBalance(String balance) {
        this.balance = balance;
    }
    
    public userData withBalance(String balance) {
        this.balance = balance;
        return this;
    }
    
    public String getPaypalEmail() {
        return paypalEmail;
    }
    
    public void setPaypalEmail(String paypalEmail) {
        this.paypalEmail = paypalEmail;
    }
    
    public userData withPaypalEmail(String paypalEmail) {
        this.paypalEmail = paypalEmail;
        return this;
    }
    
    public String getNotificationsSound() {
        return notificationsSound;
    }
    
    public void setNotificationsSound(String notificationsSound) {
        this.notificationsSound = notificationsSound;
    }
    
    public userData withNotificationsSound(String notificationsSound) {
        this.notificationsSound = notificationsSound;
        return this;
    }
    
    public String getOrderPostsBy() {
        return orderPostsBy;
    }
    
    public void setOrderPostsBy(String orderPostsBy) {
        this.orderPostsBy = orderPostsBy;
    }
    
    public userData withOrderPostsBy(String orderPostsBy) {
        this.orderPostsBy = orderPostsBy;
        return this;
    }
    
    public String getAndroidMDeviceId() {
        return androidMDeviceId;
    }
    
    public void setAndroidMDeviceId(String androidMDeviceId) {
        this.androidMDeviceId = androidMDeviceId;
    }
    
    public userData withAndroidMDeviceId(String androidMDeviceId) {
        this.androidMDeviceId = androidMDeviceId;
        return this;
    }
    
    public String getIosMDeviceId() {
        return iosMDeviceId;
    }
    
    public void setIosMDeviceId(String iosMDeviceId) {
        this.iosMDeviceId = iosMDeviceId;
    }
    
    public userData withIosMDeviceId(String iosMDeviceId) {
        this.iosMDeviceId = iosMDeviceId;
        return this;
    }
    
    public String getAndroidNDeviceId() {
        return androidNDeviceId;
    }
    
    public void setAndroidNDeviceId(String androidNDeviceId) {
        this.androidNDeviceId = androidNDeviceId;
    }
    
    public userData withAndroidNDeviceId(String androidNDeviceId) {
        this.androidNDeviceId = androidNDeviceId;
        return this;
    }
    
    public String getIosNDeviceId() {
        return iosNDeviceId;
    }
    
    public void setIosNDeviceId(String iosNDeviceId) {
        this.iosNDeviceId = iosNDeviceId;
    }
    
    public userData withIosNDeviceId(String iosNDeviceId) {
        this.iosNDeviceId = iosNDeviceId;
        return this;
    }
    
    public String getWebDeviceId() {
        return webDeviceId;
    }
    
    public void setWebDeviceId(String webDeviceId) {
        this.webDeviceId = webDeviceId;
    }
    
    public userData withWebDeviceId(String webDeviceId) {
        this.webDeviceId = webDeviceId;
        return this;
    }
    
    public String getWallet() {
        return wallet;
    }
    
    public void setWallet(String wallet) {
        this.wallet = wallet;
    }
    
    public userData withWallet(String wallet) {
        this.wallet = wallet;
        return this;
    }
    
    public String getLat() {
        return lat;
    }
    
    public void setLat(String lat) {
        this.lat = lat;
    }
    
    public userData withLat(String lat) {
        this.lat = lat;
        return this;
    }
    
    public String getLng() {
        return lng;
    }
    
    public void setLng(String lng) {
        this.lng = lng;
    }
    
    public userData withLng(String lng) {
        this.lng = lng;
        return this;
    }
    
    public String getLastLocationUpdate() {
        return lastLocationUpdate;
    }
    
    public void setLastLocationUpdate(String lastLocationUpdate) {
        this.lastLocationUpdate = lastLocationUpdate;
    }
    
    public userData withLastLocationUpdate(String lastLocationUpdate) {
        this.lastLocationUpdate = lastLocationUpdate;
        return this;
    }
    
    public String getShareMyLocation() {
        return shareMyLocation;
    }
    
    public void setShareMyLocation(String shareMyLocation) {
        this.shareMyLocation = shareMyLocation;
    }
    
    public userData withShareMyLocation(String shareMyLocation) {
        this.shareMyLocation = shareMyLocation;
        return this;
    }
    
    public String getLastDataUpdate() {
        return lastDataUpdate;
    }
    
    public void setLastDataUpdate(String lastDataUpdate) {
        this.lastDataUpdate = lastDataUpdate;
    }
    
    public userData withLastDataUpdate(String lastDataUpdate) {
        this.lastDataUpdate = lastDataUpdate;
        return this;
    }
    
    public details getDetails() {
        return details;
    }
    
    public void setDetails(details details) {
        this.details = details;
    }
    
    public userData withDetails(details details) {
        this.details = details;
        return this;
    }
    
    public String getLastAvatarMod() {
        return lastAvatarMod;
    }
    
    public void setLastAvatarMod(String lastAvatarMod) {
        this.lastAvatarMod = lastAvatarMod;
    }
    
    public userData withLastAvatarMod(String lastAvatarMod) {
        this.lastAvatarMod = lastAvatarMod;
        return this;
    }
    
    public String getLastCoverMod() {
        return lastCoverMod;
    }
    
    public void setLastCoverMod(String lastCoverMod) {
        this.lastCoverMod = lastCoverMod;
    }
    
    public userData withLastCoverMod(String lastCoverMod) {
        this.lastCoverMod = lastCoverMod;
        return this;
    }
    
    public String getPoints() {
        return points;
    }
    
    public void setPoints(String points) {
        this.points = points;
    }
    
    public userData withPoints(String points) {
        this.points = points;
        return this;
    }
    
    public String getDailyPoints() {
        return dailyPoints;
    }
    
    public void setDailyPoints(String dailyPoints) {
        this.dailyPoints = dailyPoints;
    }
    
    public userData withDailyPoints(String dailyPoints) {
        this.dailyPoints = dailyPoints;
        return this;
    }
    
    public String getPointDayExpire() {
        return pointDayExpire;
    }
    
    public void setPointDayExpire(String pointDayExpire) {
        this.pointDayExpire = pointDayExpire;
    }
    
    public userData withPointDayExpire(String pointDayExpire) {
        this.pointDayExpire = pointDayExpire;
        return this;
    }
    
    public String getLastFollowId() {
        return lastFollowId;
    }
    
    public void setLastFollowId(String lastFollowId) {
        this.lastFollowId = lastFollowId;
    }
    
    public userData withLastFollowId(String lastFollowId) {
        this.lastFollowId = lastFollowId;
        return this;
    }
    
    public String getShareMyData() {
        return shareMyData;
    }
    
    public void setShareMyData(String shareMyData) {
        this.shareMyData = shareMyData;
    }
    
    public userData withShareMyData(String shareMyData) {
        this.shareMyData = shareMyData;
        return this;
    }
    
    public Object getLastLoginData() {
        return lastLoginData;
    }
    
    public void setLastLoginData(Object lastLoginData) {
        this.lastLoginData = lastLoginData;
    }
    
    public userData withLastLoginData(Object lastLoginData) {
        this.lastLoginData = lastLoginData;
        return this;
    }
    
    public String getTwoFactor() {
        return twoFactor;
    }
    
    public void setTwoFactor(String twoFactor) {
        this.twoFactor = twoFactor;
    }
    
    public userData withTwoFactor(String twoFactor) {
        this.twoFactor = twoFactor;
        return this;
    }
    
    public String getNewEmail() {
        return newEmail;
    }
    
    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
    
    public userData withNewEmail(String newEmail) {
        this.newEmail = newEmail;
        return this;
    }
    
    public String getTwoFactorVerified() {
        return twoFactorVerified;
    }
    
    public void setTwoFactorVerified(String twoFactorVerified) {
        this.twoFactorVerified = twoFactorVerified;
    }
    
    public userData withTwoFactorVerified(String twoFactorVerified) {
        this.twoFactorVerified = twoFactorVerified;
        return this;
    }
    
    public String getNewPhone() {
        return newPhone;
    }
    
    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }
    
    public userData withNewPhone(String newPhone) {
        this.newPhone = newPhone;
        return this;
    }
    
    public String getInfoFile() {
        return infoFile;
    }
    
    public void setInfoFile(String infoFile) {
        this.infoFile = infoFile;
    }
    
    public userData withInfoFile(String infoFile) {
        this.infoFile = infoFile;
        return this;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public userData withCity(String city) {
        this.city = city;
        return this;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public userData withState(String state) {
        this.state = state;
        return this;
    }
    
    public String getZip() {
        return zip;
    }
    
    public void setZip(String zip) {
        this.zip = zip;
    }
    
    public userData withZip(String zip) {
        this.zip = zip;
        return this;
    }
    
    public String getSchoolCompleted() {
        return schoolCompleted;
    }
    
    public void setSchoolCompleted(String schoolCompleted) {
        this.schoolCompleted = schoolCompleted;
    }
    
    public userData withSchoolCompleted(String schoolCompleted) {
        this.schoolCompleted = schoolCompleted;
        return this;
    }
    
    public String getWeatherUnit() {
        return weatherUnit;
    }
    
    public void setWeatherUnit(String weatherUnit) {
        this.weatherUnit = weatherUnit;
    }
    
    public userData withWeatherUnit(String weatherUnit) {
        this.weatherUnit = weatherUnit;
        return this;
    }
    
    public String getPaystackRef() {
        return paystackRef;
    }
    
    public void setPaystackRef(String paystackRef) {
        this.paystackRef = paystackRef;
    }
    
    public userData withPaystackRef(String paystackRef) {
        this.paystackRef = paystackRef;
        return this;
    }
    
    public String getUserPlatform() {
        return userPlatform;
    }
    
    public void setUserPlatform(String userPlatform) {
        this.userPlatform = userPlatform;
    }
    
    public userData withUserPlatform(String userPlatform) {
        this.userPlatform = userPlatform;
        return this;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public userData withUrl(String url) {
        this.url = url;
        return this;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public userData withName(String name) {
        this.name = name;
        return this;
    }
    
    public ArrayList getMutualFriendsData() {
        return mutualFriendsData;
    }
    
    public void setMutualFriendsData(ArrayList mutualFriendsData) {
        this.mutualFriendsData = mutualFriendsData;
    }
    
    public userData withMutualFriendsData(ArrayList mutualFriendsData) {
        this.mutualFriendsData = mutualFriendsData;
        return this;
    }
    
    public String getLastseenUnixTime() {
        return lastseenUnixTime;
    }
    
    public void setLastseenUnixTime(String lastseenUnixTime) {
        this.lastseenUnixTime = lastseenUnixTime;
    }
    
    public userData withLastseenUnixTime(String lastseenUnixTime) {
        this.lastseenUnixTime = lastseenUnixTime;
        return this;
    }
    
    public String getLastseenStatus() {
        return lastseenStatus;
    }
    
    public void setLastseenStatus(String lastseenStatus) {
        this.lastseenStatus = lastseenStatus;
    }
    
    public userData withLastseenStatus(String lastseenStatus) {
        this.lastseenStatus = lastseenStatus;
        return this;
    }
    
    public int getIsFollowing() {
        return isFollowing;
    }
    
    public void setIsFollowing(int isFollowing) {
        this.isFollowing = isFollowing;
    }
    
    public userData withIsFollowing(int isFollowing) {
        this.isFollowing = isFollowing;
        return this;
    }
    
    public int getCanFollow() {
        return canFollow;
    }
    
    public void setCanFollow(int canFollow) {
        this.canFollow = canFollow;
    }
    
    public userData withCanFollow(int canFollow) {
        this.canFollow = canFollow;
        return this;
    }
    
    public int getIsFollowingMe() {
        return isFollowingMe;
    }
    
    public void setIsFollowingMe(int isFollowingMe) {
        this.isFollowingMe = isFollowingMe;
    }
    
    public userData withIsFollowingMe(int isFollowingMe) {
        this.isFollowingMe = isFollowingMe;
        return this;
    }
    
    public String getGenderText() {
        return genderText;
    }
    
    public void setGenderText(String genderText) {
        this.genderText = genderText;
    }
    
    public userData withGenderText(String genderText) {
        this.genderText = genderText;
        return this;
    }
    
    public String getLastseenTimeText() {
        return lastseenTimeText;
    }
    
    public void setLastseenTimeText(String lastseenTimeText) {
        this.lastseenTimeText = lastseenTimeText;
    }
    
    public userData withLastseenTimeText(String lastseenTimeText) {
        this.lastseenTimeText = lastseenTimeText;
        return this;
    }
    
    public boolean isIsBlocked() {
        return isBlocked;
    }
    
    public void setIsBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }
    
    public userData withIsBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
        return this;
    }
    
    public apiErrors getErrors () {
        return errors;
    }
    
    public void setErrors (apiErrors errors) {
        this.errors = errors;
    }
}
