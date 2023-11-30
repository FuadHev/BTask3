package com.fuadhev.task3.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fuadhev.task3.domain.model.NewsUiModel
import org.jetbrains.annotations.NotNull


@Entity("saved_news")
data class SavedDTO (
    val author: String? ,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val url: String?,
    val urlToImage: String?
)