package com.example.a1stzoomassignment.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.a1stzoomassignment.model.dataclasses.Repository
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {

    @Insert
    suspend fun insertRepository(repository: Repository)

    @Query("SELECT * FROM repository_db")
    fun getAllRepository() : Flow<List<Repository>>
}