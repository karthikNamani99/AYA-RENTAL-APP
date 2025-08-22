package com.sapotos.ayarental.presentation.welcome_page

import android.content.Intent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.databinding.HomeScreenBinding
import com.sapotos.ayarental.databinding.WelComeScreenBinding
import com.sapotos.ayarental.interfaces.NavigationInterface
import com.sapotos.ayarental.presentation.login_page.LoginScreen
import com.sapotos.ayarental.presentation.signup_page.SignUpScreen

class WelComeScreen : BaseActivity() {

    companion object {

        private var viewModel: WelComeScreen_View_Model? = null
    }

    override fun getLayoutResourceId(): View {
        val binding: WelComeScreenBinding =
            DataBindingUtil.setContentView(this, R.layout.wel_come_screen)

        viewModel = ViewModelProvider(this)[WelComeScreen_View_Model::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Buttons navigation (unchanged)
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpScreen::class.java))
//            finish()
        }
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginScreen::class.java))
//            finish()
        }

        // ---- Animations ----
        // Car: from left
        binding.imgCar.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_left).apply {
                duration = 2800
            }
        )

        // Wave/background: from bottom
        binding.imgWave.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom).apply {
                duration = 800
            }
        )

        // Buttons: from right (staggered)
        binding.btnSignUp.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_right).apply {
                startOffset = 650
            }
        )
        binding.btnLogin.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_right).apply {
                startOffset = 850
            }
        )

        return binding.root
    }
}