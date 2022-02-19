package com.mehul.interviewapplication.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
class ReviewResponse {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "post_id")
    @NonNull
    var postId: String = ""

    @ColumnInfo(name = "review")
    var review: String? = ""

}