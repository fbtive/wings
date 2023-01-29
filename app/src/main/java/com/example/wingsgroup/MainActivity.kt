package com.example.wingsgroup

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.view.Menu
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.wingsgroup.auth.WingsAuth

import com.example.wingsgroup.databinding.ActivityMainBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.transition.platform.MaterialFade
import com.google.android.material.transition.platform.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
//        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
//
//        window.enterTransition = MaterialFade().apply { duration = 400 }
//        window.allowEnterTransitionOverlap = true

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupToolbarAndNavigation()
        checkAuth()
        setupObserver()
    }

    private fun setupToolbarAndNavigation() {
        val toolbar = binding.mainToolbar
        val drawerLayout = binding.drawerLayout

        navController = findNavController(R.id.mainNavHost)
//        appBarConfiguration = AppBarConfiguration.Builder(R.id.loginFragment, R.id.homeFragment)
//            .setOpenableLayout(drawerLayout)
//            .build()
        NavigationUI.setupWithNavController(toolbar, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navDrawer, navController)

        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            when(nd.id) {
                R.id.loginFragment -> {
                    binding.mainAppbar.visibility = View.GONE
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.registerFragment -> {
                    binding.mainAppbar.visibility = View.GONE
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                else -> {
                    binding.mainAppbar.visibility = View.VISIBLE
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
            }
        }

        binding.navDrawer.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.homeFragment -> {
                    binding.drawerLayout.close()
                    if(navController.currentDestination?.id != R.id.homeFragment) {
                        navController.navigate(R.id.homeFragment)
                    }
                }
                R.id.transactionFragment -> {
                    binding.drawerLayout.close()
                    if(navController.currentDestination?.id != R.id.transactionFragment) {
                        navController.navigate(R.id.transactionFragment)
                    }
                }
                R.id.logout_menu -> {
                    WingsAuth.logout(this)
                    binding.mainAppbar.visibility = View.GONE
                    navController.navigate(R.id.loginFragment)
                    Timber.i("${WingsAuth.username}, ${WingsAuth.email}, ${WingsAuth.phone}")
                }
            }

            true
        }
    }

    fun setupObserver() {
        viewModel.cartCount.observe(this) {
            Timber.i("cart count is $it")
            binding.mainToolbar.run {
                val menuItem = menu.findItem(R.id.action_cart)
                val actionView = menuItem.actionView

                val textCount = actionView?.findViewById<TextView>(R.id.cart_badge)

                if(this.visibility != View.GONE) {
                    textCount?.text = it?.toString() ?: "0"
                }

                actionView?.setOnClickListener {
                    if(navController.currentDestination?.id != R.id.cartFragment) {
                        navController.navigate(R.id.cartFragment)
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }

    private fun checkAuth() {
        if(WingsAuth.email == null) {
            navController.navigate(R.id.loginFragment)
        }
    }
}