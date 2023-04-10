package com.learningroom

import androidx.room.Dao
import com.learningroom.db.Subscriber
import com.learningroom.db.SubscriberDao

class MainRepository(private val dao: SubscriberDao) {

    val allSubscriber = dao.getListOfSubscriber()

    suspend fun addSubscriber(subscriber: Subscriber) :Long {
       return dao.addSubscriber(subscriber)
    }

    suspend fun updateSubscriber(subscriber: Subscriber):Int {
      return  dao.updateSubscriber(subscriber)
    }


    suspend fun deleteSubscriber(subscriber: Subscriber):Int {
       return dao.deleteSubscriber(subscriber)
    }


    suspend fun deleteAllSubscriber() :Int {
      return  dao.deleteAllSubscriber()
    }


}