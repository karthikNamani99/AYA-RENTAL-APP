package com.sapotos.ayarental.presentation.splash_screen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.constants.Constants
import com.sapotos.ayarental.databinding.SplashScreenMainBinding
import com.sapotos.ayarental.presentation.dashboad_screen.BottomNavigation
import com.sapotos.ayarental.presentation.welcome_page.WelComeScreen

@SuppressLint("CustomSplashScreen")
class SplashScreen : BaseActivity() {

    companion object {
        private var viewModel: SplashScreen_View_Model? = null
    }

    override fun getLayoutResourceId(): View {
        val binding: SplashScreenMainBinding =
            DataBindingUtil.setContentView(this, R.layout.splash_screen_main)

        viewModel = ViewModelProvider(this)[SplashScreen_View_Model::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Start animations
        val popIn = AnimationUtils.loadAnimation(this, R.anim.logo_pop_in)
        val breathe = AnimationUtils.loadAnimation(this, R.anim.logo_breathe)

        popIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                // After pop-in completes, start gentle breathing
                binding.logoImage.startAnimation(breathe)
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })

        binding.logoImage.startAnimation(popIn)

        // Prefs + routing after delay
        val prefs = PreferenceHelper.defaultPrefs(this)

        Handler(Looper.getMainLooper()).postDelayed({
            val flag = prefs.getString(Constants.IS_LOGIN_FIRST_TIME, null)
            when (flag) {
                "Y" -> {
                    // first-time completed? go to BottomNavigation (as per your logic)
                    startActivity(Intent(this, BottomNavigation::class.java))
                }
                "N" -> {
                    // needs onboarding/welcome
                    startActivity(Intent(this, WelComeScreen::class.java))
                }
                else -> {
                    // default: welcome
                    startActivity(Intent(this, WelComeScreen::class.java))
                }
            }
            finish()
        }, 1500)

        return binding.root
    }
}
