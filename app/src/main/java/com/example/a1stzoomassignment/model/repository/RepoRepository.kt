package com.example.a1stzoomassignment.model.repository

import androidx.lifecycle.LiveData
import com.example.a1stzoomassignment.Utils.Resource
import com.example.a1stzoomassignment.model.dataclasses.Repository
import com.example.a1stzoomassignment.model.remote_db.ApiService
import com.example.a1stzoomassignment.model.remote_db.dataclasses.ApiResponse
import com.example.a1stzoomassignment.model.room.RepositoryDao
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject


class RepoRepository @Inject constructor(
    private val apiService: ApiService,
    private val repositoryDao: RepositoryDao
) {

    suspend fun getRepository(ownerName: String, repositoryName: String): Resource<ApiResponse> {
        return responseToResource(apiService.getRepository(ownerName, repositoryName))
    }

    suspend fun insertRepository(repository: Repository) {
        repositoryDao.insertRepository(repository)
    }

    private fun responseToResource(response: Response<ApiResponse>): Resource<ApiResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    fun getRepositoryListFromLocalDb(): Flow<List<Repository>> {
        return repositoryDao.getAllRepository()
    }

}