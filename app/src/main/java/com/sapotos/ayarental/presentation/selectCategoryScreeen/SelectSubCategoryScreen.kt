package com.sapotos.ayarental.presentation.selectCategoryScreeen  // ← keep exactly the same as your other files

import android.content.Intent
import android.view.View
import androidx.databinding.DataBindingUtil
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.databinding.SelectSubcategoryScreenBinding
import com.sapotos.ayarental.presentation.cardetails.CarDetails

class SelectSubCategoryScreen : BaseActivity() {

    private lateinit var binding: SelectSubcategoryScreenBinding

    override fun getLayoutResourceId(): View {
        binding = DataBindingUtil.setContentView(this, R.layout.select_subcategory_screen)

        val name = intent.getStringExtra(EXTRA_NAME) ?: "Category"
        val img = intent.getIntExtra(EXTRA_IMG, 0)

        binding.tvTitle.text = name
        if (img != 0) binding.imgCar.setImageResource(img)

        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.luxuryCar.setOnClickListener {
            val i = Intent(this, CarDetails::class.java).apply {
                putExtra(SelectSubCategoryScreen.EXTRA_NAME, name)
                putExtra(SelectSubCategoryScreen.EXTRA_IMG, img)
                putExtra(SelectSubCategoryScreen.EXTRA_PRICE, 50)
            }
            startActivity(i)

        }
        binding.economyCar.setOnClickListener {
            val i = Intent(this, CarDetails::class.java).apply {
                putExtra(SelectSubCategoryScreen.EXTRA_NAME, name)
                putExtra(SelectSubCategoryScreen.EXTRA_IMG, img)
                putExtra(SelectSubCategoryScreen.EXTRA_PRICE, 40)
            }
            startActivity(i)
        }
        binding.compactCar.setOnClickListener {
            val i = Intent(this, CarDetails::class.java).apply {
                putExtra(SelectSubCategoryScreen.EXTRA_NAME, name)
                putExtra(SelectSubCategoryScreen.EXTRA_IMG, img)
                putExtra(SelectSubCategoryScreen.EXTRA_PRICE, 30)
            }
            startActivity(i)
        }
        // TODO: handle row clicks (Luxury/Economy/Compact)
        return binding.root
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_IMG = "extra_img"
        const val EXTRA_PRICE = "extra_price"
    }
}
