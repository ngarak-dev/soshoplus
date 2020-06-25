package com.soshoplus.timeline.models.postsfeed;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class product implements Serializable {

	@SerializedName("images")
	private List<productImages> images;

	@SerializedName("lng")
	private String lng;

	@SerializedName("sub_category")
	private String subCategory;

	@SerializedName("description")
	private String description;

	@SerializedName("active")
	private String active;

	@SerializedName("product_sub_category")
	private String productSubCategory;

	@SerializedName("type")
	private String type;

	@SerializedName("url")
	private String url;

	@SerializedName("time_text")
	private String timeText;

	@SerializedName("page_id")
	private String pageId;

	@SerializedName("post_id")
	private String postId;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("location")
	private String location;

	@SerializedName("currency")
	private String currency;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	@SerializedName("category")
	private String category;

	@SerializedName("edit_description")
	private String editDescription;

	@SerializedName("lat")
	private String lat;

	@SerializedName("status")
	private String status;
	
	public product () {
	
	}
	
	public product (List<productImages> images, String lng, String subCategory, String description, String active, String productSubCategory, String type, String url, String timeText, String pageId, String postId, String userId, String price, String name, String location, String currency, String id, String time, String category, String editDescription, String lat, String status) {
		this.images = images;
		this.lng = lng;
		this.subCategory = subCategory;
		this.description = description;
		this.active = active;
		this.productSubCategory = productSubCategory;
		this.type = type;
		this.url = url;
		this.timeText = timeText;
		this.pageId = pageId;
		this.postId = postId;
		this.userId = userId;
		this.price = price;
		this.name = name;
		this.location = location;
		this.currency = currency;
		this.id = id;
		this.time = time;
		this.category = category;
		this.editDescription = editDescription;
		this.lat = lat;
		this.status = status;
	}
	
	public List<productImages> getImages () {
		return images;
	}
	
	public void setImages (List<productImages> images) {
		this.images = images;
	}
	
	public String getLng () {
		return lng;
	}
	
	public void setLng (String lng) {
		this.lng = lng;
	}
	
	public String getSubCategory () {
		return subCategory;
	}
	
	public void setSubCategory (String subCategory) {
		this.subCategory = subCategory;
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
	
	public String getProductSubCategory () {
		return productSubCategory;
	}
	
	public void setProductSubCategory (String productSubCategory) {
		this.productSubCategory = productSubCategory;
	}
	
	public String getType () {
		return type;
	}
	
	public void setType (String type) {
		this.type = type;
	}
	
	public String getUrl () {
		return url;
	}
	
	public void setUrl (String url) {
		this.url = url;
	}
	
	public String getTimeText () {
		return timeText;
	}
	
	public void setTimeText (String timeText) {
		this.timeText = timeText;
	}
	
	public String getPageId () {
		return pageId;
	}
	
	public void setPageId (String pageId) {
		this.pageId = pageId;
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
	
	public String getPrice () {
		return price;
	}
	
	public void setPrice (String price) {
		this.price = price;
	}
	
	public String getName () {
		return name;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public String getLocation () {
		return location;
	}
	
	public void setLocation (String location) {
		this.location = location;
	}
	
	public String getCurrency () {
		return currency;
	}
	
	public void setCurrency (String currency) {
		this.currency = currency;
	}
	
	public String getId () {
		return id;
	}
	
	public void setId (String id) {
		this.id = id;
	}
	
	public String getTime () {
		return time;
	}
	
	public void setTime (String time) {
		this.time = time;
	}
	
	public String getCategory () {
		return category;
	}
	
	public void setCategory (String category) {
		this.category = category;
	}
	
	public String getEditDescription () {
		return editDescription;
	}
	
	public void setEditDescription (String editDescription) {
		this.editDescription = editDescription;
	}
	
	public String getLat () {
		return lat;
	}
	
	public void setLat (String lat) {
		this.lat = lat;
	}
	
	public String getStatus () {
		return status;
	}
	
	public void setStatus (String status) {
		this.status = status;
	}
}