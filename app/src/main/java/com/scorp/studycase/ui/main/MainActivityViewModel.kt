package com.scorp.studycase.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scorp.studycase.data.DataSource
import com.scorp.studycase.data.FetchCompletionHandler
import com.scorp.studycase.data.Person
import com.scorp.studycase.util.Errors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(val dataSource: DataSource) : ViewModel() {
    @Inject
    lateinit var errors: Errors
    var people: MutableLiveData<List<Person>?> = MutableLiveData()
    var errorState: MutableLiveData<Int> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var next: String? = null

    init {
        fetchData()
    }

    private var fetchCompletionHandler: FetchCompletionHandler = { fetchResponse, fetchError ->
        if (fetchResponse != null) {
            next = fetchResponse.next

            if (fetchResponse.people.isNotEmpty()) {
                if (people.value.isNullOrEmpty()) {
                    people.postValue(fetchResponse.people.distinctBy { it.id })
                } else {
                    val updatedList = people.value?.plus(fetchResponse.people)?.distinctBy { it.id }
                    if (updatedList!!.size == people.value!!.size) {
                        errorState.postValue(errors.getErrorMessage(errors.NO_NEW_DATA_ERROR))
                    }
                    people.postValue(updatedList)
                }
            } else {
                errorState.postValue(errors.getErrorMessage(errors.NO_DATA_ERROR))
            }

        }
        if (fetchError != null) {
            errorState.postValue(errors.getErrorMessage(fetchError.errorCode))
        }
        isLoading.postValue(false)
    }

    fun fetchData() {
        if (!isLoading.value!!) {
            isLoading.postValue(true)
            viewModelScope.launch(Dispatchers.IO) {
                dataSource.fetch(next, fetchCompletionHandler)
            }
        }
    }

    fun refreshPage() {
        people.value = null
        fetchData()
    }
}