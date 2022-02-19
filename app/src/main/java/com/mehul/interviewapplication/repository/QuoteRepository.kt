package com.mehul.interviewapplication.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mehul.interviewapplication.apis.IAppService
import com.mehul.interviewapplication.db.QuoteDataBase
import com.mehul.interviewapplication.interfaces.IQuotesDaoResponse
import com.mehul.interviewapplication.model.QuoteResponse
import com.mehul.interviewapplication.model.ResultsItemResponse
import com.mehul.interviewapplication.model.ReviewResponse
import retrofit2.Response

class QuoteRepository(private val mAppService:IAppService, private val mAppDataBaseService: QuoteDataBase) {

    private val mQuotesLiveData = MutableLiveData<ArrayList<ResultsItemResponse>>()
    private val mLocalQuotesLiveData = MutableLiveData<ArrayList<ResultsItemResponse>>()
    private val mTotalQuotesPageSizeLiveData = MutableLiveData<Int>()

    val mQuotes: LiveData<ArrayList<ResultsItemResponse>>
    get() = mQuotesLiveData

    val mLocalQuotes: LiveData<ArrayList<ResultsItemResponse>>
    get() = mLocalQuotesLiveData

    val mTotalQuotesPageSize: LiveData<Int>
    get() = mTotalQuotesPageSizeLiveData

    suspend fun getQuotes(pageNumber: Int = 1) {
        val result: Response<QuoteResponse> = mAppService.getAllQuotes(pageNumber)
        result.body()?.let { body ->
            body.results.let { list -> mAppDataBaseService.getQuotesDao().insertQuote(list) }
            val quotesList: ArrayList<ResultsItemResponse> = ArrayList()
            quotesList.addAll(body.results)
            mQuotesLiveData.postValue(quotesList)
            mTotalQuotesPageSizeLiveData.postValue(result.body()?.totalPages ?: 1)
        }
    }

    suspend fun getLocalQuotes() {
        val list = mAppDataBaseService.getQuotesDao().getAllQuote()
        val quotesList: ArrayList<ResultsItemResponse> = ArrayList()
        quotesList.addAll(list)
        mLocalQuotesLiveData.postValue(quotesList)
    }

    suspend fun getQuoteReviewById(id: String, daoItemResponse: IQuotesDaoResponse) {
        Log.d("MainActivity", "getQuoteReviewById: id :: $id")
        val reviewResponse = mAppDataBaseService.getQuotesDao().getQuoteReviewById(id)
        daoItemResponse.onQuotesReviewResponse(reviewResponse, id)
    }

    suspend fun insertQuoteReview(review: ReviewResponse, daoItemResponse: IQuotesDaoResponse) {
        mAppDataBaseService.getQuotesDao().insertQuoteReview(review)
        daoItemResponse.onInsertQuotesReviewResponse()
    }

}