package com.sapotos.ayarental.presentation.booking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.databinding.BookingConfirmationScreenBinding
import com.sapotos.ayarental.presentation.carMoredetails.CarMoreDetailsScreen_View_Model
import com.sapotos.ayarental.presentation.cardetails.CarDetails.Companion.EXTRA_IMG
import com.sapotos.ayarental.presentation.cardetails.CarDetails.Companion.EXTRA_NAME
import com.sapotos.ayarental.presentation.cardetails.CarDetails.Companion.EXTRA_PRICE
import com.sapotos.ayarental.presentation.cardetails.CarDetailsScreen_View_Model.Companion.EXTRA_DESC
import com.sapotos.ayarental.presentation.idetificationScreen.IdentificationScreen
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BookingConfirmationActivity : BaseActivity() {

    private lateinit var binding: BookingConfirmationScreenBinding
    private lateinit var vm: BookingConfirmation_View_Model

    override fun getLayoutResourceId(): View {
        binding = DataBindingUtil.setContentView(this, R.layout.booking_confirmation_screen)
        vm = ViewModelProvider(this)[BookingConfirmation_View_Model::class.java]

        binding.lifecycleOwner = this
        binding.viewModel = vm

        // Back
        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Click handlers for pickers
        binding.etPickLocation.setOnClickListener { /* open place picker if you have one */ }
        binding.etReturnLocation.setOnClickListener { /* open place picker if you have one */ }

        binding.etPickDateTime.setOnClickListener { showDateTimePicker(isPickup = true) }
        binding.etReturnDateTime.setOnClickListener { showDateTimePicker(isPickup = false) }
        val img = intent.getIntExtra(EXTRA_IMG, 0)
        if (img != 0) binding.imgCar.setImageResource(img)


        // Continue
        binding.btnContinue.setOnClickListener {
            if (vm.isFormValid()) {
                val pickupLoc = binding.etPickLocation.text?.toString()?.trim().orEmpty()
                val dropLoc   = binding.etReturnLocation.text?.toString()?.trim().orEmpty()
                val pickupDT  = vm.pickDateTime.value.orEmpty()   // e.g., "Aug 21, 2025 10:30 AM"
                val dropDT    = vm.returnDateTime.value.orEmpty()

                val name = intent.getStringExtra(EXTRA_NAME) ?: "Category"
                val price = intent.getIntExtra(EXTRA_PRICE,0)
                val des = intent.getStringExtra(EXTRA_DESC) ?: "Description"

                val i = Intent(this, IdentificationScreen::class.java).apply {
                    putExtra(BookingConfirmation_View_Model.EXTRA_NAME, name)
                    putExtra(BookingConfirmation_View_Model.EXTRA_PRICE, price)
                    putExtra(BookingConfirmation_View_Model.EXTRA_HERO_RES, img)
                    putExtra(BookingConfirmation_View_Model.EXTRA_DESC, des)

                    // 🚗 Pass pickup/drop info
                    putExtra(BookingConfirmation_View_Model.EXTRA_PICKUP_LOC, pickupLoc)
                    putExtra(BookingConfirmation_View_Model.EXTRA_DROP_LOC, dropLoc)
                    putExtra(BookingConfirmation_View_Model.EXTRA_PICKUP_DATE, pickupDT)
                    putExtra(BookingConfirmation_View_Model.EXTRA_DROP_DATE, dropDT)
                }
                startActivity(i)
            } else {
                Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }

    private fun showDateTimePicker(isPickup: Boolean) {
        val baseMillis = if (isPickup) vm.pickMillis else vm.returnMillis
        val cal = Calendar.getInstance().apply { timeInMillis = baseMillis }

        DatePickerDialog(
            this,
            { _, y, m, d -> showTimePicker(isPickup, y, m, d) },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePicker(isPickup: Boolean, year: Int, month: Int, day: Int) {
        val now = Calendar.getInstance()
        TimePickerDialog(
            this,
            { _, hour, minute ->
                val c = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, day)
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                val df = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US)
                if (isPickup) {
                    vm.pickMillis = c.timeInMillis
                    vm.pickDateTime.value = df.format(c.time)
                } else {
                    vm.returnMillis = c.timeInMillis
                    vm.returnDateTime.value = df.format(c.time)
                }
            },
            now.get(Calendar.HOUR_OF_DAY),
            now.get(Calendar.MINUTE),
            false
        ).show()
    }
}
