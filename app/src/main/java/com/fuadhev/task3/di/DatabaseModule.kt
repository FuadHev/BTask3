package com.fuadhev.task3.di

import android.content.Context
import androidx.room.Room
import com.fuadhev.task3.data.local.SavedDAO
import com.fuadhev.task3.data.local.SavedDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): SavedDB =
        Room.databaseBuilder(
            context,
            SavedDB::class.java,
            "SavedNewsDB"
        ).build()

    @Singleton
    @Provides
    fun provideFavDao(db: SavedDB): SavedDAO = db.getSavedDao()


}