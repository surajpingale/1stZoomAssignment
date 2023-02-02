package com.example.a1stzoomassignment.di

import android.content.Context
import androidx.room.Room
import com.example.a1stzoomassignment.Utils.Constants
import com.example.a1stzoomassignment.model.room.RepoDatabase
import com.example.a1stzoomassignment.model.room.RepositoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context : Context) : RepoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            RepoDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepositoryDao(repoDatabase: RepoDatabase) : RepositoryDao {
        return repoDatabase.repositoryDao()
    }

}