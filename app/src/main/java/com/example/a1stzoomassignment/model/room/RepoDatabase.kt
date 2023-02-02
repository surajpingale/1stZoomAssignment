package com.example.a1stzoomassignment.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.a1stzoomassignment.model.dataclasses.Repository

@Database(entities = [Repository::class], version = 1)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repositoryDao() : RepositoryDao
}