package com.mehul.interviewapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mehul.interviewapplication.model.ResultsItemResponse
import com.mehul.interviewapplication.model.ReviewResponse

@Database(entities = [ResultsItemResponse::class, ReviewResponse::class], version = 1)
abstract class QuoteDataBase: RoomDatabase() {

    abstract fun getQuotesDao(): QuotesDao

    companion object {

        private var INSTANCE:QuoteDataBase? = null
        private const val DATABASE_NAME = "QuoteDataBase"

        fun getDataBase(context: Context): QuoteDataBase {
            if (null == INSTANCE) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context, QuoteDataBase::class.java, DATABASE_NAME).build()
                }
            }
            return INSTANCE!!
        }

    }

}