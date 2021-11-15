package com.bazar.bane.bazarshahr.loginFragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.databinding.FragmentSplashScreenBinding
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.USER_ID
import com.bazar.bane.bazarshahr.util.AppConstants.Companion.USER_TOKEN
import com.bazar.bane.bazarshahr.util.SharedPreferenceUtil

class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_splash_screen, container, false)
        val view = binding.root
        binding.lifecycleOwner = this
        initData()
        Handler(Looper.getMainLooper()).postDelayed({
            startApp()
        }, 3000)
        return view
    }

    private fun startApp() {
        /*var a = SharedPreferenceUtil.getStringValue(USER_TOKEN)
        var b = SharedPreferenceUtil.getStringValue(USER_ID)*/
        when {
            !existUser() -> {
                findNavController().navigate(R.id.action_splashScreenFragment_to_signInFragment)
            }
            existUser() -> {
                findNavController().navigate(R.id.action_splashScreenFragment_to_mainActivity)
                requireActivity().finish()
            }
        }
    }

    private fun existUser(): Boolean {
        if (SharedPreferenceUtil.getStringValue(USER_TOKEN)
                .equals("")
        )
            return false
        return true
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