package com.fuadhev.task3.ui.saved

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.task3.common.utils.Resource
import com.fuadhev.task3.domain.mapper.Mapper.toNewUiModelL
import com.fuadhev.task3.domain.mapper.Mapper.toNewUiModelList
import com.fuadhev.task3.domain.repository.NewsRepository
import com.fuadhev.task3.ui.detail.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(private val repo: NewsRepository):ViewModel() {


    private val _savedState = MutableStateFlow<SavedUiState>(SavedUiState.Loading)
    val savedState get() = _savedState.asStateFlow()





    fun getSaves(){

        repo.getSaves().onEach {
            when(it){
                is Resource.Loading->{  _savedState.value=SavedUiState.Loading }
                is Resource.Success->{   it.data?.let { list-> _savedState.value=SavedUiState.SuccessSavedData(list.toNewUiModelL())
                    Log.e("saves", "$list" )}}
                is Resource.Error->{  _savedState.value=SavedUiState.Error(it.exception)  }
            }
        }.launchIn(viewModelScope)
        
    }


}