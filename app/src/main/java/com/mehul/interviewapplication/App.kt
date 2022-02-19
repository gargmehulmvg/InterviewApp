package com.mehul.interviewapplication

import android.app.Application
import com.mehul.interviewapplication.apis.IAppService
import com.mehul.interviewapplication.apis.RetrofitHelper
import com.mehul.interviewapplication.db.QuoteDataBase
import com.mehul.interviewapplication.repository.QuoteRepository

class App: Application() {

    lateinit var mQuoteRepository: QuoteRepository

    override fun onCreate() {
        super.onCreate()
        initializeRepository()
    }

    private fun initializeRepository() {
        val quoteService = RetrofitHelper.getInstance().create(IAppService::class.java)
        val database = QuoteDataBase.getDataBase(applicationContext)
        mQuoteRepository = QuoteRepository(quoteService, database)
    }

}