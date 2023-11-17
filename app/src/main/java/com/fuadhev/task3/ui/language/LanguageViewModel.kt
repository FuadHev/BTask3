package com.fuadhev.task3.ui.language

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fuadhev.task3.common.utils.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(private val sp:SharedPrefManager)  :ViewModel() {

    fun saveLang(lang:String){
        sp.saveLang(lang)

    }


}