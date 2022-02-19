package com.mehul.interviewapplication.model

import com.google.gson.annotations.SerializedName

data class PostResponse(

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("body")
    val body: String
)