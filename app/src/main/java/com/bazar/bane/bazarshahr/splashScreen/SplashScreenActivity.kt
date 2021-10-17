package com.bazar.bane.bazarshahr.splashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bazar.bane.bazarshahr.MainActivity
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.databinding.ActivitySplashScreenBinding


class SplashScreenActivity : AppCompatActivity() {
    lateinit var binding:ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)
        initData()
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    private fun initData() {
        binding.splash.translationX = 0f
        binding.splash.translationY = 0f
        binding.splash.scaleX = 0.2f
        binding.splash.scaleY = 0.2f
        customAnimation()
    }

    private fun customAnimation() {
        binding.splash.animate().translationX(0f).translationY(0f).rotationBy(1f).scaleX(1f)
            .scaleY(1f).duration = 3000
    }
}

