package com.mehul.interviewapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehul.interviewapplication.model.QuoteResponse
import com.mehul.interviewapplication.model.ResultsItemResponse
import com.mehul.interviewapplication.repository.QuoteRepository
import kotlinx.coroutines.launch

class MainViewModel(private val mRepository: QuoteRepository): ViewModel() {

    val mQuotesList: LiveData<ArrayList<ResultsItemResponse>>
    get() = mRepository.mQuotes

    val mTotalQuotesPageNumber: LiveData<Int>
    get() = mRepository.mTotalQuotesPageSize

    val mLocalQuotesList: LiveData<ArrayList<ResultsItemResponse>>
        get() = mRepository.mLocalQuotes

    fun getQuotes(pageNumber: Int) {
        viewModelScope.launch {
            mRepository.getQuotes(pageNumber)
        }
    }

    fun getLocalQuotes() {
        viewModelScope.launch {
            mRepository.getLocalQuotes()
        }
    }

}