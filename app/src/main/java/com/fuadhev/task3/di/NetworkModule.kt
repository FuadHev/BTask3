package com.fuadhev.task3.di

import com.fuadhev.task3.common.utils.Constants.BASE_URL
import com.fuadhev.task3.data.network.api.NewsApiService
import com.fuadhev.task3.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        GsonConverterFactory.create()).build()


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) = retrofit.create(NewsApiService::class.java)

    @Singleton
    @Provides
    fun provideAuthRepository(apiService:NewsApiService): NewsRepository  = NewsRepository(apiService)

}