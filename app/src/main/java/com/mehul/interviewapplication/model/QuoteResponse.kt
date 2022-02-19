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
	val results: List<ResultsItemResponse>
)