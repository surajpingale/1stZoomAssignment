package com.example.a1stzoomassignment.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.a1stzoomassignment.model.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LandingScreenViewModel @Inject constructor(
    private val repoRepository: RepoRepository
) : ViewModel() {

    fun getListOfRepositoryFromLocalDb() = liveData {
        repoRepository.getRepositoryListFromLocalDb().collect {
            emit(it)
        }
    }

}