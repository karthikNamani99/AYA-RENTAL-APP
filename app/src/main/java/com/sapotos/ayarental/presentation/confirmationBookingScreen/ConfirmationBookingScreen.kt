package com.sapotos.ayarental.presentation.confirmationBookingScreen

import android.content.Intent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.constants.Constants
import com.sapotos.ayarental.databinding.ConfirmationCarBookingScreenBinding
import com.sapotos.ayarental.presentation.cardetails.CarDetails.Companion.EXTRA_IMG
import com.sapotos.ayarental.presentation.successfullBookingScreen.SuccessFullBookingScreen


class ConfirmationBookingScreen : BaseActivity() {

    private lateinit var binding: ConfirmationCarBookingScreenBinding
    private lateinit var viewModel: ConfirmationBookingScreen_View_Model

    override fun getLayoutResourceId(): View {
        binding = DataBindingUtil.setContentView(this, R.layout.confirmation_car_booking_screen)
        viewModel = ViewModelProvider(this)[ConfirmationBookingScreen_View_Model::class.java]

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val img = intent.getIntExtra(EXTRA_IMG, 0)
        if (img != 0) binding.imgCar.setImageResource(img)

        viewModel.loadFromIntent(intent)

        binding.btnConfirm.setOnClickListener {
            // Build the Intent using the latest values from the ViewModel
            val next = Intent(this, SuccessFullBookingScreen::class.java).apply {
                putExtra(Constants.EXTRA_NAME,        viewModel.carName.value.orEmpty())
                putExtra(Constants.EXTRA_PRICE,       viewModel.pricePerDay.value ?: 0)
                putExtra(Constants.EXTRA_PICKUP_LOC,  viewModel.pickupLocation.value.orEmpty())
                putExtra(Constants.EXTRA_DROP_LOC,    viewModel.returnLocation.value.orEmpty())
                putExtra(Constants.EXTRA_PICKUP_DATE, viewModel.pickupDateText.value.orEmpty())
                putExtra(Constants.EXTRA_DROP_DATE,   viewModel.returnDateText.value.orEmpty())

                // If you also want to forward the image to the next page:
                putExtra(EXTRA_IMG, img)

                // If you are also tracking millis in this VM and want to pass them:
                // putExtra(Constants.EXTRA_PICKUP_MILLIS, viewModel.getPickupMillisOrZero())
                // putExtra(Constants.EXTRA_DROP_MILLIS,   viewModel.getReturnMillisOrZero())
            }
            startActivity(next)
        }

        return binding.root
    }
}