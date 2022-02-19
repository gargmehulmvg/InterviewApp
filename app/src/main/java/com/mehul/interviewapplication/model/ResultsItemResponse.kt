package com.mehul.interviewapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "quote")
data class ResultsItemResponse(

    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("authorSlug")
    val authorSlug: String? = null,

    @field:SerializedName("_id")
    val postId: String? = null,

    @field:SerializedName("author")
    val author: String? = null,

    @field:SerializedName("content")
    val content: String? = null,

    @field:SerializedName("review")
    val review: String? = ""
)