package com.fuadhev.task3.data.network.api

import com.fuadhev.task3.common.utils.Constants.API_KEY
import com.fuadhev.task3.data.network.dto.NewsDTO
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {


    @GET("top-headlines")
    suspend fun getTopNews(
        @Query("language") lang: String="en",
        @Query("apiKey") apiKey: String = API_KEY
    ):NewsDTO

    @GET("everything")
    suspend fun searchNews(
        @Query("language") lang: String,
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = API_KEY
    ):NewsDTO
}