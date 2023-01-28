package com.example.wingsgroup

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.lifecycle.lifecycleScope
import com.example.wingsgroup.auth.WingsAuth
import com.google.android.material.transition.platform.MaterialFade
import com.google.android.material.transition.platform.MaterialSharedAxis
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
//        window.exitTransition = MaterialFade().apply { duration = 400 }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val intent = Intent(this, MainActivity::class.java)
//        val bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
        lifecycleScope.launch {
            WingsAuth.getLocalData(applicationContext)
            delay(1600)
            startActivity(intent)
            finish()
        }
    }
}