package com.fuadhev.task3.domain.repository

import com.fuadhev.task3.common.utils.Resource
import com.fuadhev.task3.data.local.SavedDAO
import com.fuadhev.task3.data.local.SavedDB
import com.fuadhev.task3.data.local.dto.SavedDTO
import com.fuadhev.task3.data.network.api.NewsApiService
import com.fuadhev.task3.data.network.dto.Article
import com.fuadhev.task3.data.network.dto.NewsDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiService: NewsApiService,private val savedDao:SavedDAO) {

    fun getTopNews(lang:String): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading)
        val response=apiService.getTopNews(lang)
        if (response.isSuccessful) {
            val list = response.body()?.articles
            emit(Resource.Success(list))
        }else{
            emit(Resource.Error("Error 404"))
        }


    }.catch {

        emit(Resource.Error(it.localizedMessage?:"Error 404"))

    }

    fun searchNews(lang: String,query:String): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading)
        val response=apiService.searchNews(lang,query)
        if (response.isSuccessful){
            val list=response.body()?.articles
            emit(Resource.Success(list))
        }else{
            emit(Resource.Error("Error 404"))

        }


    }.catch {
        emit(Resource.Error(it.localizedMessage?:"Error 404"))
    }


    fun getSaves(): Flow<Resource<List<SavedDTO>>> = flow {
        emit(Resource.Loading)
        val response = savedDao.getSavedNews()
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Error 404"))
    }.flowOn(Dispatchers.IO)

    fun isNewsSaved(title: String) : Flow<Boolean> = flow {
        val response = savedDao.isNewsSaved(title)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun addSaves(savedDto:SavedDTO):Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        savedDao.addSaves(savedDto)
        emit(Resource.Success(true))
    }.catch {
        emit(Resource.Error(it.localizedMessage?:"Error 404"))
    }.flowOn(Dispatchers.IO)

    fun deleteNews(savedDto:SavedDTO):Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        savedDao.deleteNews(savedDto)
        emit(Resource.Success(true))
    }.catch {
        emit(Resource.Error(it.localizedMessage?:"Error 404"))
    }.flowOn(Dispatchers.IO)
}