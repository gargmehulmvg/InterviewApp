package com.mehul.interviewapplication.model

import com.google.gson.annotations.SerializedName

data class QuoteResponse(

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("totalPages")
	val totalPages: Int? = null,

	@field:SerializedName("lastItemIndex")
	val lastItemIndex: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("totalCount")
	val totalCount: Int? = null,

	@field:SerializedName("results")
	val results: ArrayList<ResultsItem?>? = null
)

data class ResultsItem(

	@field:SerializedName("authorSlug")
	val authorSlug: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("length")
	val length: Int? = null,

	@field:SerializedName("dateModified")
	val dateModified: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("dateAdded")
	val dateAdded: String? = null,

	@field:SerializedName("tags")
	val tags: List<String?>? = null
)
