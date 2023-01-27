package com.example.wingsgroup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.wingsgroup.auth.WingsAuth

import com.example.wingsgroup.databinding.ActivityMainBinding
import com.google.android.material.transition.platform.MaterialFade
import com.google.android.material.transition.platform.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
//        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
//
//        window.enterTransition = MaterialFade().apply { duration = 400 }
//        window.allowEnterTransitionOverlap = true

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navController = findNavController(R.id.mainNavHost)
        if(WingsAuth.email == null) {
            navController.navigate(R.id.loginFragment)
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(navController)
//    }
}