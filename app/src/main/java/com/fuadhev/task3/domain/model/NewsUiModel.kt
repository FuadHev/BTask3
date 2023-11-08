package com.fuadhev.task3.domain.model

import java.io.Serializable


data class NewsUiModel(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
):Serializable