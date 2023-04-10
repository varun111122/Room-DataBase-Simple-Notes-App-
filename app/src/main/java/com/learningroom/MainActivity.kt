package com.learningroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.learningroom.databinding.ActivityMainBinding
import com.learningroom.db.Subscriber
import com.learningroom.db.SubscriberDatabase

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    private lateinit var adapterSubscriber: AdapterSubscriber
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(this).dao
        val repository = MainRepository(dao)
        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        binding.mainViewModel = viewModel
        binding.lifecycleOwner = this

        initUI()
    }

    private fun initUI() {
        displayListOfSubscriber()
        eventHandleObserver()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapterSubscriber = AdapterSubscriber() { subscriber ->
            handleDeleteOrUpdate(subscriber)
        }
        binding.recyclerView.adapter = adapterSubscriber

    }

    private fun eventHandleObserver() {
        viewModel.eventState.observe(this, Observer { observe ->
            observe.getContentIfNotHandled().let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayListOfSubscriber() {
        viewModel.allSubscriber.observe(this, Observer {
            adapterSubscriber.setList(it)
            adapterSubscriber.notifyDataSetChanged()
        })
    }

    private fun handleDeleteOrUpdate(subscriber: Subscriber) {
        viewModel.handleDeleteOrUpdate(subscriber)

    }
}