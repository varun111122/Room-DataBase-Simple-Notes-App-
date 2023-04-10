package com.learningroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.learningroom.databinding.SubscriberAdapterBinding
import com.learningroom.db.Subscriber

class AdapterSubscriber(private val function: (Subscriber) -> Unit) :
    RecyclerView.Adapter<MyViewHolder>() {
    private val subscriberList = ArrayList<Subscriber>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: SubscriberAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.subscriber_adapter,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscriberList[position], function)
    }

    override fun getItemCount() = subscriberList.size

    fun setList(list: List<Subscriber>) {
        subscriberList.clear()
        subscriberList.addAll(list)
    }

}

class MyViewHolder(private val adapterBinding: SubscriberAdapterBinding) :
    RecyclerView.ViewHolder(adapterBinding.root) {
    fun bind(subscriber: Subscriber, function: (Subscriber) -> Unit) {
        adapterBinding.tvName.text = subscriber.name
        adapterBinding.tvEmail.text = subscriber.email

        adapterBinding.root.setOnClickListener {
            function(subscriber)
        }
    }
}