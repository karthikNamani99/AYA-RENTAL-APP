package com.sapotos.ayarental.presentation.carMoredetails

import android.content.Intent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.databinding.CarDetailsMoreBinding
import com.sapotos.ayarental.presentation.booking.BookingConfirmationActivity
import com.sapotos.ayarental.presentation.cardetails.CarDetails.Companion.EXTRA_IMG
import com.sapotos.ayarental.presentation.cardetails.CarDetails.Companion.EXTRA_NAME
import com.sapotos.ayarental.presentation.cardetails.CarDetails.Companion.EXTRA_PRICE
import com.sapotos.ayarental.presentation.cardetails.CarDetailsScreen_View_Model
import com.sapotos.ayarental.presentation.cardetails.CarDetailsScreen_View_Model.Companion.EXTRA_DESC

class CarMoreDetails : BaseActivity() {

    private lateinit var binding: CarDetailsMoreBinding
    private lateinit var vm: CarMoreDetailsScreen_View_Model

    override fun getLayoutResourceId(): View {
        binding = DataBindingUtil.setContentView(this, R.layout.car_details_more)
        vm = ViewModelProvider(this)[CarMoreDetailsScreen_View_Model::class.java]

        binding.lifecycleOwner = this

        vm.loadFromIntent(intent)
        val name = intent.getStringExtra(EXTRA_NAME) ?: "Category"
        val price = intent.getIntExtra(EXTRA_PRICE,0)
        val img = intent.getIntExtra(EXTRA_IMG, 0)
        val des = intent.getStringExtra(EXTRA_DESC)?: "Description"
        // Book button
        binding.btnBook.setOnClickListener {
            val i = Intent(this, BookingConfirmationActivity::class.java).apply {
                putExtra(CarMoreDetailsScreen_View_Model.EXTRA_NAME, name)
                putExtra(CarMoreDetailsScreen_View_Model.EXTRA_PRICE, price)
                putExtra(CarMoreDetailsScreen_View_Model.EXTRA_HERO_RES, img )
                putExtra(CarMoreDetailsScreen_View_Model.EXTRA_DESC, des)
                putIntegerArrayListExtra(
                    CarMoreDetailsScreen_View_Model.EXTRA_GALLERY,
                    ArrayList(vm.gallery.value ?: emptyList())
                )

            // TODO: booking flow
        }
            startActivity(i)
        }
        return binding.root
    }
}
