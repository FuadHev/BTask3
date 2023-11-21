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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo:NewsRepository,private val sp:SharedPrefManager) : ViewModel() {



    private val _homeState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeState get() = _homeState.asStateFlow()

    private val _selectedLanguage = MutableStateFlow("en")
    val selectedLanguage get() = _selectedLanguage.asStateFlow()


    fun getLanguage():String{
        val lang=sp.getLang()?:"en"
        _selectedLanguage.value = lang
        return lang
    }
    fun getTopNews(lang:String){

        repo.getTopNews(lang).onEach {
            when(it){
                is Resource.Loading -> {   _homeState.value=HomeUiState.Loading  }
                is Resource.Success -> {  _homeState.value = it.data?.let { it1 ->
                    HomeUiState.SuccessTopNewsData(
                        it1.toNewUiModelList()
                    )
                } ?: HomeUiState.Error("Melumat tapilmadi")
                }
                is Resource.Error -> { _homeState.value=  HomeUiState.Error(it.exception)  }
            }
        }.launchIn(viewModelScope)
    }



    @OptIn(FlowPreview::class)
    fun searchNews(lang: String, query:String){

        repo.searchNews(lang,query).debounce(400).onEach {
            when(it){
                is Resource.Loading -> {   _homeState.value=HomeUiState.Loading  }
                is Resource.Success -> {  _homeState.value= it.data?.let { it1 ->
                    HomeUiState.SuccessSearchData(
                        it1.toNewUiModelList()
                    )
                } ?: HomeUiState.Error("Melumat tapilmadi")
                }
                is Resource.Error -> { _homeState.value=  HomeUiState.Error(it.exception)  }

            }
        }.launchIn(viewModelScope)


    }
}