/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.models.friends.suggested;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class suggestedDetails implements Serializable {
	
	@SerializedName("post_count")
	@Expose
	private String postCount;
	@SerializedName("album_count")
	@Expose
	private String albumCount;
	@SerializedName("following_count")
	@Expose
	private String followingCount;
	@SerializedName("followers_count")
	@Expose
	private String followersCount;
	@SerializedName("groups_count")
	@Expose
	private String groupsCount;
	@SerializedName("likes_count")
	@Expose
	private String likesCount;
	@SerializedName("mutual_friends_count")
	@Expose
	private boolean mutualFriendsCount;
	private final static long serialVersionUID = -3314139828092032440L;
	
	/**
	 * No args constructor for use in serialization
	 *
	 */
	
	public suggestedDetails () {
	
	}
	
	/**
	 *
	 * @param likesCount
	 * @param groupsCount
	 * @param mutualFriendsCount
	 * @param albumCount
	 * @param postCount
	 * @param followersCount
	 * @param followingCount
	 */
	
	public suggestedDetails (String postCount, String albumCount, String followingCount,
							 String followersCount,
			 String groupsCount, String likesCount, boolean mutualFriendsCount) {
		super();
		this.postCount = postCount;
		this.albumCount = albumCount;
		this.followingCount = followingCount;
		this.followersCount = followersCount;
		this.groupsCount = groupsCount;
		this.likesCount = likesCount;
		this.mutualFriendsCount = mutualFriendsCount;
	}
	
	public String getPostCount() {
		return postCount;
	}
	
	public void setPostCount(String postCount) {
		this.postCount = postCount;
	}
	
	public suggestedDetails withPostCount(String postCount) {
		this.postCount = postCount;
		return this;
	}
	
	public String getAlbumCount() {
		return albumCount;
	}
	
	public void setAlbumCount(String albumCount) {
		this.albumCount = albumCount;
	}
	
	public suggestedDetails withAlbumCount(String albumCount) {
		this.albumCount = albumCount;
		return this;
	}
	
	public String getFollowingCount() {
		return followingCount;
	}
	
	public void setFollowingCount(String followingCount) {
		this.followingCount = followingCount;
	}
	
	public suggestedDetails withFollowingCount(String followingCount) {
		this.followingCount = followingCount;
		return this;
	}
	
	public String getFollowersCount() {
		return followersCount;
	}
	
	public void setFollowersCount(String followersCount) {
		this.followersCount = followersCount;
	}
	
	public suggestedDetails withFollowersCount(String followersCount) {
		this.followersCount = followersCount;
		return this;
	}
	
	public String getGroupsCount() {
		return groupsCount;
	}
	
	public void setGroupsCount(String groupsCount) {
		this.groupsCount = groupsCount;
	}
	
	public suggestedDetails withGroupsCount(String groupsCount) {
		this.groupsCount = groupsCount;
		return this;
	}
	
	public String getLikesCount() {
		return likesCount;
	}
	
	public void setLikesCount(String likesCount) {
		this.likesCount = likesCount;
	}
	
	public suggestedDetails withLikesCount(String likesCount) {
		this.likesCount = likesCount;
		return this;
	}
	
	public boolean isMutualFriendsCount() {
		return mutualFriendsCount;
	}
	
	public void setMutualFriendsCount(boolean mutualFriendsCount) {
		this.mutualFriendsCount = mutualFriendsCount;
	}
	
	public suggestedDetails withMutualFriendsCount(boolean mutualFriendsCount) {
		this.mutualFriendsCount = mutualFriendsCount;
		return this;
	}
}