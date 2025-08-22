package com.sapotos.ayarental.presentation.idetificationScreen

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.databinding.IdentificationScreenBinding
import com.google.android.material.snackbar.Snackbar
import com.sapotos.ayarental.presentation.booking.BookingConfirmation_View_Model
import com.sapotos.ayarental.presentation.cardetails.CarDetails.Companion.EXTRA_IMG
import com.sapotos.ayarental.presentation.cardetails.CarDetails.Companion.EXTRA_NAME
import com.sapotos.ayarental.presentation.cardetails.CarDetails.Companion.EXTRA_PRICE
import com.sapotos.ayarental.presentation.cardetails.CarDetailsScreen_View_Model.Companion.EXTRA_DESC
import com.sapotos.ayarental.presentation.confirmationBookingScreen.ConfirmationBookingScreen

class IdentificationScreen : BaseActivity() {

    private lateinit var binding: IdentificationScreenBinding
    private lateinit var vm: IdentificationScreen_View_Model

    private val pickFront = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            vm.frontUri.value = it
            binding.frontPreview.setImageURI(it)
            binding.frontOverlay.visibility = View.GONE
            binding.frontPreview.visibility = View.VISIBLE
        }
    }
    private val pickBack = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            vm.backUri.value = it
            binding.backPreview.setImageURI(it)
            binding.backOverlay.visibility = View.GONE
            binding.backPreview.visibility = View.VISIBLE
        }
    }

    override fun getLayoutResourceId(): View {
        binding = DataBindingUtil.setContentView(this, R.layout.identification_screen)
        vm = ViewModelProvider(this)[IdentificationScreen_View_Model::class.java]

        binding.lifecycleOwner = this
        binding.viewModel = vm

        // Back
        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Radio selection
        binding.rbNational.setOnCheckedChangeListener { _, checked ->
            if (checked) vm.docType.value = DocumentType.NATIONAL_ID
        }
        binding.rbPassport.setOnCheckedChangeListener { _, checked ->
            if (checked) vm.docType.value = DocumentType.PASSPORT
        }
        val name = intent.getStringExtra(EXTRA_NAME) ?: "Category"
        val price = intent.getIntExtra(EXTRA_PRICE,0)
        val pickupLoc = intent.getStringExtra(IdentificationScreen_View_Model.EXTRA_PICKUP_LOC)
        val dropLoc   = intent.getStringExtra(IdentificationScreen_View_Model.EXTRA_DROP_LOC)
        val pickupDT  = intent.getStringExtra(IdentificationScreen_View_Model.EXTRA_PICKUP_DATE)
        val dropDT    = intent.getStringExtra(IdentificationScreen_View_Model.EXTRA_DROP_DATE)
        val img = intent.getIntExtra(EXTRA_IMG, 0)

        // Upload tiles
        binding.frontTile.setOnClickListener { pickFront.launch("image/*") }
        binding.backTile.setOnClickListener  { pickBack.launch("image/*")  }
        binding.radioGroup.setOnCheckedChangeListener { _, checked ->
            vm.docType.value = if (checked == R.id.rbNational)
                DocumentType.NATIONAL_ID else DocumentType.PASSPORT
        }
        vm.docType.observe(this) { kind ->
            binding.tilIdNumber.hint = getString(
                if (kind == DocumentType.NATIONAL_ID)
                    R.string.national_id_number
                else
                    R.string.passport_number
            )
        }

        // Continue
        binding.btnContinue.setOnClickListener {
            if (vm.isValid()) {
                Snackbar.make(binding.root, R.string.saved_ok, Snackbar.LENGTH_SHORT).show()
                val i = Intent(this, ConfirmationBookingScreen::class.java).apply {
                    putExtra(IdentificationScreen_View_Model.EXTRA_NAME, name)
                    putExtra(IdentificationScreen_View_Model.EXTRA_PRICE, price)
                    putExtra(IdentificationScreen_View_Model.EXTRA_HERO_RES, img )
                    putExtra(IdentificationScreen_View_Model.EXTRA_PICKUP_LOC, pickupLoc)
                    putExtra(IdentificationScreen_View_Model.EXTRA_DROP_LOC, dropLoc)
                    putExtra(IdentificationScreen_View_Model.EXTRA_PICKUP_DATE, pickupDT)
                    putExtra(IdentificationScreen_View_Model.EXTRA_DROP_DATE, dropDT)

//                    putIntegerArrayListExtra(
//                        CarMoreDetailsScreen_View_Model.EXTRA_GALLERY,
//                        ArrayList(vm.gallery.value ?: emptyList())
//                    )

                }
                startActivity(i)
            } else {
                Snackbar.make(binding.root, R.string.fill_all, Snackbar.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}
