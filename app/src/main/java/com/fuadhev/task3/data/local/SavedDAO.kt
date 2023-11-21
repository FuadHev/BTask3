package com.fuadhev.task3.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.fuadhev.task3.data.local.dto.SavedDTO
import com.fuadhev.task3.domain.model.NewsUiModel

@Dao
interface SavedDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSaves(newsUiModel: SavedDTO)

    @Delete
    fun deleteNews(newsUiModel: SavedDTO)

    @Query("SELECT * FROM saved_news")
    fun getSavedNews(): List<SavedDTO>

    @Query("SELECT EXISTS (SELECT 1 FROM saved_news WHERE title = :title)")
    suspend fun isNewsSaved(title: String): Boolean
}