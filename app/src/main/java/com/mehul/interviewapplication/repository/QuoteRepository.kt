package com.mehul.interviewapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mehul.interviewapplication.apis.IAppService
import com.mehul.interviewapplication.model.QuoteResponse
import retrofit2.Response

class QuoteRepository(private val mAppService:IAppService) {

    private val mQuotesLiveData = MutableLiveData<QuoteResponse>()

    val mQuotes: LiveData<QuoteResponse>
    get() = mQuotesLiveData

    suspend fun getQuotes(pageNumber: Int = 1) {
        val result: Response<QuoteResponse> = mAppService.getAllQuotes(pageNumber)
        result.body()?.let { body ->
            mQuotesLiveData.postValue(body)
        }
    }

}