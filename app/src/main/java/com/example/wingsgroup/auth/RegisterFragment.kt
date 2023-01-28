package com.example.wingsgroup.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.wingsgroup.R
import com.example.wingsgroup.databinding.FragmentRegisterBinding
import com.example.wingsgroup.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var handler: RegistrationHandler
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        handler = RegistrationHandler(binding)

        binding.model = viewModel.model
        binding.handler = handler

        setupNavigation()
        setupRegister()
        setupObserver()

        return binding.root
    }

    private fun setupRegister() {
        binding.registerBtn.setOnClickListener {
            registerNewUser()
        }
    }

    private fun registerNewUser() {
        handler.usernameValidator(binding.nameField.editText?.text.toString())
        handler.emailValidator(binding.emailField.editText?.text.toString())
        handler.passwordValidator(binding.passwordField.editText?.text.toString())
        handler.phoneValidator(binding.phoneField.editText?.text.toString())

        if(
            binding.nameField.error.isNullOrEmpty()
            && binding.emailField.error.isNullOrEmpty()
            && binding.passwordField.error.isNullOrEmpty()
            && binding.phoneField.error.isNullOrEmpty()
        ) {
            viewModel.registerUser(
                binding.nameField.editText?.text.toString(),
                binding.emailField.editText?.text.toString(),
                binding.passwordField.editText?.text.toString(),
                binding.phoneField.editText?.text.toString()
            )
        }else {
            Timber.i("form invalid")
        }
    }

    private fun setupObserver() {
        viewModel.allUser.observe(viewLifecycleOwner) {
            for (user in it) {
                Timber.i("${user.username}, ${user.email}, ${user.password}, ${user.phone} ")
            }
        }

        viewModel.userAuthEvent.observe(viewLifecycleOwner, EventObserver {
            if(it) {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
            }
        })
    }

    private fun setupNavigation() {
        binding.signinBtn.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
    }


}