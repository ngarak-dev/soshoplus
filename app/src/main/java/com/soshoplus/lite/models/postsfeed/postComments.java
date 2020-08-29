/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.models.postsfeed;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class postComments implements Serializable {

	@SerializedName("comment_wonders")
	private String commentWonders;

	@SerializedName("c_file")
	private String cFile;

	@SerializedName("is_comment_liked")
	private boolean isCommentLiked;

	@SerializedName("Orginaltext")
	private String orginaltext;

	@SerializedName("fullurl")
	private String fullurl;

	@SerializedName("onwer")
	private boolean onwer;

	@SerializedName("is_comment_wondered")
	private boolean isCommentWondered;

	@SerializedName("url")
	private String url;

	@SerializedName("page_id")
	private String pageId;

	@SerializedName("post_onwer")
	private boolean postOnwer;

	@SerializedName("post_id")
	private String postId;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("record")
	private String record;

	@SerializedName("comment_likes")
	private String commentLikes;

	@SerializedName("publisher")
	private publisherInfo publisherInfo;

	@SerializedName("id")
	private String id;

	@SerializedName("text")
	private String text;

	@SerializedName("time")
	private String time;

	@SerializedName("replies")
	private String replies;
	
	public postComments () {
	
	}
	
	public postComments(String commentWonders, String cFile, boolean isCommentLiked, String orginaltext, String fullurl, boolean onwer, boolean isCommentWondered, String url, String pageId, boolean postOnwer, String postId, String userId, String record, String commentLikes, com.soshoplus.lite.models.postsfeed.publisherInfo publisherInfo, String id, String text, String time, String replies) {
		this.commentWonders = commentWonders;
		this.cFile = cFile;
		this.isCommentLiked = isCommentLiked;
		this.orginaltext = orginaltext;
		this.fullurl = fullurl;
		this.onwer = onwer;
		this.isCommentWondered = isCommentWondered;
		this.url = url;
		this.pageId = pageId;
		this.postOnwer = postOnwer;
		this.postId = postId;
		this.userId = userId;
		this.record = record;
		this.commentLikes = commentLikes;
		this.publisherInfo = publisherInfo;
		this.id = id;
		this.text = text;
		this.time = time;
		this.replies = replies;
	}
	
	public String getCommentWonders () {
		return commentWonders;
	}
	
	public void setCommentWonders (String commentWonders) {
		this.commentWonders = commentWonders;
	}
	
	public String getcFile () {
		return cFile;
	}
	
	public void setcFile (String cFile) {
		this.cFile = cFile;
	}
	
	public boolean isCommentLiked () {
		return isCommentLiked;
	}
	
	public void setCommentLiked (boolean commentLiked) {
		isCommentLiked = commentLiked;
	}
	
	public String getOrginaltext () {
		return orginaltext;
	}
	
	public void setOrginaltext (String orginaltext) {
		this.orginaltext = orginaltext;
	}
	
	public String getFullurl () {
		return fullurl;
	}
	
	public void setFullurl (String fullurl) {
		this.fullurl = fullurl;
	}
	
	public boolean isOnwer () {
		return onwer;
	}
	
	public void setOnwer (boolean onwer) {
		this.onwer = onwer;
	}
	
	public boolean isCommentWondered () {
		return isCommentWondered;
	}
	
	public void setCommentWondered (boolean commentWondered) {
		isCommentWondered = commentWondered;
	}
	
	public String getUrl () {
		return url;
	}
	
	public void setUrl (String url) {
		this.url = url;
	}
	
	public String getPageId () {
		return pageId;
	}
	
	public void setPageId (String pageId) {
		this.pageId = pageId;
	}
	
	public boolean isPostOnwer () {
		return postOnwer;
	}
	
	public void setPostOnwer (boolean postOnwer) {
		this.postOnwer = postOnwer;
	}
	
	public String getPostId () {
		return postId;
	}
	
	public void setPostId (String postId) {
		this.postId = postId;
	}
	
	public String getUserId () {
		return userId;
	}
	
	public void setUserId (String userId) {
		this.userId = userId;
	}
	
	public String getRecord () {
		return record;
	}
	
	public void setRecord (String record) {
		this.record = record;
	}
	
	public String getCommentLikes () {
		return commentLikes;
	}
	
	public void setCommentLikes (String commentLikes) {
		this.commentLikes = commentLikes;
	}
	
	public com.soshoplus.lite.models.postsfeed.publisherInfo getPublisherInfo () {
		return publisherInfo;
	}
	
	public void setPublisherInfo (com.soshoplus.lite.models.postsfeed.publisherInfo publisherInfo) {
		this.publisherInfo = publisherInfo;
	}
	
	public String getId () {
		return id;
	}
	
	public void setId (String id) {
		this.id = id;
	}
	
	public String getText () {
		return text;
	}
	
	public void setText (String text) {
		this.text = text;
	}
	
	public String getTime () {
		return time;
	}
	
	public void setTime (String time) {
		this.time = time;
	}

	public String getReplies() {
		return replies;
	}

	public void setReplies(String replies) {
		this.replies = replies;
	}
}