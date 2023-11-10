package com.fuadhev.task3.domain.mapper

import com.fuadhev.task3.data.local.dto.SavedDTO
import com.fuadhev.task3.data.network.dto.Article
import com.fuadhev.task3.data.network.dto.NewsDTO
import com.fuadhev.task3.domain.model.NewsUiModel

object Mapper {

    fun Article.toNewsUiModel() = NewsUiModel(
        author,
        content,
        description,
        publishedAt,
        title,
        url,
        urlToImage
    )

    fun List<Article>.toNewUiModelList()=map {
        NewsUiModel(
            it.author?:"Anonym",
            it.content,
            it.description,
            it.publishedAt,
            it.title,
            it.url,
            it.urlToImage
        )
    }

    fun NewsUiModel.toSavedDTO() = SavedDTO(
        author?:"unknown",
        content?:"unknown",
        description?:"unknown",
        publishedAt?:"unknown",
        title?:"unknown",
        url?:"unknown",
        urlToImage?:"unknown"
    )

    fun List<SavedDTO>.toNewUiModelL()=map {
        NewsUiModel(
            it.author?:"unknown",
            it.content?:"unknown",
            it.description?:"unknown",
            it.publishedAt?:"unknown",
            it.title?:"unknown",
            it.url?:"unknown",
            it.urlToImage?:"unknown"
        )
    }

    fun SavedDTO.toNewsUiModel() = NewsUiModel(
        author?:"unknown",
        content?:"unknown",
        description?:"unknown",
        publishedAt?:"unknown",
        title?:"unknown",
        url?:"unknown",
        urlToImage?:"unknown"
    )
}