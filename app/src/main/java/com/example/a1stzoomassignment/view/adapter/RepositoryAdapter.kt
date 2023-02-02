package com.example.a1stzoomassignment.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.a1stzoomassignment.databinding.RepositoryListItemBinding
import com.example.a1stzoomassignment.model.dataclasses.Repository

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepoItemViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RepositoryListItemBinding.inflate(inflater, parent, false)
        return RepoItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        val repositoryItem = differ.currentList[position]

        holder.binding.apply {
            tvRepositoryName.text = repositoryItem.repositoryName
            tvDescription.text = repositoryItem.description

            ivShare.setOnClickListener {
                onShareClickListener?.let {listener->
                    listener(repositoryItem)
                }
            }
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { listener -> listener(repositoryItem) }
        }
    }

    private var onItemClickListener: ((repository: Repository) -> Unit)? = null

    private var onShareClickListener : ((repository:Repository)->Unit)? = null


    fun setOnClickListener(listener: ((repository: Repository) -> Unit)) {
        onItemClickListener = listener
    }

    fun setOnShareClickListener(listener: ((repository: Repository) -> Unit)) {
        onShareClickListener = listener
    }



    inner class RepoItemViewHolder(itemView: RepositoryListItemBinding) :
        ViewHolder(itemView.root) {
        var binding: RepositoryListItemBinding

        init {
            binding = itemView
        }
    }
}