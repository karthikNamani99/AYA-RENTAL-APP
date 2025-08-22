package com.sapotos.ayarental.presentation.login_page

import android.content.Intent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.constants.Constants
import com.sapotos.ayarental.databinding.LoginScreenBinding
import com.sapotos.ayarental.presentation.dashboad_screen.BottomNavigation
import com.sapotos.ayarental.presentation.signup_page.SignUpScreen

class LoginScreen : BaseActivity() {

    private lateinit var binding: LoginScreenBinding
    private lateinit var vm: LoginScreen_View_Model

    override fun getLayoutResourceId(): View {
        binding = DataBindingUtil.setContentView(this, R.layout.login_screen)

        // ViewModel without the viewModels() delegate
        vm = ViewModelProvider(this)[LoginScreen_View_Model::class.java]

        binding.lifecycleOwner = this
        binding.vm = vm

        // Login button
        binding.btnLogin.setOnClickListener { vm.onLoginClick() }

        // Observe success -> save flag + navigate
        vm.loginSuccess.observe(this) { ok ->
            if (ok == true) {
                val prefs = PreferenceHelper.defaultPrefs(this)
                prefs.edit().putString(Constants.IS_LOGIN_FIRST_TIME, "Y").apply()

                startActivity(Intent(this, BottomNavigation::class.java))
                finish()
            }
        }

        // "SignUp Now" link
        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this, SignUpScreen::class.java))
        }


        binding.ivHeader.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_from_top).apply {
                duration = 1500
            }
        )
        binding.carImage.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_right_new).apply {
                duration = 2500
            }
        )

        // Wave/background: from bottom
        binding.loginCard.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_right_new).apply {
                duration = 1200
            }
        )

        // Buttons: from right (staggered)
        binding.loginTxt.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_right_new).apply {
                startOffset = 1350
            }
        )
        binding.email.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_right_new).apply {
                startOffset = 1450
            }
        )
        binding.password.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_right_new).apply {
                startOffset = 1550
            }
        )
        binding.btnLogin.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_left).apply {
                startOffset = 1650
            }
        )
        binding.tvSignup.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_right_new).apply {
                startOffset = 1750
            }
        )


        return binding.root
    }
}
