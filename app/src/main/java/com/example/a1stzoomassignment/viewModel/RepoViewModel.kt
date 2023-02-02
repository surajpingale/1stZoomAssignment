package com.example.a1stzoomassignment.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1stzoomassignment.R
import com.example.a1stzoomassignment.Utils.NetworkAvailability
import com.example.a1stzoomassignment.Utils.Resource
import com.example.a1stzoomassignment.model.dataclasses.Repository
import com.example.a1stzoomassignment.model.remote_db.dataclasses.ApiResponse
import com.example.a1stzoomassignment.model.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
    private val app: Application
) : ViewModel() {

    private var _repository: MutableLiveData<Resource<ApiResponse>> = MutableLiveData()

    val repository: LiveData<Resource<ApiResponse>>
        get() = _repository

    private var _dataInserted: MutableLiveData<Boolean> = MutableLiveData()

    val dataInserted: LiveData<Boolean>
        get() = _dataInserted



    fun getRepository(ownerName: String, repositoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (NetworkAvailability.isNetworkAvailable(app)) {
                    val apiResult = repoRepository.getRepository(ownerName, repositoryName)
                    _repository.postValue(apiResult)
                } else {
                    _repository.postValue(Resource.Error("Network Not Available"))
                }
            } catch (exception: Exception) {
                _repository.postValue(Resource.Error(exception.message.toString()))
            }
        }
    }

    fun insertRepositoryToLocalDb(repository: Repository) {
        _dataInserted.postValue(false)
        viewModelScope.launch(Dispatchers.IO) {
            repoRepository.insertRepository(repository)
            _dataInserted.postValue(true)
        }
    }

    fun validateOwnerName(ownerName: String): Pair<Boolean, String> {
        return if (ownerName.isEmpty()) {
            Pair(
                false, app.applicationContext.resources.getString(
                    R.string.error_owner_name
                )
            )

        } else {
            Pair(true, "")
        }
    }

    fun validateRepositoryName(repositoryName: String): Pair<Boolean, String> {
        return if (repositoryName.isEmpty()) {
            Pair(
                false, app.applicationContext.resources.getString(
                    R.string.error_repository_name
                )
            )
        } else {
            Pair(true, "")
        }
    }

}