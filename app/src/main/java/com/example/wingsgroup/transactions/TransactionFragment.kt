package com.example.wingsgroup.transactions

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.transition.Slide
import com.example.wingsgroup.R
import com.example.wingsgroup.databinding.FragmentTransactionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionFragment : Fragment() {

    private lateinit var binding: FragmentTransactionBinding
    private lateinit var adapter: TransactionListAdapter
    private val viewModel: TransactionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransactionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        enterTransition = Slide(Gravity.BOTTOM).apply { duration = 600L }
        exitTransition = Slide(Gravity.BOTTOM).apply { duration = 600L }

        setupRecycler()
        setupObserver()

        return binding.root
    }

    private fun setupRecycler(){
        adapter = TransactionListAdapter()
        binding.transactionList.adapter = adapter
//        binding.transactionList.itemAnimator = null
    }

    private fun setupObserver() {
        viewModel.transactionList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

}