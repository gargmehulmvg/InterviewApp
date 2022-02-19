package com.mehul.interviewapplication.interfaces

import com.mehul.interviewapplication.model.ReviewResponse

interface IQuotesDaoResponse {

    fun onQuotesReviewResponse(reviewItem: ReviewResponse?)

}