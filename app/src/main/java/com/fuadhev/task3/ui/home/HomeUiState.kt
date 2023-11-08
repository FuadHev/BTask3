package com.fuadhev.task3.ui.home

import com.fuadhev.task3.data.network.dto.Article
import com.fuadhev.task3.domain.model.NewsUiModel

sealed class HomeUiState{
    object Loading :HomeUiState()
    data class SuccessTopNewsData(val list : List<NewsUiModel>) : HomeUiState()
    data class SuccessSearchData(val list : List<NewsUiModel>) : HomeUiState()
    data class Error(val message : String) : HomeUiState()
}