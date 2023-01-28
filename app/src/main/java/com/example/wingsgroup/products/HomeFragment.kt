package com.example.wingsgroup.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.wingsgroup.R
import com.example.wingsgroup.auth.WingsAuth
import com.google.android.material.appbar.MaterialToolbar
import timber.log.Timber


class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment

        setupBackPress()
        Timber.i("${WingsAuth.username}, ${WingsAuth.email}, ${WingsAuth.phone}")

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun setupBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }

}