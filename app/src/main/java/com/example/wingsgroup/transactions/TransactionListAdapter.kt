package com.example.wingsgroup.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wingsgroup.data.local.TransactionModel
import com.example.wingsgroup.databinding.ListTransactionItemBinding

class TransactionListAdapter(): ListAdapter<TransactionModel, TransactionListAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TransactionViewHolder(val binding: ListTransactionItemBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): TransactionViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListTransactionItemBinding.inflate(inflater, parent, false)
                return TransactionViewHolder(binding)
            }
        }

        fun bind(item: TransactionModel) {
            binding.transaction = item
            binding.executePendingBindings()
        }
    }

    class TransactionDiffCallback: DiffUtil.ItemCallback<TransactionModel>() {
        override fun areItemsTheSame(
            oldItem: TransactionModel,
            newItem: TransactionModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: TransactionModel,
            newItem: TransactionModel
        ): Boolean {
            return oldItem.documentNumber == oldItem.documentNumber
        }
    }
}