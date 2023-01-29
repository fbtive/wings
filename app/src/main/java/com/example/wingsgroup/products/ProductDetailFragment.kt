package com.example.wingsgroup.products

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.wingsgroup.R
import com.example.wingsgroup.data.domain.Product
import com.example.wingsgroup.databinding.FragmentProductDetailBinding
import com.example.wingsgroup.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding
    private lateinit var product: Product
    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

        val arguments = ProductDetailFragmentArgs.fromBundle(requireArguments())

        product = viewModel.findProduct(arguments.code)

        binding.viewModel = viewModel
        binding.product = product
        binding.productImg.transitionName = arguments.code
        binding.productImg.setImageDrawable(ContextCompat.getDrawable(requireContext(), product.img))

        setupObserver()

        return binding.root
    }

    fun setupObserver() {
        viewModel.eventBuy.observe(viewLifecycleOwner, EventObserver {
            if(it) {
                findNavController().navigate(R.id.cartFragment)
            }
        })
    }

}