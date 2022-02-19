package com.mehul.interviewapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mehul.interviewapplication.model.ResultsItemResponse
import com.mehul.interviewapplication.model.ReviewResponse

@Dao
interface QuotesDao {

    @Insert
    suspend fun insertQuote(quoteList: List<ResultsItemResponse>)

    @Query("SELECT * FROM quote")
    suspend fun getAllQuote(): List<ResultsItemResponse>

    @Query("SELECT * FROM reviews WHERE post_id=:postId")
    suspend fun getQuoteReviewById(postId: String): ReviewResponse?

}