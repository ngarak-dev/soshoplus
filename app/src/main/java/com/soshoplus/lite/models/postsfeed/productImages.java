/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.models.postsfeed;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class productImages implements Serializable {

	@SerializedName("image")
	private String image;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("id")
	private String id;

	@SerializedName("image_org")
	private String imageOrg;
	
	public productImages () {
	
	}
	
	public productImages (String image, String productId, String id, String imageOrg) {
		this.image = image;
		this.productId = productId;
		this.id = id;
		this.imageOrg = imageOrg;
	}
	
	public String getImage () {
		return image;
	}
	
	public void setImage (String image) {
		this.image = image;
	}
	
	public String getProductId () {
		return productId;
	}
	
	public void setProductId (String productId) {
		this.productId = productId;
	}
	
	public String getId () {
		return id;
	}
	
	public void setId (String id) {
		this.id = id;
	}
	
	public String getImageOrg () {
		return imageOrg;
	}
	
	public void setImageOrg (String imageOrg) {
		this.imageOrg = imageOrg;
	}
}