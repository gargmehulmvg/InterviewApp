package com.mehul.interviewapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.mehul.interviewapplication.R
import com.mehul.interviewapplication.databinding.QuotesItemBinding
import com.mehul.interviewapplication.interfaces.IAdapterItemClickListener
import com.mehul.interviewapplication.model.ResultsItemResponse

class QuotesAdapter(
    private var mContext: Context?,
    private var mList: ArrayList<ResultsItemResponse?>?,
    private var mListener: IAdapterItemClickListener
) : RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder>() {

    inner class QuotesViewHolder(val viewDataBinding: QuotesItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root)

    private var mLastUpdatedPosition = -1

    fun updateDataSource(list: ArrayList<ResultsItemResponse?>?) {
        this.mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        val binding = QuotesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuotesViewHolder(binding)
    }

    override fun getItemCount(): Int = mList?.size ?: 0

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        holder.viewDataBinding.apply {
            if (holder.adapterPosition > mLastUpdatedPosition) {
                val animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row_animation)
                imageView.startAnimation(animation)
            }
            itemClickListener = mListener
            adapterPosition = position
            authorName = mList?.get(position)?.author ?: ""
            content = mList?.get(position)?.content ?: ""
            mLastUpdatedPosition = holder.adapterPosition
        }
    }

}