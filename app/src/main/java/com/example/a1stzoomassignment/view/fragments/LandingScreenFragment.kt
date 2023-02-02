package com.example.a1stzoomassignment.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a1stzoomassignment.R
import com.example.a1stzoomassignment.databinding.FragmentLandingScreenBinding
import com.example.a1stzoomassignment.view.adapter.RepositoryAdapter
import com.example.a1stzoomassignment.viewModel.LandingScreenViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LandingScreenFragment : Fragment() {

    private var _binding: FragmentLandingScreenBinding? = null

    private val binding
        get() = _binding!!

    private val landingScreenViewModel: LandingScreenViewModel by viewModels()

    private lateinit var repositoryAdapter: RepositoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandingScreenBinding.inflate(inflater, container, false)

        initViews()

        return binding.root
    }

    private fun initViews() {
        initiateMenuClick()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        repositoryAdapter = RepositoryAdapter()
        landingScreenViewModel.getListOfRepositoryFromLocalDb().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.tvNoData.visibility = View.GONE
                binding.rvRepositories.visibility = View.VISIBLE
                repositoryAdapter.differ.submitList(it)
            } else {
                binding.tvNoData.visibility = View.VISIBLE
                binding.rvRepositories.visibility = View.GONE
            }
        }

        repositoryAdapter.setOnClickListener {
            val url = it.repositoryUrl
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        repositoryAdapter.setOnShareClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            val textToSend = resources.getString(R.string.share_repository_name_and_url,
                it.repositoryName, it.repositoryUrl)
            shareIntent.putExtra(Intent.EXTRA_TEXT, textToSend)
            startActivity(Intent.createChooser(shareIntent, "Share repository"))
        }

        val recyclerView = binding.rvRepositories

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = repositoryAdapter
        }
    }

    private fun initiateMenuClick() {
        binding.tbLandingScreen.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_add_repo -> {
                    findNavController().navigate(
                        LandingScreenFragmentDirections.fragmentLandingScreenToRepoScreen()
                    )
                    true
                }

                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}