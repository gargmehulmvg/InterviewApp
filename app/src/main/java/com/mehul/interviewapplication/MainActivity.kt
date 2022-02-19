package com.mehul.interviewapplication

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mehul.interviewapplication.adapters.QuotesAdapter
import com.mehul.interviewapplication.constants.isNotEmpty
import com.mehul.interviewapplication.databinding.ActivityMainBinding
import com.mehul.interviewapplication.interfaces.IAdapterItemClickListener
import com.mehul.interviewapplication.interfaces.IQuotesDaoResponse
import com.mehul.interviewapplication.model.ResultsItemResponse
import com.mehul.interviewapplication.model.ReviewResponse
import com.mehul.interviewapplication.viewmodels.MainViewModel
import com.mehul.interviewapplication.viewmodelsfactory.MainViewModelFactory

class MainActivity : AppCompatActivity(), IAdapterItemClickListener, IQuotesDaoResponse {

    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mBinding: ActivityMainBinding
    private var mQuotesAdapter: QuotesAdapter? = null
    private var mCurrentPageCount = 1
    private var mTotalPageCount = 0
    private var mProgressDialog: Dialog? = null
    private var mPostResponseList: ArrayList<ResultsItemResponse?>? = ArrayList()

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val repository = (application as App).mQuoteRepository
        mMainViewModel = ViewModelProvider(this, MainViewModelFactory(repository, this))[MainViewModel::class.java]
        mMainViewModel.mQuotesList.observe(this, { response ->
            Log.d(TAG, "mMainViewModel.mQuotesList: $response")
            if (1 == mCurrentPageCount) mPostResponseList = ArrayList()
            mPostResponseList?.addAll(response ?: ArrayList())
            if (isNotEmpty(mPostResponseList)) mQuotesAdapter?.updateDataSource(mPostResponseList)
            stopProgress()
        })
        mMainViewModel.mTotalQuotesPageNumber.observe(this, { totalPageCount ->
            Log.d(TAG, "mMainViewModel.mTotalQuotesPageNumber: $totalPageCount")
            mTotalPageCount = totalPageCount ?: 1
        })
        mMainViewModel.mLocalQuotesList.observe(this, { localQuotesList ->
            Log.d(TAG, "mMainViewModel.mLocalQuotesList: $localQuotesList")
            mPostResponseList?.addAll(localQuotesList ?: ArrayList())
            if (isNotEmpty(mPostResponseList)) mQuotesAdapter?.updateDataSource(mPostResponseList)
            mMainViewModel.getQuotes(mCurrentPageCount)
        })
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            mQuotesAdapter = QuotesAdapter(this@MainActivity, null, this@MainActivity)
            adapter = mQuotesAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && RecyclerView.SCROLL_STATE_IDLE == newState) {
                        if (mCurrentPageCount < mTotalPageCount) {
                            mCurrentPageCount++
                            showProgressDialog()
                            mMainViewModel.getQuotes(mCurrentPageCount)
                        }
                    }
                }
            })
            mMainViewModel.getLocalQuotes()
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

    override fun onAdapterItemClickListener(position: Int) {
        Log.d(TAG, "onAdapterItemClickListener: position :: $position :: item :: ${mPostResponseList?.get(position)}")
        mMainViewModel.getQuoteReviewById(mPostResponseList?.get(position)?.postId ?: "")
    }

    override fun onQuotesReviewResponse(reviewItem: ReviewResponse?) {
        Log.d(TAG, "onQuotesReviewResponse: reviewItem :: $reviewItem")
    }

}