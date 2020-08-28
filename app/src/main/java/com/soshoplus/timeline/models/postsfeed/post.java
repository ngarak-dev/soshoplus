/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.models.postsfeed;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class post implements Serializable {
	
	/*BY POST TYPE*/
	public static int DEFAULT_POST = 1;
	public static int PROFILE_PIC = 2;
	public static int COVER_PIC = 3;
	public static int ADS = 4;
	public static int SHARED_POST = 5;
	/*COLOURED POST*/
	public static int COLOURED_POST = 6;
	/*VIDEO POST*/
	public static int VIDEO_POST = 7;
	/*IMAGE POST*/
	public static int IMAGE_POST = 8;
	/*AUDIO POST*/
	public static int AUDIO_POST = 9;
	/*BLOG POST*/
	public static int BLOG_POST = 10;
	/*MAP POST*/
	public static int MAP_POST = 11;
	/*MULTI IMAGE POST*/
	public static int MULTI_IMAGE_POST = 12;

	@SerializedName("ad_media")
	private String adMedia;

	@SerializedName("gender")
	private String gender;

	@SerializedName("bidding")
	private String bidding;

	@SerializedName("description")
	private String description;

	@SerializedName("appears")
	private String appears;

	@SerializedName("posted")
	private String posted;

	@SerializedName("page_id")
	private String pageId;

	@SerializedName("end")
	private String end;

	@SerializedName("id")
	private String id;

	@SerializedName("headline")
	private String headline;

	@SerializedName("views")
	private String views;

	@SerializedName("budget")
	private String budget;

	@SerializedName("audience")
	private String audience;

	@SerializedName("shared_info")
	private Object sharedInfo;

	@SerializedName("postType")
	private String postType;

	@SerializedName("is_owner")
	private boolean isOwner;

	@SerializedName("spent")
	private String spent;

	@SerializedName("start")
	private String start;

	@SerializedName("user_data")
	private Object userData;	/*This is an Array*/

	@SerializedName("url")
	private String url;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("name")
	private String name;

	@SerializedName("clicks")
	private String clicks;

	@SerializedName("publisher")
	private publisherInfo publisherInfo;

	@SerializedName("location")
	private String location;

	@SerializedName("status")
	private String status;

	@SerializedName("postFeeling")
	private String postFeeling;

	@SerializedName("postDailymotion")
	private String postDailymotion;

	@SerializedName("postLinkImage")
	private String postLinkImage;

	@SerializedName("postFileName")
	private String postFileName;

	@SerializedName("postSoundCloud")
	private String postSoundCloud;

	@SerializedName("page_event_id")
	private String pageEventId;

	@SerializedName("registered")
	private String registered;

	@SerializedName("offer")
	private List<Object> offer;

	@SerializedName("is_post_saved")
	private boolean isPostSaved;

	@SerializedName("stream_name")
	private String streamName;

	@SerializedName("postPlaying")
	private String postPlaying;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("options")
	private List<Object> options;

	@SerializedName("is_wondered")
	private boolean isWondered;

	@SerializedName("cache")
	private String cache;

	@SerializedName("postFile")
	private String postFile;

	@SerializedName("group_admin")
	private boolean groupAdmin;

	@SerializedName("active")
	private String active;

	@SerializedName("live_ended")
	private String liveEnded;

	@SerializedName("photo_album")
	private List<Object> photoAlbum;
	
	@SerializedName("photo_multi")
	private List<photoMulti> photoMulti;

	@SerializedName("postMap")
	private String postMap;

	@SerializedName("is_post_pinned")
	private boolean isPostPinned;

	@SerializedName("postDeepsound")
	private String postDeepsound;

	@SerializedName("is_post_reported")
	private boolean isPostReported;

	@SerializedName("agora_resource_id")
	private Object agoraResourceId;

	@SerializedName("comments_status")
	private String commentsStatus;

	@SerializedName("postFileThumb")
	private String postFileThumb;

	@SerializedName("post_is_promoted")
	private int postIsPromoted;

	@SerializedName("postText_API")
	private String postTextAPI;

	@SerializedName("postSticker")
	private Object postSticker;

	@SerializedName("is_post_boosted")
	private int isPostBoosted;

	@SerializedName("postWatching")
	private String postWatching;

	@SerializedName("blur")
	private String blur;

	@SerializedName("fund_id")
	private String fundId;

	@SerializedName("is_group_post")
	private boolean isGroupPost;

	@SerializedName("forum_id")
	private String forumId;

	@SerializedName("postVimeo")
	private String postVimeo;

	@SerializedName("boosted")
	private String boosted;

	@SerializedName("group_recipient_exists")
	private boolean groupRecipientExists;

	@SerializedName("recipient_id")
	private String recipientId;

	@SerializedName("is_liked")
	private boolean isLiked;

	@SerializedName("agora_sid")
	private String agoraSid;

	@SerializedName("blog_id")
	private String blogId;

	@SerializedName("limited_comments")
	private boolean limitedComments;

	@SerializedName("live_time")
	private String liveTime;

	@SerializedName("post_wonders")
	private String postWonders;

	@SerializedName("post_share")
	private String postShare;

	@SerializedName("post_comments")
	private String postComments;

	@SerializedName("question_id")
	private String questionId;

	@SerializedName("post_time")
	private String postTime;

	@SerializedName("event_id")
	private String eventId;

	@SerializedName("post_url")
	private Object postUrl;

	@SerializedName("postVine")
	private String postVine;

	@SerializedName("fund_raise_id")
	private String fundRaiseId;

	@SerializedName("limit_comments")
	private int limitComments;

	@SerializedName("multi_image")
	private String multiImage;

	@SerializedName("blog")
	private blog blog;

	@SerializedName("voted_id")
	private int votedId;

	@SerializedName("live_sub_users")
	private int liveSubUsers;

	@SerializedName("thread_id")
	private String threadId;

	@SerializedName("postLink")
	private String postLink;

	@SerializedName("postLinkContent")
	private String postLinkContent;

	@SerializedName("postYoutube")
	private String postYoutube;

	@SerializedName("post_shares")
	private String postShares;

	@SerializedName("post_id")
	private String postId;

	@SerializedName("parent_id")
	private String parentId;

	@SerializedName("postTraveling")
	private String postTraveling;

	@SerializedName("page")
	private int page;

	@SerializedName("job")
	private List<Object> job;

	@SerializedName("postPlaytube")
	private String postPlaytube;

	@SerializedName("videoViews")
	private String videoViews;

	@SerializedName("Orginaltext")
	private String orginaltext;

	@SerializedName("is_still_live")
	private boolean isStillLive;

	@SerializedName("multi_image_post")
	private String multiImagePost;

	@SerializedName("admin")
	private boolean admin;

	@SerializedName("fund_data")
	private List<Object> fundData;

	@SerializedName("recipient_exists")
	private boolean recipientExists;

	@SerializedName("postListening")
	private String postListening;

	@SerializedName("poll_id")
	private String pollId;

	@SerializedName("album_name")
	private String albumName;

	@SerializedName("post_likes")
	private String postLikes;

	@SerializedName("postFacebook")
	private String postFacebook;

	@SerializedName("postLinkTitle")
	private String postLinkTitle;

	@SerializedName("postRecord")
	private String postRecord;

	@SerializedName("postFile_full")
	private String postFileFull;

	@SerializedName("thread")
	private List<Object> thread;

	@SerializedName("postPrivacy")
	private String postPrivacy;

	@SerializedName("offer_id")
	private String offerId;

	@SerializedName("shared_from")
	private boolean sharedFrom;

	@SerializedName("forum")
	private List<Object> forum;

	@SerializedName("postText")
	private String postText;

	@SerializedName("get_post_comments")
	private List<postComments> getPostComments;

	@SerializedName("color_id")
	private String colorId;

	@SerializedName("group_id")
	private String groupId;

	@SerializedName("fund")
	private List<Object> fund;

	@SerializedName("job_id")
	private String jobId;

	@SerializedName("postPhoto")
	private String postPhoto;

	@SerializedName("recipient")
	private Object recipient;

	@SerializedName("time")
	private String time;

	@SerializedName("via_type")
	private String viaType;

	@SerializedName("page_info")
	private List<Object> pageInfo;
	
	public post () {
	
	}
	
	public post (String adMedia, String gender, String bidding, String description, String appears, String posted, String pageId, String end, String id, String headline, String views, String budget, String audience, Object sharedInfo, String postType, boolean isOwner, String spent, String start, Object userData, String url, String userId, String name, String clicks, com.soshoplus.timeline.models.postsfeed.publisherInfo publisherInfo, String location, String status, String postFeeling, String postDailymotion, String postLinkImage, String postFileName, String postSoundCloud, String pageEventId, String registered, List<Object> offer, boolean isPostSaved, String streamName, String postPlaying, String productId, List<Object> options, boolean isWondered, String cache, String postFile, boolean groupAdmin, String active, String liveEnded, List<Object> photoAlbum, List<photoMulti> photoMulti, String postMap, boolean isPostPinned, String postDeepsound, boolean isPostReported, Object agoraResourceId, String commentsStatus, String postFileThumb, int postIsPromoted, String postTextAPI, Object postSticker, int isPostBoosted, String postWatching, String blur, String fundId, boolean isGroupPost, String forumId, String postVimeo, String boosted, boolean groupRecipientExists, String recipientId, boolean isLiked, String agoraSid, String blogId, boolean limitedComments, String liveTime, String postWonders, String postShare, String postComments, String questionId, String postTime, String eventId, Object postUrl, String postVine, String fundRaiseId, int limitComments, String multiImage, com.soshoplus.timeline.models.postsfeed.blog blog, int votedId, int liveSubUsers, String threadId, String postLink, String postLinkContent, String postYoutube, String postShares, String postId, String parentId, String postTraveling, int page, List<Object> job, String postPlaytube, String videoViews, String orginaltext, boolean isStillLive, String multiImagePost, boolean admin, List<Object> fundData, boolean recipientExists, String postListening, String pollId, String albumName, String postLikes, String postFacebook, String postLinkTitle, String postRecord, String postFileFull, List<Object> thread, String postPrivacy, String offerId, boolean sharedFrom, List<Object> forum, String postText, List<com.soshoplus.timeline.models.postsfeed.postComments> getPostComments, String colorId, String groupId, List<Object> fund, String jobId, String postPhoto, Object recipient, String time, String viaType, List<Object> pageInfo) {
		this.adMedia = adMedia;
		this.gender = gender;
		this.bidding = bidding;
		this.description = description;
		this.appears = appears;
		this.posted = posted;
		this.pageId = pageId;
		this.end = end;
		this.id = id;
		this.headline = headline;
		this.views = views;
		this.budget = budget;
		this.audience = audience;
		this.sharedInfo = sharedInfo;
		this.postType = postType;
		this.isOwner = isOwner;
		this.spent = spent;
		this.start = start;
		this.userData = userData;
		this.url = url;
		this.userId = userId;
		this.name = name;
		this.clicks = clicks;
		this.publisherInfo = publisherInfo;
		this.location = location;
		this.status = status;
		this.postFeeling = postFeeling;
		this.postDailymotion = postDailymotion;
		this.postLinkImage = postLinkImage;
		this.postFileName = postFileName;
		this.postSoundCloud = postSoundCloud;
		this.pageEventId = pageEventId;
		this.registered = registered;
		this.offer = offer;
		this.isPostSaved = isPostSaved;
		this.streamName = streamName;
		this.postPlaying = postPlaying;
		this.productId = productId;
		this.options = options;
		this.isWondered = isWondered;
		this.cache = cache;
		this.postFile = postFile;
		this.groupAdmin = groupAdmin;
		this.active = active;
		this.liveEnded = liveEnded;
		this.photoAlbum = photoAlbum;
		this.photoMulti = photoMulti;
		this.postMap = postMap;
		this.isPostPinned = isPostPinned;
		this.postDeepsound = postDeepsound;
		this.isPostReported = isPostReported;
		this.agoraResourceId = agoraResourceId;
		this.commentsStatus = commentsStatus;
		this.postFileThumb = postFileThumb;
		this.postIsPromoted = postIsPromoted;
		this.postTextAPI = postTextAPI;
		this.postSticker = postSticker;
		this.isPostBoosted = isPostBoosted;
		this.postWatching = postWatching;
		this.blur = blur;
		this.fundId = fundId;
		this.isGroupPost = isGroupPost;
		this.forumId = forumId;
		this.postVimeo = postVimeo;
		this.boosted = boosted;
		this.groupRecipientExists = groupRecipientExists;
		this.recipientId = recipientId;
		this.isLiked = isLiked;
		this.agoraSid = agoraSid;
		this.blogId = blogId;
		this.limitedComments = limitedComments;
		this.liveTime = liveTime;
		this.postWonders = postWonders;
		this.postShare = postShare;
		this.postComments = postComments;
		this.questionId = questionId;
		this.postTime = postTime;
		this.eventId = eventId;
		this.postUrl = postUrl;
		this.postVine = postVine;
		this.fundRaiseId = fundRaiseId;
		this.limitComments = limitComments;
		this.multiImage = multiImage;
		this.blog = blog;
		this.votedId = votedId;
		this.liveSubUsers = liveSubUsers;
		this.threadId = threadId;
		this.postLink = postLink;
		this.postLinkContent = postLinkContent;
		this.postYoutube = postYoutube;
		this.postShares = postShares;
		this.postId = postId;
		this.parentId = parentId;
		this.postTraveling = postTraveling;
		this.page = page;
		this.job = job;
		this.postPlaytube = postPlaytube;
		this.videoViews = videoViews;
		this.orginaltext = orginaltext;
		this.isStillLive = isStillLive;
		this.multiImagePost = multiImagePost;
		this.admin = admin;
		this.fundData = fundData;
		this.recipientExists = recipientExists;
		this.postListening = postListening;
		this.pollId = pollId;
		this.albumName = albumName;
		this.postLikes = postLikes;
		this.postFacebook = postFacebook;
		this.postLinkTitle = postLinkTitle;
		this.postRecord = postRecord;
		this.postFileFull = postFileFull;
		this.thread = thread;
		this.postPrivacy = postPrivacy;
		this.offerId = offerId;
		this.sharedFrom = sharedFrom;
		this.forum = forum;
		this.postText = postText;
		this.getPostComments = getPostComments;
		this.colorId = colorId;
		this.groupId = groupId;
		this.fund = fund;
		this.jobId = jobId;
		this.postPhoto = postPhoto;
		this.recipient = recipient;
		this.time = time;
		this.viaType = viaType;
		this.pageInfo = pageInfo;
	}
	
	public String getAdMedia () {
		return adMedia;
	}
	
	public void setAdMedia (String adMedia) {
		this.adMedia = adMedia;
	}
	
	public String getGender () {
		return gender;
	}
	
	public void setGender (String gender) {
		this.gender = gender;
	}
	
	public String getBidding () {
		return bidding;
	}
	
	public void setBidding (String bidding) {
		this.bidding = bidding;
	}
	
	public String getDescription () {
		return description;
	}
	
	public void setDescription (String description) {
		this.description = description;
	}
	
	public String getAppears () {
		return appears;
	}
	
	public void setAppears (String appears) {
		this.appears = appears;
	}
	
	public String getPosted () {
		return posted;
	}
	
	public void setPosted (String posted) {
		this.posted = posted;
	}
	
	public String getPageId () {
		return pageId;
	}
	
	public void setPageId (String pageId) {
		this.pageId = pageId;
	}
	
	public String getEnd () {
		return end;
	}
	
	public void setEnd (String end) {
		this.end = end;
	}
	
	public String getId () {
		return id;
	}
	
	public void setId (String id) {
		this.id = id;
	}
	
	public String getHeadline () {
		return headline;
	}
	
	public void setHeadline (String headline) {
		this.headline = headline;
	}
	
	public String getViews () {
		return views;
	}
	
	public void setViews (String views) {
		this.views = views;
	}
	
	public String getBudget () {
		return budget;
	}
	
	public void setBudget (String budget) {
		this.budget = budget;
	}
	
	public String getAudience () {
		return audience;
	}
	
	public void setAudience (String audience) {
		this.audience = audience;
	}
	
	public Object getSharedInfo () {
		return sharedInfo;
	}
	
	public void setSharedInfo (Object sharedInfo) {
		this.sharedInfo = sharedInfo;
	}
	
	public String getPostType () {
		return postType;
	}
	
	public void setPostType (String postType) {
		this.postType = postType;
	}
	
	public boolean isOwner () {
		return isOwner;
	}
	
	public void setOwner (boolean owner) {
		isOwner = owner;
	}
	
	public String getSpent () {
		return spent;
	}
	
	public void setSpent (String spent) {
		this.spent = spent;
	}
	
	public String getStart () {
		return start;
	}
	
	public void setStart (String start) {
		this.start = start;
	}
	
	public Object getUserData () {
		return userData;
	}
	
	public void setUserData (Object userData) {
		this.userData = userData;
	}
	
	public String getUrl () {
		return url;
	}
	
	public void setUrl (String url) {
		this.url = url;
	}
	
	public String getUserId () {
		return userId;
	}
	
	public void setUserId (String userId) {
		this.userId = userId;
	}
	
	public String getName () {
		return name;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public String getClicks () {
		return clicks;
	}
	
	public void setClicks (String clicks) {
		this.clicks = clicks;
	}
	
	public publisherInfo getPublisherInfo () {
		return publisherInfo;
	}
	
	public void setPublisherInfo (publisherInfo publisherInfo) {
		this.publisherInfo = publisherInfo;
	}
	
	public String getLocation () {
		return location;
	}
	
	public void setLocation (String location) {
		this.location = location;
	}
	
	public String getStatus () {
		return status;
	}
	
	public void setStatus (String status) {
		this.status = status;
	}
	
	public String getPostFeeling () {
		return postFeeling;
	}
	
	public void setPostFeeling (String postFeeling) {
		this.postFeeling = postFeeling;
	}
	
	public String getPostDailymotion () {
		return postDailymotion;
	}
	
	public void setPostDailymotion (String postDailymotion) {
		this.postDailymotion = postDailymotion;
	}
	
	public String getPostLinkImage () {
		return postLinkImage;
	}
	
	public void setPostLinkImage (String postLinkImage) {
		this.postLinkImage = postLinkImage;
	}
	
	public String getPostFileName () {
		return postFileName;
	}
	
	public void setPostFileName (String postFileName) {
		this.postFileName = postFileName;
	}
	
	public String getPostSoundCloud () {
		return postSoundCloud;
	}
	
	public void setPostSoundCloud (String postSoundCloud) {
		this.postSoundCloud = postSoundCloud;
	}
	
	public String getPageEventId () {
		return pageEventId;
	}
	
	public void setPageEventId (String pageEventId) {
		this.pageEventId = pageEventId;
	}
	
	public String getRegistered () {
		return registered;
	}
	
	public void setRegistered (String registered) {
		this.registered = registered;
	}
	
	public List<Object> getOffer () {
		return offer;
	}
	
	public void setOffer (List<Object> offer) {
		this.offer = offer;
	}
	
	public boolean isPostSaved () {
		return isPostSaved;
	}
	
	public void setPostSaved (boolean postSaved) {
		isPostSaved = postSaved;
	}
	
	public String getStreamName () {
		return streamName;
	}
	
	public void setStreamName (String streamName) {
		this.streamName = streamName;
	}
	
	public String getPostPlaying () {
		return postPlaying;
	}
	
	public void setPostPlaying (String postPlaying) {
		this.postPlaying = postPlaying;
	}
	
	public String getProductId () {
		return productId;
	}
	
	public void setProductId (String productId) {
		this.productId = productId;
	}
	
	public List<Object> getOptions () {
		return options;
	}
	
	public void setOptions (List<Object> options) {
		this.options = options;
	}
	
	public boolean isWondered () {
		return isWondered;
	}
	
	public void setWondered (boolean wondered) {
		isWondered = wondered;
	}
	
	public String getCache () {
		return cache;
	}
	
	public void setCache (String cache) {
		this.cache = cache;
	}
	
	public String getPostFile () {
		return postFile;
	}
	
	public void setPostFile (String postFile) {
		this.postFile = postFile;
	}
	
	public boolean isGroupAdmin () {
		return groupAdmin;
	}
	
	public void setGroupAdmin (boolean groupAdmin) {
		this.groupAdmin = groupAdmin;
	}
	
	public String getActive () {
		return active;
	}
	
	public void setActive (String active) {
		this.active = active;
	}
	
	public String getLiveEnded () {
		return liveEnded;
	}
	
	public void setLiveEnded (String liveEnded) {
		this.liveEnded = liveEnded;
	}
	
	public List<Object> getPhotoAlbum () {
		return photoAlbum;
	}
	
	public void setPhotoAlbum (List<Object> photoAlbum) {
		this.photoAlbum = photoAlbum;
	}
	
	public String getPostMap () {
		return postMap;
	}
	
	public void setPostMap (String postMap) {
		this.postMap = postMap;
	}
	
	public boolean isPostPinned () {
		return isPostPinned;
	}
	
	public void setPostPinned (boolean postPinned) {
		isPostPinned = postPinned;
	}
	
	public String getPostDeepsound () {
		return postDeepsound;
	}
	
	public void setPostDeepsound (String postDeepsound) {
		this.postDeepsound = postDeepsound;
	}
	
	public boolean isPostReported () {
		return isPostReported;
	}
	
	public void setPostReported (boolean postReported) {
		isPostReported = postReported;
	}
	
	public Object getAgoraResourceId () {
		return agoraResourceId;
	}
	
	public void setAgoraResourceId (Object agoraResourceId) {
		this.agoraResourceId = agoraResourceId;
	}
	
	public String getCommentsStatus () {
		return commentsStatus;
	}
	
	public void setCommentsStatus (String commentsStatus) {
		this.commentsStatus = commentsStatus;
	}
	
	public String getPostFileThumb () {
		return postFileThumb;
	}
	
	public void setPostFileThumb (String postFileThumb) {
		this.postFileThumb = postFileThumb;
	}
	
	public int getPostIsPromoted () {
		return postIsPromoted;
	}
	
	public void setPostIsPromoted (int postIsPromoted) {
		this.postIsPromoted = postIsPromoted;
	}
	
	public String getPostTextAPI () {
		return postTextAPI;
	}
	
	public void setPostTextAPI (String postTextAPI) {
		this.postTextAPI = postTextAPI;
	}
	
	public Object getPostSticker () {
		return postSticker;
	}
	
	public void setPostSticker (Object postSticker) {
		this.postSticker = postSticker;
	}
	
	public int getIsPostBoosted () {
		return isPostBoosted;
	}
	
	public void setIsPostBoosted (int isPostBoosted) {
		this.isPostBoosted = isPostBoosted;
	}
	
	public String getPostWatching () {
		return postWatching;
	}
	
	public void setPostWatching (String postWatching) {
		this.postWatching = postWatching;
	}
	
	public String getBlur () {
		return blur;
	}
	
	public void setBlur (String blur) {
		this.blur = blur;
	}
	
	public String getFundId () {
		return fundId;
	}
	
	public void setFundId (String fundId) {
		this.fundId = fundId;
	}
	
	public boolean isGroupPost () {
		return isGroupPost;
	}
	
	public void setGroupPost (boolean groupPost) {
		isGroupPost = groupPost;
	}
	
	public String getForumId () {
		return forumId;
	}
	
	public void setForumId (String forumId) {
		this.forumId = forumId;
	}
	
	public String getPostVimeo () {
		return postVimeo;
	}
	
	public void setPostVimeo (String postVimeo) {
		this.postVimeo = postVimeo;
	}
	
	public String getBoosted () {
		return boosted;
	}
	
	public void setBoosted (String boosted) {
		this.boosted = boosted;
	}
	
	public boolean isGroupRecipientExists () {
		return groupRecipientExists;
	}
	
	public void setGroupRecipientExists (boolean groupRecipientExists) {
		this.groupRecipientExists = groupRecipientExists;
	}
	
	public String getRecipientId () {
		return recipientId;
	}
	
	public void setRecipientId (String recipientId) {
		this.recipientId = recipientId;
	}
	
	public boolean isLiked () {
		return isLiked;
	}
	
	public void setLiked (boolean liked) {
		isLiked = liked;
	}
	
	public String getAgoraSid () {
		return agoraSid;
	}
	
	public void setAgoraSid (String agoraSid) {
		this.agoraSid = agoraSid;
	}
	
	public String getBlogId () {
		return blogId;
	}
	
	public void setBlogId (String blogId) {
		this.blogId = blogId;
	}
	
	public boolean isLimitedComments () {
		return limitedComments;
	}
	
	public void setLimitedComments (boolean limitedComments) {
		this.limitedComments = limitedComments;
	}
	
	public String getLiveTime () {
		return liveTime;
	}
	
	public void setLiveTime (String liveTime) {
		this.liveTime = liveTime;
	}
	
	public String getPostWonders () {
		return postWonders;
	}
	
	public void setPostWonders (String postWonders) {
		this.postWonders = postWonders;
	}
	
	public String getPostShare () {
		return postShare;
	}
	
	public void setPostShare (String postShare) {
		this.postShare = postShare;
	}
	
	public String getPostComments () {
		return postComments;
	}
	
	public void setPostComments (String postComments) {
		this.postComments = postComments;
	}
	
	public String getQuestionId () {
		return questionId;
	}
	
	public void setQuestionId (String questionId) {
		this.questionId = questionId;
	}
	
	public String getPostTime () {
		return postTime;
	}
	
	public void setPostTime (String postTime) {
		this.postTime = postTime;
	}
	
	public String getEventId () {
		return eventId;
	}
	
	public void setEventId (String eventId) {
		this.eventId = eventId;
	}
	
	public Object getPostUrl () {
		return postUrl;
	}
	
	public void setPostUrl (Object postUrl) {
		this.postUrl = postUrl;
	}
	
	public String getPostVine () {
		return postVine;
	}
	
	public void setPostVine (String postVine) {
		this.postVine = postVine;
	}
	
	public String getFundRaiseId () {
		return fundRaiseId;
	}
	
	public void setFundRaiseId (String fundRaiseId) {
		this.fundRaiseId = fundRaiseId;
	}
	
	public int getLimitComments () {
		return limitComments;
	}
	
	public void setLimitComments (int limitComments) {
		this.limitComments = limitComments;
	}
	
	public String getMultiImage () {
		return multiImage;
	}
	
	public void setMultiImage (String multiImage) {
		this.multiImage = multiImage;
	}
	
	public blog getBlog () {
		return blog;
	}
	
	public void setBlog (blog blog) {
		this.blog = blog;
	}
	
	public int getVotedId () {
		return votedId;
	}
	
	public void setVotedId (int votedId) {
		this.votedId = votedId;
	}
	
	public int getLiveSubUsers () {
		return liveSubUsers;
	}
	
	public void setLiveSubUsers (int liveSubUsers) {
		this.liveSubUsers = liveSubUsers;
	}
	
	public String getThreadId () {
		return threadId;
	}
	
	public void setThreadId (String threadId) {
		this.threadId = threadId;
	}
	
	public String getPostLink () {
		return postLink;
	}
	
	public void setPostLink (String postLink) {
		this.postLink = postLink;
	}
	
	public String getPostLinkContent () {
		return postLinkContent;
	}
	
	public void setPostLinkContent (String postLinkContent) {
		this.postLinkContent = postLinkContent;
	}
	
	public String getPostYoutube () {
		return postYoutube;
	}
	
	public void setPostYoutube (String postYoutube) {
		this.postYoutube = postYoutube;
	}
	
	public String getPostShares () {
		return postShares;
	}
	
	public void setPostShares (String postShares) {
		this.postShares = postShares;
	}
	
	public String getPostId () {
		return postId;
	}
	
	public void setPostId (String postId) {
		this.postId = postId;
	}
	
	public String getParentId () {
		return parentId;
	}
	
	public void setParentId (String parentId) {
		this.parentId = parentId;
	}
	
	public String getPostTraveling () {
		return postTraveling;
	}
	
	public void setPostTraveling (String postTraveling) {
		this.postTraveling = postTraveling;
	}
	
	public int getPage () {
		return page;
	}
	
	public void setPage (int page) {
		this.page = page;
	}
	
	public List<Object> getJob () {
		return job;
	}
	
	public void setJob (List<Object> job) {
		this.job = job;
	}
	
	public String getPostPlaytube () {
		return postPlaytube;
	}
	
	public void setPostPlaytube (String postPlaytube) {
		this.postPlaytube = postPlaytube;
	}
	
	public String getVideoViews () {
		return videoViews;
	}
	
	public void setVideoViews (String videoViews) {
		this.videoViews = videoViews;
	}
	
	public String getOrginaltext () {
		return orginaltext;
	}
	
	public void setOrginaltext (String orginaltext) {
		this.orginaltext = orginaltext;
	}
	
	public boolean isStillLive () {
		return isStillLive;
	}
	
	public void setStillLive (boolean stillLive) {
		isStillLive = stillLive;
	}
	
	public String getMultiImagePost () {
		return multiImagePost;
	}
	
	public void setMultiImagePost (String multiImagePost) {
		this.multiImagePost = multiImagePost;
	}
	
	public boolean isAdmin () {
		return admin;
	}
	
	public void setAdmin (boolean admin) {
		this.admin = admin;
	}
	
	public List<Object> getFundData () {
		return fundData;
	}
	
	public void setFundData (List<Object> fundData) {
		this.fundData = fundData;
	}
	
	public boolean isRecipientExists () {
		return recipientExists;
	}
	
	public void setRecipientExists (boolean recipientExists) {
		this.recipientExists = recipientExists;
	}
	
	public String getPostListening () {
		return postListening;
	}
	
	public void setPostListening (String postListening) {
		this.postListening = postListening;
	}
	
	public String getPollId () {
		return pollId;
	}
	
	public void setPollId (String pollId) {
		this.pollId = pollId;
	}
	
	public String getAlbumName () {
		return albumName;
	}
	
	public void setAlbumName (String albumName) {
		this.albumName = albumName;
	}
	
	public String getPostLikes () {
		return postLikes;
	}
	
	public void setPostLikes (String postLikes) {
		this.postLikes = postLikes;
	}
	
	public String getPostFacebook () {
		return postFacebook;
	}
	
	public void setPostFacebook (String postFacebook) {
		this.postFacebook = postFacebook;
	}
	
	public String getPostLinkTitle () {
		return postLinkTitle;
	}
	
	public void setPostLinkTitle (String postLinkTitle) {
		this.postLinkTitle = postLinkTitle;
	}
	
	public String getPostRecord () {
		return postRecord;
	}
	
	public void setPostRecord (String postRecord) {
		this.postRecord = postRecord;
	}
	
	public String getPostFileFull () {
		return postFileFull;
	}
	
	public void setPostFileFull (String postFileFull) {
		this.postFileFull = postFileFull;
	}
	
	public List<Object> getThread () {
		return thread;
	}
	
	public void setThread (List<Object> thread) {
		this.thread = thread;
	}
	
	public String getPostPrivacy () {
		return postPrivacy;
	}
	
	public void setPostPrivacy (String postPrivacy) {
		this.postPrivacy = postPrivacy;
	}
	
	public String getOfferId () {
		return offerId;
	}
	
	public void setOfferId (String offerId) {
		this.offerId = offerId;
	}
	
	public boolean isSharedFrom () {
		return sharedFrom;
	}
	
	public void setSharedFrom (boolean sharedFrom) {
		this.sharedFrom = sharedFrom;
	}
	
	public List<Object> getForum () {
		return forum;
	}
	
	public void setForum (List<Object> forum) {
		this.forum = forum;
	}
	
	public String getPostText () {
		return postText;
	}
	
	public void setPostText (String postText) {
		this.postText = postText;
	}
	
	public List<com.soshoplus.timeline.models.postsfeed.postComments> getGetPostComments () {
		return getPostComments;
	}
	
	public void setGetPostComments (List<com.soshoplus.timeline.models.postsfeed.postComments> getPostComments) {
		this.getPostComments = getPostComments;
	}
	
	public String getColorId () {
		return colorId;
	}
	
	public void setColorId (String colorId) {
		this.colorId = colorId;
	}
	
	public String getGroupId () {
		return groupId;
	}
	
	public void setGroupId (String groupId) {
		this.groupId = groupId;
	}
	
	public List<Object> getFund () {
		return fund;
	}
	
	public void setFund (List<Object> fund) {
		this.fund = fund;
	}
	
	public String getJobId () {
		return jobId;
	}
	
	public void setJobId (String jobId) {
		this.jobId = jobId;
	}
	
	public String getPostPhoto () {
		return postPhoto;
	}
	
	public void setPostPhoto (String postPhoto) {
		this.postPhoto = postPhoto;
	}
	
	public Object getRecipient () {
		return recipient;
	}
	
	public void setRecipient (Object recipient) {
		this.recipient = recipient;
	}
	
	public String getTime () {
		return time;
	}
	
	public void setTime (String time) {
		this.time = time;
	}
	
	public String getViaType () {
		return viaType;
	}
	
	public void setViaType (String viaType) {
		this.viaType = viaType;
	}
	
	public List<Object> getPageInfo () {
		return pageInfo;
	}
	
	public void setPageInfo (List<Object> pageInfo) {
		this.pageInfo = pageInfo;
	}
	
	public List<photoMulti> getPhotoMulti () {
		return photoMulti;
	}
	
	public void setPhotoMulti (List<photoMulti> photoMulti) {
		this.photoMulti = photoMulti;
	}
}