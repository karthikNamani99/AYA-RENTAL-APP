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
import androidx.lifecycle.ViewModelProvider
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.databinding.SignupScreenBinding
import com.sapotos.ayarental.databinding.WelComeScreenBinding
import com.sapotos.ayarental.presentation.legal.PrivacyPolicyActivity
import com.sapotos.ayarental.presentation.legal.TermsAndConditionsScreen
import com.sapotos.ayarental.presentation.login_page.LoginScreen

class SignUpScreen : BaseActivity() {

    companion object {

        private var viewModel: SignUpScreen_View_Model? = null
    }

    override fun getLayoutResourceId(): View {
        val binding: SignupScreenBinding = DataBindingUtil.setContentView(this, R.layout.signup_screen)
        viewModel = ViewModelProvider(this)[SignUpScreen_View_Model::class.java]
        val prefs = PreferenceHelper.defaultPrefs(this)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpPageMain::class.java))
//            finish()
        }
        binding.loginTxt.setOnClickListener {
            startActivity(Intent(this, LoginScreen::class.java))
//            finish()
        }
        setupClickableLegal(binding.tvLegal)
        binding.btnSignUp.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_left).apply {
                duration = 1800
            }
        )

        // Wave/background: from bottom
        binding.alreadyMemberTxt.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_left).apply {
                duration = 1200
            }
        )

        // Buttons: from right (staggered)
        binding.loginTxt.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_left).apply {
                startOffset = 1050
            }
        )
        binding.tvLegal.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_left).apply {
                startOffset = 850
            }
        )
        binding.btnCancel.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.slide_in_left).apply {
                startOffset = 850
            }
        )
//        viewModel!!.routingListener = this
        return binding.root

    }
    private fun setupClickableLegal(tv: TextView) {
        val full = getString(R.string.by_signing_up_you_accept_our_terms_and_conditions_and_privacy_policy)

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
                        ds.isUnderlineText = false          // no underline
                        ds.color = Color.parseColor("#1976D2") // blue (material-ish)
                        ds.typeface = Typeface.create(ds.typeface, Typeface.BOLD)
                    }
                }
                spannable.setSpan(clickable, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                // Also ensure color if a11y adjusts spans
                spannable.setSpan(ForegroundColorSpan(Color.parseColor("#1976D2")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        makeClickable(termsText) {
            // With flags (optional): CLEAR_TOP + SINGLE_TOP to avoid stacking
            val intent = Intent(this, TermsAndConditionsScreen::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        makeClickable(privacyText) {
            val intent = Intent(this, PrivacyPolicyActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        tv.text = spannable
        tv.movementMethod = LinkMovementMethod.getInstance()   // <- enable clicks
        tv.highlightColor = Color.TRANSPARENT                  // no click highlight
    }

}