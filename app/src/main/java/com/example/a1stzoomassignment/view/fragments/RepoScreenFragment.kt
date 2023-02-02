package com.example.a1stzoomassignment.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.a1stzoomassignment.R
import com.example.a1stzoomassignment.Utils.Resource
import com.example.a1stzoomassignment.databinding.FragmentRepoScreenBinding
import com.example.a1stzoomassignment.model.dataclasses.Repository
import com.example.a1stzoomassignment.model.remote_db.dataclasses.ApiResponse
import com.example.a1stzoomassignment.viewModel.RepoViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RepoScreenFragment : Fragment() {

    private var _binding: FragmentRepoScreenBinding? = null

    private val binding
        get() = _binding!!

    private val repoViewModel: RepoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepoScreenBinding.inflate(inflater, container, false)

        initViews()

        return binding.root
    }

    private fun initViews() {
        binding.tbRepoScreen.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnAddRepository.setOnClickListener {
            val ownerName = binding.etOwnerName.text.toString().trim { it <= ' ' }
            val repositoryName = binding.etRepositoryName.text.toString().trim { it <= ' ' }
            if (validateEntriesOfEditText(ownerName, repositoryName)) {
                fetchRepository(ownerName, repositoryName)
            }
        }
    }

    private fun validateEntriesOfEditText(ownerName: String, repositoryName: String): Boolean {

        val validateOwnerName = repoViewModel.validateOwnerName(ownerName)
        val validateRepositoryName = repoViewModel.validateRepositoryName(repositoryName)

        if (!validateOwnerName.first) {
            binding.tilOwnerName.isErrorEnabled = true
            binding.tilOwnerName.error = validateOwnerName.second
            return false
        } else {
            binding.tilOwnerName.isErrorEnabled = false
        }

        if (!validateRepositoryName.first) {
            binding.tilRepositoryName.isErrorEnabled = true
            binding.tilRepositoryName.error = validateRepositoryName.second
            return false
        } else {
            binding.tilRepositoryName.isErrorEnabled = false
        }

        return true
    }

    private fun fetchRepository(ownerName: String, repositoryName: String) {

        repoViewModel.getRepository(ownerName, repositoryName)

        repoViewModel.repository.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    addRepository(result.data!!)
                }

                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        result.errorMessage, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun addRepository(result: ApiResponse) {
        val localRepository = Repository()
        localRepository.ownerName = result.owner.login
        localRepository.repositoryName = result.name
        localRepository.repositoryUrl = result.htmlUrl
        localRepository.description =
            result.description ?: resources.getString(R.string.description_empty)

        repoViewModel.insertRepositoryToLocalDb(localRepository)

        repoViewModel.dataInserted.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}