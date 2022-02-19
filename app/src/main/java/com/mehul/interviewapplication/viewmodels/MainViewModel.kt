package com.mehul.interviewapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehul.interviewapplication.model.QuoteResponse
import com.mehul.interviewapplication.repository.QuoteRepository
import kotlinx.coroutines.launch

class MainViewModel(private val mRepository: QuoteRepository): ViewModel() {

    init {
        viewModelScope.launch {
            mRepository.getQuotes()
        }
    }

    val mQuotesList: LiveData<QuoteResponse>
    get() = mRepository.mQuotes

    fun getQuotes(pageNumber: Int) {
        viewModelScope.launch {
            mRepository.getQuotes(pageNumber)
        }
    }

}