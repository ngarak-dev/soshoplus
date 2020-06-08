package com.soshoplus.timeline.models.postsfeed;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class blog implements Serializable {

	@SerializedName("shared")
	private String shared;

	@SerializedName("thumbnail")
	private String thumbnail;

	@SerializedName("category_name")
	private String categoryName;

	@SerializedName("author")
	private authorInfo authorInfo;

	@SerializedName("description")
	private String description;

	@SerializedName("active")
	private String active;

	@SerializedName("title")
	private String title;

	@SerializedName("content")
	private String content;

	@SerializedName("url")
	private String url;

	@SerializedName("posted")
	private String posted;

	@SerializedName("tags")
	private String tags;

	@SerializedName("tags_array")
	private List<String> tagsArray;

	@SerializedName("view")
	private String view;

	@SerializedName("category_link")
	private String categoryLink;

	@SerializedName("is_post_admin")
	private boolean isPostAdmin;

	@SerializedName("id")
	private String id;

	@SerializedName("category")
	private String category;

	@SerializedName("user")
	private String user;
	
	public blog () {
	
	}
	
	public blog (String shared, String thumbnail, String categoryName, com.soshoplus.timeline.models.postsfeed.authorInfo authorInfo, String description, String active, String title, String content, String url, String posted, String tags, List<String> tagsArray, String view, String categoryLink, boolean isPostAdmin, String id, String category, String user) {
		this.shared = shared;
		this.thumbnail = thumbnail;
		this.categoryName = categoryName;
		this.authorInfo = authorInfo;
		this.description = description;
		this.active = active;
		this.title = title;
		this.content = content;
		this.url = url;
		this.posted = posted;
		this.tags = tags;
		this.tagsArray = tagsArray;
		this.view = view;
		this.categoryLink = categoryLink;
		this.isPostAdmin = isPostAdmin;
		this.id = id;
		this.category = category;
		this.user = user;
	}
	
	public String getShared () {
		return shared;
	}
	
	public void setShared (String shared) {
		this.shared = shared;
	}
	
	public String getThumbnail () {
		return thumbnail;
	}
	
	public void setThumbnail (String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public String getCategoryName () {
		return categoryName;
	}
	
	public void setCategoryName (String categoryName) {
		this.categoryName = categoryName;
	}
	
	public com.soshoplus.timeline.models.postsfeed.authorInfo getAuthorInfo () {
		return authorInfo;
	}
	
	public void setAuthorInfo (com.soshoplus.timeline.models.postsfeed.authorInfo authorInfo) {
		this.authorInfo = authorInfo;
	}
	
	public String getDescription () {
		return description;
	}
	
	public void setDescription (String description) {
		this.description = description;
	}
	
	public String getActive () {
		return active;
	}
	
	public void setActive (String active) {
		this.active = active;
	}
	
	public String getTitle () {
		return title;
	}
	
	public void setTitle (String title) {
		this.title = title;
	}
	
	public String getContent () {
		return content;
	}
	
	public void setContent (String content) {
		this.content = content;
	}
	
	public String getUrl () {
		return url;
	}
	
	public void setUrl (String url) {
		this.url = url;
	}
	
	public String getPosted () {
		return posted;
	}
	
	public void setPosted (String posted) {
		this.posted = posted;
	}
	
	public String getTags () {
		return tags;
	}
	
	public void setTags (String tags) {
		this.tags = tags;
	}
	
	public List<String> getTagsArray () {
		return tagsArray;
	}
	
	public void setTagsArray (List<String> tagsArray) {
		this.tagsArray = tagsArray;
	}
	
	public String getView () {
		return view;
	}
	
	public void setView (String view) {
		this.view = view;
	}
	
	public String getCategoryLink () {
		return categoryLink;
	}
	
	public void setCategoryLink (String categoryLink) {
		this.categoryLink = categoryLink;
	}
	
	public boolean isPostAdmin () {
		return isPostAdmin;
	}
	
	public void setPostAdmin (boolean postAdmin) {
		isPostAdmin = postAdmin;
	}
	
	public String getId () {
		return id;
	}
	
	public void setId (String id) {
		this.id = id;
	}
	
	public String getCategory () {
		return category;
	}
	
	public void setCategory (String category) {
		this.category = category;
	}
	
	public String getUser () {
		return user;
	}
	
	public void setUser (String user) {
		this.user = user;
	}
}