package com.fuadhev.task3.data.local

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.fuadhev.task3.data.local.dto.SavedDTO

@Database(entities = [SavedDTO::class], version = 1,exportSchema = true)
abstract class SavedDB:RoomDatabase() {

    abstract fun getSavedDao():SavedDAO

}