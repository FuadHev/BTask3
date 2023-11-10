package com.fuadhev.task3.ui.saved

import com.fuadhev.task3.domain.model.NewsUiModel
import com.fuadhev.task3.ui.detail.DetailUiState

sealed class SavedUiState {

    object Loading : SavedUiState()
    data class SuccessSavedData(val list : List<NewsUiModel>) : SavedUiState()
    data class Error(val message: String) : SavedUiState()
}