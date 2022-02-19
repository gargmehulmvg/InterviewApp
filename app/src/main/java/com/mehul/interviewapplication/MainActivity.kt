package com.mehul.interviewapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehul.interviewapplication.adapters.QuotesAdapter
import com.mehul.interviewapplication.apis.IAppService
import com.mehul.interviewapplication.apis.RetrofitHelper
import com.mehul.interviewapplication.databinding.ActivityMainBinding
import com.mehul.interviewapplication.repository.QuoteRepository
import com.mehul.interviewapplication.viewmodels.MainViewModel
import com.mehul.interviewapplication.viewmodelsfactory.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mBinding: ActivityMainBinding
    private var mQuotesAdapter: QuotesAdapter? = null

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val quoteService = RetrofitHelper.getInstance().create(IAppService::class.java)
        val repository = QuoteRepository(quoteService)
        mMainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]

        mMainViewModel.mQuotesList.observe(this, { response ->
            Log.d(TAG, "MEHUL ::\n ${response?.toString()}")
            mQuotesAdapter?.updateDataSource(response.results)
        })
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            mQuotesAdapter = QuotesAdapter(this@MainActivity, null)
            adapter = mQuotesAdapter
        }
    }
}