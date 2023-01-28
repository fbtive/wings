package com.example.wingsgroup.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.wingsgroup.R
import com.example.wingsgroup.databinding.FragmentLoginBinding
import com.example.wingsgroup.utils.EventObserver
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        setupNavigation()
        setupBackPress()
        setupLogin()
        setupObserver()

        return binding.root
    }

    private fun setupLogin() {
        binding.loginBtn.setOnClickListener {
            val email = binding.emailField.editText?.text.toString()
            val password = binding.passwordField.editText?.text.toString()
            if(email.isNullOrEmpty()) {
                binding.emailField.error = "Email is required"
            }else if(password.isNullOrEmpty()){
                binding.passwordField.error = "Password is required"
            }else {
                viewModel.loginUser(email, password)
            }
        }
    }

    private fun setupNavigation() {
        binding.signupBtn.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    private fun setupObserver() {
        viewModel.errorEvent.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })

        viewModel.userAuthEvent.observe(viewLifecycleOwner, EventObserver {
            if(it) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }
        })
    }

    private fun setupBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }

}