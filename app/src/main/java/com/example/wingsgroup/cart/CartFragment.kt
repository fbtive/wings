package com.example.wingsgroup.cart

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.Fade
import androidx.transition.Slide
import com.example.wingsgroup.R
import com.example.wingsgroup.data.local.asDomainModel
import com.example.wingsgroup.databinding.FragmentCartBinding
import com.example.wingsgroup.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var adapter: CartListAdapter
    private val viewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        enterTransition = Slide(Gravity.TOP).apply { duration = 400L }
        exitTransition = Slide(Gravity.TOP).apply { duration = 400L }

        setupRecycler()
        setupObserver()

        return binding.root
    }

    private fun setupRecycler() {
        adapter = CartListAdapter {
            viewModel.removeItemCart(it)
        }

        binding.cartList.itemAnimator = null
        binding.cartList.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.cartList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.eventBuy.observe(viewLifecycleOwner, EventObserver {
            if(it) {
                findNavController().navigate(R.id.transactionFragment)
            }
        })
    }

}