package com.amar.peoplelistapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amar.peoplelistapp.model.PersonData
import com.amar.peoplelistapp.repository.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val repository: PeopleRepository
): ViewModel() {

    private val _peopleList = MutableStateFlow(listOf<PersonData>())
    val peopleList = _peopleList.asStateFlow()
    private val list: ArrayList<PersonData> = arrayListOf()

    private val page = MutableStateFlow(1)
    private val _hasMore = MutableStateFlow(true)
    val hasMore = _hasMore.asStateFlow()

    private val _showProgress = MutableStateFlow(false)
    val showProgress = _showProgress.asStateFlow()

    fun getPeopleListData() {
        if (!_hasMore.value) return
        viewModelScope.launch(Dispatchers.IO) {
            _showProgress.value = true
            val newData = repository.getPeople(page.value)
            if (newData.size < 20) _hasMore.value = false
            list.addAll(newData)
            _peopleList.value = list.toList()
            _showProgress.value = false
        }
    }

    fun loadMore() {
        page.value = page.value + 1
        getPeopleListData()
    }

    fun setLiked(id: Int, liked: Boolean) {
        viewModelScope.launch {
            repository.setLiked(id, liked)
        }
    }


}