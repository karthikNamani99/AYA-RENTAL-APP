package com.sapotos.ayarental.presentation.legal

import android.view.View
import androidx.databinding.DataBindingUtil
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.databinding.PrivacyPolicyScreenBinding

class PrivacyPolicyActivity : BaseActivity() {

    private lateinit var binding: PrivacyPolicyScreenBinding

    override fun getLayoutResourceId(): View {
        binding = DataBindingUtil.setContentView(this, R.layout.privacy_policy_screen)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Load from res/raw/privacy_policy.txt
        val text = resources.openRawResource(R.raw.privacy_policy)
            .bufferedReader(Charsets.UTF_8)
            .use { it.readText() }
        binding.tvBody.text = text

        return binding.root
    }
}
