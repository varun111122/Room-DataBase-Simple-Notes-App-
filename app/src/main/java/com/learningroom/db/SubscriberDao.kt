package com.learningroom.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDao {

    @Insert
    suspend fun addSubscriber(subscriber: Subscriber) :Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber) :Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber) :Int

    @Query("DELETE FROM mySubscriber_table")
    suspend fun deleteAllSubscriber() :Int

    @Query("SELECT * FROM mySubscriber_table")
     fun getListOfSubscriber(): LiveData<List<Subscriber>>

}