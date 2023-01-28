package com.example.wingsgroup.auth

import android.util.Patterns
import com.example.wingsgroup.databinding.FragmentRegisterBinding

class RegistrationHandler(val binding: FragmentRegisterBinding) {

    fun usernameValidator(username: String) {
        if(binding.nameField.editText == null) return

        if(username.isEmpty())
            binding.nameField.error = "Name is required"
        else
            binding.nameField.error = ""
    }

    fun emailValidator(email: String) {
        if(binding.emailField.editText == null) return

        if(email.isEmpty())
            binding.emailField.error = "E-Mail is required!"
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            binding.emailField.error = "E-Mail is not valid"
        else
            binding.emailField.error = ""
    }

    fun passwordValidator(password: String) {
        if(binding.passwordField.editText == null) return
        val min = 6
        if(password.isEmpty() || password.length < min) {
            binding.passwordField.error = "Minimum password is 6 characters"
        }else {
            binding.passwordField.error = ""
        }
    }

    fun phoneValidator(phone: String) {
        if(binding.phoneField.editText == null) return

        if(phone.isEmpty())
            binding.phoneField.error = "Phone number is required"
        else if(!Patterns.PHONE.matcher(phone).matches())
            binding.phoneField.error = "Invalid phone number"
        else
            binding.phoneField.error = ""
    }
}