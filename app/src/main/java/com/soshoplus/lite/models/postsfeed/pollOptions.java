/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.models.postsfeed;

import com.google.gson.annotations.SerializedName;

public class pollOptions {

	@SerializedName("all")
	private int all;

	@SerializedName("option_votes")
	private String optionVotes;

	@SerializedName("post_id")
	private String postId;

	@SerializedName("percentage")
	private String percentage;

	@SerializedName("percentage_num")
	private String percentageNum;

	@SerializedName("id")
	private String id;

	@SerializedName("text")
	private String text;

	@SerializedName("time")
	private String time;
}