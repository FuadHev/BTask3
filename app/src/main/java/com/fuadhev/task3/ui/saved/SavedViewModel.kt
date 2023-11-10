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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(private val repo: NewsRepository):ViewModel() {


    private val _savedState = MutableLiveData<SavedUiState>()
    val savedState: LiveData<SavedUiState> get() = _savedState

    init {
        getSaves()
    }

    fun getSaves(){
        viewModelScope.launch {
            repo.getSaves().collectLatest {
                when(it){
                    is Resource.Loading->{  _savedState.value=SavedUiState.Loading }
                    is Resource.Success->{   it.data?.let { list-> _savedState.value=SavedUiState.SuccessSavedData(list.toNewUiModelL())
                        Log.e("saves", "$list" )}}
                    is Resource.Error->{  _savedState.value=SavedUiState.Error(it.exception)  }
                }
            }
        }
    }


}