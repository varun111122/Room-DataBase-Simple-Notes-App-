package com.learningroom

import androidx.lifecycle.*
import com.learningroom.db.Subscriber
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private var repository: MainRepository) : ViewModel() {

    var allSubscriber = repository.allSubscriber

    private var isUpdateOrDelete = false
    private lateinit var deleteOrUpdateSubscriber: Subscriber

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    var handleEvent = MutableLiveData<Event<String>>()

    val eventState: LiveData<Event<String>>
        get() = handleEvent

    var saveOrUpdateBtnText = MutableLiveData<String>()
    var deleteOrClearAllBtnText = MutableLiveData<String>()


    init {
        saveOrUpdateBtnText.value = "Save"
        deleteOrClearAllBtnText.value = "Clear All"
    }

    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            deleteOrUpdateSubscriber.name = inputName.value.toString()
            deleteOrUpdateSubscriber.email = inputEmail.value.toString()
            updateSubscriber(deleteOrUpdateSubscriber)
        } else {
            val name = inputName.value ?: ""
            val email = inputEmail.value ?: ""
            insertSubscriber(Subscriber(0, name, email))
            inputName.value = ""
            inputEmail.value = ""
        }
    }

    fun clearAllOrDeleteAll() {
        if (isUpdateOrDelete) {
            deleteSubscriber(deleteOrUpdateSubscriber)
        } else {
            clearAllSubscriber()

        }
    }

    private fun insertSubscriber(subscriber: Subscriber) = viewModelScope.launch(IO) {
        val insertAtRow = repository.addSubscriber(subscriber)
        withContext(Main) {
            handleEvent.value = Event("Inserted in ${insertAtRow}")
        }

    }

    private fun updateSubscriber(subscriber: Subscriber) = viewModelScope.launch(IO) {
        val updatedRow = repository.updateSubscriber(deleteOrUpdateSubscriber)
        withContext(Main) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateBtnText.value = "Save"
            deleteOrClearAllBtnText.value = "Clear All"
            handleEvent.value = Event("Updated in $updatedRow")
        }
    }

    private fun deleteSubscriber(subscriber: Subscriber) = viewModelScope.launch(IO) {
        val deleteRow = repository.deleteSubscriber(subscriber)
        withContext(Main) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateBtnText.value = "Save"
            deleteOrClearAllBtnText.value = "Clear All"
            handleEvent.value = Event("Delete in $deleteRow")

        }
    }

    private fun clearAllSubscriber() = viewModelScope.launch(IO) {
        val noOfDeletedRow = repository.deleteAllSubscriber()
        withContext(Main) {
            handleEvent.value = Event("Delete in $noOfDeletedRow")

        }
    }

    fun handleDeleteOrUpdate(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        deleteOrUpdateSubscriber = subscriber
        saveOrUpdateBtnText.value = "Update"
        deleteOrClearAllBtnText.value = "Delete"
    }

}