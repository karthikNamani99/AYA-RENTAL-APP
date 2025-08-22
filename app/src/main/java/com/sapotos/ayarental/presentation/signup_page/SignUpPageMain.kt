package com.sapotos.ayarental.presentation.signup_page

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.checkbox.MaterialCheckBox
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.constants.Constants
import com.sapotos.ayarental.databinding.SignupPageNainBinding
import com.sapotos.ayarental.presentation.dashboad_screen.BottomNavigation
import com.sapotos.ayarental.presentation.legal.PrivacyPolicyActivity
import com.sapotos.ayarental.presentation.legal.TermsAndConditionsScreen

class SignUpPageMain : BaseActivity() {

    companion object {

        private var viewModel: SignUpPageMain_View_Model? = null
    }

    override fun getLayoutResourceId(): View {
        val binding: SignupPageNainBinding = DataBindingUtil.setContentView(this, R.layout.signup_page_nain)
        viewModel = ViewModelProvider(this)[SignUpPageMain_View_Model::class.java]
        val prefs = PreferenceHelper.defaultPrefs(this)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel!!.nav.observe(this, Observer { event ->
            event.getIfNotHandled()?.let { dest ->
                when (dest) {
                    is SignUpPageMain_View_Model.Nav.ToHome -> {
                        val prefs = PreferenceHelper.defaultPrefs(this)   // SharedPreferences

                        // Replace HomeScreen::class.java with your target
                        startActivity(Intent(this, BottomNavigation::class.java))
                        prefs.edit()
                            .putString(Constants.IS_LOGIN_FIRST_TIME, "Y")   // or use putBoolean if you prefer
                            .apply()
                        finish() // optional
                    }
                }
            }
        })

        setupClickableCheckBox(binding.tvLegal)


        binding.back.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_right_new).apply {
                duration = 1000
            }
        )

        // Wave/background: from bottom
        binding.fullName.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_right_new).apply {
                duration = 1200
            }
        )

        // Buttons: from right (staggered)
        binding.email.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_right_new).apply {
                startOffset = 1350
            }
        )
        binding.mobile.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_right_new).apply {
                startOffset = 1450
            }
        )
        binding.password.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_right_new).apply {
                startOffset = 1550
            }
        )
        binding.confirmPassword.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_left).apply {
                startOffset = 1650
            }
        )
        binding.tvLegal.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_right_new).apply {
                startOffset = 1750
            }
        )
        binding.btnRegister.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.slide_in_right_new).apply {
                    startOffset = 1850
                }
                )
//        viewModel!!.routingListener = this
        return binding.root

    }
    private fun setupClickableCheckBox(cb: MaterialCheckBox) {
        val full = getString(R.string.by_creating_an_account_you_agree_to_our_terms_and_conditions_and_privacy_policy)

        val termsText = "Terms and Conditions"
        val privacyText = "Privacy Policy"

        val spannable = SpannableString(full)

        fun makeClickable(target: String, onClick: () -> Unit) {
            val start = full.indexOf(target)
            if (start >= 0) {
                val end = start + target.length

                val clickable = object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        onClick()
                    }
                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                        ds.color = Color.parseColor("#1976D2") // Material blue
                        ds.typeface = Typeface.create(ds.typeface, Typeface.BOLD)
                    }
                }
                spannable.setSpan(clickable, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannable.setSpan(
                    ForegroundColorSpan(Color.parseColor("#1976D2")),
                    start,
                    end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        makeClickable(termsText) {
            startActivity(Intent(this, TermsAndConditionsScreen::class.java))
        }
        makeClickable(privacyText) {
            startActivity(Intent(this, PrivacyPolicyActivity::class.java))
        }

        cb.text = spannable
        cb.movementMethod = LinkMovementMethod.getInstance()
        cb.highlightColor = Color.TRANSPARENT
    }


}