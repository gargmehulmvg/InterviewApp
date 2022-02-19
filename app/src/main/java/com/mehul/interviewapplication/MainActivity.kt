package com.mehul.interviewapplication

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mehul.interviewapplication.adapters.QuotesAdapter
import com.mehul.interviewapplication.apis.IAppService
import com.mehul.interviewapplication.apis.RetrofitHelper
import com.mehul.interviewapplication.databinding.ActivityMainBinding
import com.mehul.interviewapplication.model.ResultsItem
import com.mehul.interviewapplication.repository.QuoteRepository
import com.mehul.interviewapplication.viewmodels.MainViewModel
import com.mehul.interviewapplication.viewmodelsfactory.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mBinding: ActivityMainBinding
    private var mQuotesAdapter: QuotesAdapter? = null
    private var mCurrentPageCount = 1
    private var mTotalPageCount = 0
    private var mProgressDialog: Dialog? = null
    private var mPostResponseList: ArrayList<ResultsItem?>? = ArrayList()

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val quoteService = RetrofitHelper.getInstance().create(IAppService::class.java)
        val repository = QuoteRepository(quoteService)
        mMainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]

        showProgressDialog()

        mMainViewModel.mQuotesList.observe(this, { response ->
            if (1 == mCurrentPageCount) mPostResponseList = ArrayList()
            mTotalPageCount = response.totalPages ?: 1
            mPostResponseList?.addAll(response.results ?: ArrayList())
            mQuotesAdapter?.updateDataSource(mPostResponseList)
            stopProgress()
        })
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            mQuotesAdapter = QuotesAdapter(this@MainActivity, null)
            adapter = mQuotesAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && RecyclerView.SCROLL_STATE_IDLE == newState) {
                        if (mTotalPageCount != mCurrentPageCount) {
                            mCurrentPageCount++
                            showProgressDialog()
                            mMainViewModel.getQuotes(mCurrentPageCount)
                        }
                    }
                }
            })

        }
    }

    private fun showProgressDialog() {
        runOnUiThread {
            try {
                if (null != mProgressDialog && true == mProgressDialog?.isShowing) return@runOnUiThread
                mProgressDialog = Dialog(this)
                mProgressDialog?.apply {
                    val view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
                    setContentView(view)
                    setCancelable(false)
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }?.show()
            } catch (e: Exception) {
                Log.e(TAG, "showProgressDialog: ${e.message}", e)
            }
        }
    }

    private fun stopProgress() {
        runOnUiThread {
            try {
                if (null != mProgressDialog) {
                    mProgressDialog?.dismiss()
                    mProgressDialog = null
                }
            } catch (e: Exception) {
                Log.e(TAG, "stopProgress: ${e.message}", e)
            }
        }
    }

}