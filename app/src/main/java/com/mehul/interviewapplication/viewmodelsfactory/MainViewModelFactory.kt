package com.mehul.interviewapplication.viewmodelsfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mehul.interviewapplication.repository.QuoteRepository
import com.mehul.interviewapplication.viewmodels.MainViewModel

class MainViewModelFactory(private val mRepository: QuoteRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(mRepository) as T
    }

}