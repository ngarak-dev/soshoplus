/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.models.friends.suggested;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class suggestedDetails implements Serializable {

	@SerializedName("likes_count")
	private String likesCount;

	@SerializedName("album_count")
	private String albumCount;

	@SerializedName("following_count")
	private String followingCount;

	@SerializedName("mutual_friends_count")
	private boolean mutualFriendsCount;

	@SerializedName("followers_count")
	private String followersCount;

	@SerializedName("groups_count")
	private String groupsCount;

	@SerializedName("post_count")
	private String postCount;
	
	public suggestedDetails () {
	
	}
	
	public suggestedDetails (String likesCount, String albumCount, String followingCount, boolean mutualFriendsCount, String followersCount, String groupsCount, String postCount) {
		this.likesCount = likesCount;
		this.albumCount = albumCount;
		this.followingCount = followingCount;
		this.mutualFriendsCount = mutualFriendsCount;
		this.followersCount = followersCount;
		this.groupsCount = groupsCount;
		this.postCount = postCount;
	}
	
	public String getLikesCount () {
		return likesCount;
	}
	
	public void setLikesCount (String likesCount) {
		this.likesCount = likesCount;
	}
	
	public String getAlbumCount () {
		return albumCount;
	}
	
	public void setAlbumCount (String albumCount) {
		this.albumCount = albumCount;
	}
	
	public String getFollowingCount () {
		return followingCount;
	}
	
	public void setFollowingCount (String followingCount) {
		this.followingCount = followingCount;
	}
	
	public boolean isMutualFriendsCount () {
		return mutualFriendsCount;
	}
	
	public void setMutualFriendsCount (boolean mutualFriendsCount) {
		this.mutualFriendsCount = mutualFriendsCount;
	}
	
	public String getFollowersCount () {
		return followersCount;
	}
	
	public void setFollowersCount (String followersCount) {
		this.followersCount = followersCount;
	}
	
	public String getGroupsCount () {
		return groupsCount;
	}
	
	public void setGroupsCount (String groupsCount) {
		this.groupsCount = groupsCount;
	}
	
	public String getPostCount () {
		return postCount;
	}
	
	public void setPostCount (String postCount) {
		this.postCount = postCount;
	}
}