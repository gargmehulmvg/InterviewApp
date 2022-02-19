package com.mehul.interviewapplication.apis

import com.mehul.interviewapplication.model.QuoteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IAppService {

    @GET("quotes")
    suspend fun getAllQuotes(@Query("page") pageNumber: Int): Response<QuoteResponse>

}