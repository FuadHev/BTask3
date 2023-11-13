package com.fuadhev.task3.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.task3.common.utils.Resource
import com.fuadhev.task3.data.local.dto.SavedDTO
import com.fuadhev.task3.domain.repository.NewsRepository
import com.fuadhev.task3.ui.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(private val repo: NewsRepository) : ViewModel() {

    private val _detailState = MutableLiveData<DetailUiState?>()
    val detailState: LiveData<DetailUiState?> get() = _detailState


    fun addSaves(savedDTO: SavedDTO) {
       val job= viewModelScope.launch {
            repo.addSaves(savedDTO).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _detailState.value = DetailUiState.Loading
                    }

                    is Resource.Success -> {
                        _detailState.value = DetailUiState.SuccessSaveNews("News Saved")
                    }

                    is Resource.Error -> {
                        _detailState.value = DetailUiState.Error(it.exception)
                    }
                }
            }
        }
        job.cancel()

    }

    fun deleteSaves(savedDTO: SavedDTO) {
        viewModelScope.launch {
            repo.deleteNews(savedDTO).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _detailState.value = DetailUiState.Loading
                    }

                    is Resource.Success -> {
                        _detailState.value = DetailUiState.SuccessDeleteSave("News Deleted")
                    }

                    is Resource.Error -> {
                        _detailState.value = DetailUiState.Error(it.exception)
                    }
                }
            }
        }

    }

    fun isNewsSaved(title: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            repo.isNewsSaved(title).collectLatest {

                callback(it)

            }
        }

    }
    fun resetData(){
        _detailState.value=null
    }

}