package com.sapotos.ayarental.presentation.legal

import android.view.View
import androidx.databinding.DataBindingUtil
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.databinding.TermsAndConditionsScreenBinding

class TermsAndConditionsScreen : BaseActivity() {

    private lateinit var binding: TermsAndConditionsScreenBinding

    override fun getLayoutResourceId(): View {
        binding = DataBindingUtil.setContentView(this, R.layout.terms_and_conditions_screen)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val terms = resources.openRawResource(R.raw.terms)
            .bufferedReader().use { it.readText() }
        binding.tvBody.text = terms
        return binding.root
    }
}
