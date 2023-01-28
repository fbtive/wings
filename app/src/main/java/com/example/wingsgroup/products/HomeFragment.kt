package com.example.wingsgroup.products

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wingsgroup.R
import com.example.wingsgroup.auth.WingsAuth
import com.example.wingsgroup.databinding.FragmentHomeBinding
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ProductAdapter
    private val viewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        sharedElementReturnTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

        setupRecyclerView()
        setupObserver()
        setupBackPress()
        Timber.i("${WingsAuth.username}, ${WingsAuth.email}, ${WingsAuth.phone}")

        return binding.root
    }

    private fun setupRecyclerView() {
        val recyclerManager = GridLayoutManager(activity, 2)
        recyclerManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when(position) {
                0 -> 2
                else -> 1
            }
        }

        adapter = ProductAdapter{ code: String, imageView: ImageView ->
            navigateToDetail(code, imageView)
        }

        binding.productRecycler.layoutManager = recyclerManager
        binding.productRecycler.adapter = adapter

        postponeEnterTransition()
        binding.productRecycler.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun setupObserver() {
        viewModel.allProduct.observe(viewLifecycleOwner) {
            adapter.addHeaderAndSubmitList(it)
        }
    }

    private fun navigateToDetail(code: String, imageView: ImageView) {
        val direction = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(code)
        val extras = FragmentNavigatorExtras(
            imageView to code
        )

        findNavController().navigate(direction, extras)
    }

    private fun setupBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }

}