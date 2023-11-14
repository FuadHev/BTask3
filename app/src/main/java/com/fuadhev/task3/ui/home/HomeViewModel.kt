package com.fuadhev.task3.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.task3.common.utils.Resource
import com.fuadhev.task3.common.utils.SharedPrefManager
import com.fuadhev.task3.domain.mapper.Mapper.toNewUiModelList
import com.fuadhev.task3.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo:NewsRepository,private val sp:SharedPrefManager) : ViewModel() {


    private val _homeState = MutableLiveData<HomeUiState>()
    val homeState: LiveData<HomeUiState> get() = _homeState

    private val _selectedLanguage = MutableLiveData<String>()
    val selectedLanguage: LiveData<String> get() = _selectedLanguage


    fun getLanguage(){
        _selectedLanguage.value = sp.getLang()?:"en"
    }

    fun getTopNews(lang:String){
        viewModelScope.launch {
            repo.getTopNews(lang).collectLatest {

                when(it){
                    is Resource.Loading -> {   _homeState.value=HomeUiState.Loading  }
                    is Resource.Success -> {  _homeState.value= it.data?.let { it1 ->
                        HomeUiState.SuccessTopNewsData(
                            it1.toNewUiModelList()
                        )
                    }
                    }
                    is Resource.Error -> { _homeState.value=  HomeUiState.Error(it.exception)  }

                }

            }
        }
    }


    fun searchNeews(lang: String,query:String){
        viewModelScope.launch {
            repo.searchViews(lang,query).collectLatest {
                when(it){
                    is Resource.Loading -> {   _homeState.value=HomeUiState.Loading  }
                    is Resource.Success -> {  _homeState.value= it.data?.let { it1 ->
                        HomeUiState.SuccessSearchData(
                            it1.toNewUiModelList()
                        )
                    }
                    }
                    is Resource.Error -> { _homeState.value=  HomeUiState.Error(it.exception)  }

                }
            }
        }

    }
}