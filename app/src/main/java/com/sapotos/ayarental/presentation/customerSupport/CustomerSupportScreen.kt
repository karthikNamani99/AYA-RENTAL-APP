package com.sapotos.ayarental.presentation.customerSupport

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.databinding.CustomerSupportScreenBinding

class CustomerSupportScreen : BaseActivity() {

    private lateinit var binding: CustomerSupportScreenBinding
    private lateinit var vm: CustomerSupportScreen_View_Model

    override fun getLayoutResourceId(): View {
        binding = DataBindingUtil.setContentView(this, R.layout.customer_support_screen)
        vm = ViewModelProvider(this)[CustomerSupportScreen_View_Model::class.java]

        binding.lifecycleOwner = this
        binding.vm = vm

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.cardPhone.setOnClickListener {
            val number = vm.phone.value?.trim().orEmpty()
            if (number.isNotEmpty()) {
                val dial = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
                startActivity(dial)
            }
        }

        binding.cardEmail.setOnClickListener {
            val email = vm.email.value?.trim().orEmpty()
            if (email.isNotEmpty()) {
                val mail = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                    putExtra(Intent.EXTRA_SUBJECT, getString(R.string.support_email_subject))
                }
                startActivity(Intent.createChooser(mail, getString(R.string.send_email)))
            }
        }

        return binding.root
    }
}
