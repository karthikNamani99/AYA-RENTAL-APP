package com.sapotos.ayarental.presentation.cardetails

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.databinding.CarDetailsScreenBinding
import com.sapotos.ayarental.presentation.carMoredetails.CarMoreDetails
import com.sapotos.ayarental.presentation.selectCategoryScreeen.SelectSubCategoryScreen.Companion.EXTRA_IMG
import com.sapotos.ayarental.presentation.selectCategoryScreeen.SelectSubCategoryScreen.Companion.EXTRA_NAME

class CarDetails : BaseActivity() {

    private lateinit var binding: CarDetailsScreenBinding
    private lateinit var vm: CarDetailsScreen_View_Model

    @SuppressLint("SetTextI18n")
    override fun getLayoutResourceId(): View {
        binding = DataBindingUtil.setContentView(this, R.layout.car_details_screen)
        vm = ViewModelProvider(this)[CarDetailsScreen_View_Model::class.java]

        binding.lifecycleOwner = this

        // Load from intent or use defaults inside the VM
        vm.loadFromIntent(intent)
        val name = intent.getStringExtra(EXTRA_NAME) ?: "Category"
        val price = intent.getIntExtra(EXTRA_PRICE,0)
        val img = intent.getIntExtra(EXTRA_IMG, 0)
        // Bind the 6 spec chips
        bindSpecChips()
        binding.tvName.text = name
        binding.tvPrice.text = "$${price}/day"
        if (img != 0) binding.imgCar.setImageResource(img)
        // Back button if you have one in the layout
        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Open “more details” when tapping the hero image or price row
        binding.imgCar.setOnClickListener { openMoreDetails() }

        return binding.root
    }

    private fun openMoreDetails() {
        val i = Intent(this, CarMoreDetails::class.java).apply {
            putExtra(CarDetailsScreen_View_Model.EXTRA_NAME, vm.name.value)
            putExtra(CarDetailsScreen_View_Model.EXTRA_PRICE, vm.pricePerDay.value ?: 0)
            putExtra(CarDetailsScreen_View_Model.EXTRA_HERO_RES, vm.heroRes.value ?: 0)
            putExtra(CarDetailsScreen_View_Model.EXTRA_DESC, vm.description.value)
            putIntegerArrayListExtra(
                CarDetailsScreen_View_Model.EXTRA_GALLERY,
                ArrayList(vm.gallery.value ?: emptyList())
            )
        }
        startActivity(i)
    }

    private fun bindSpecChips() {
        val map = mapOf(
            R.id.specSpeed to 0,
            R.id.specWifi to 1,
            R.id.specSeats to 2,
            R.id.specSensor to 3,
            R.id.specBt to 4,
            R.id.specDoors to 5
        )
        vm.specs.observe(this) { list ->
            map.forEach { (includeId, idx) ->
                val container = binding.root.findViewById<View>(includeId) ?: return@forEach
                if (idx in list.indices) {
                    val spec = list[idx]
                    container.findViewById<ImageView>(R.id.icon)?.setImageResource(spec.iconRes)
                    container.findViewById<TextView>(R.id.title)?.text = spec.title
                    container.findViewById<TextView>(R.id.value)?.text = spec.value
                }
            }
        }
    }
    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_IMG = "extra_img"
        const val EXTRA_PRICE = "extra_img"
    }
}
