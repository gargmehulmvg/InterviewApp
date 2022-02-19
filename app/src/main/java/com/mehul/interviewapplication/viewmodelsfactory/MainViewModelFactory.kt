package com.mehul.interviewapplication.viewmodelsfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mehul.interviewapplication.interfaces.IQuotesDaoResponse
import com.mehul.interviewapplication.repository.QuoteRepository
import com.mehul.interviewapplication.viewmodels.MainViewModel

class MainViewModelFactory(
    private val mRepository: QuoteRepository,
    private val mDaoItemResponse: IQuotesDaoResponse
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(mRepository, mDaoItemResponse) as T
    }

}