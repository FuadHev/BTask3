package com.fuadhev.task3.data.network.dto

data class NewsDTO(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)