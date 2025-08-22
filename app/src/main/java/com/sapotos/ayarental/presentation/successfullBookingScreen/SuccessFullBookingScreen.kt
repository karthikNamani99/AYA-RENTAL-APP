// com/sapotos/ayarental/presentation/successfullBookingScreen/SuccessFullBookingScreen.kt
package com.sapotos.ayarental.presentation.successfullBookingScreen

import android.content.Intent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.databinding.SuccessfullBookingScreenBinding
import com.sapotos.ayarental.presentation.dashboad_screen.BottomNavigation

class SuccessFullBookingScreen : BaseActivity() {

    private lateinit var binding: SuccessfullBookingScreenBinding
    private lateinit var viewModel: SuccessFullBookingScreen_View_Model

    override fun getLayoutResourceId(): View {
        binding = DataBindingUtil.setContentView(this, R.layout.successfull_booking_screen)
        viewModel = ViewModelProvider(this)[SuccessFullBookingScreen_View_Model::class.java]

        binding.lifecycleOwner = this
        binding.vm = viewModel

        viewModel.loadFromIntent(intent)

        binding.btnDone.setOnClickListener {
            startActivity(Intent(this, BottomNavigation::class.java))
            finish()
        } // or navigate to home

        return binding.root
    }
}
