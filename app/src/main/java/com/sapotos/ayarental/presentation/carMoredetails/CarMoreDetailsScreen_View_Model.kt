package com.sapotos.ayarental.presentation.carMoredetails

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sapotos.ayarental.R
import com.sapotos.ayarental.presentation.cardetails.CarDetailsScreen_View_Model
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class CarMoreDetailsScreen_View_Model(application: Application)
    : AndroidViewModel(application), CoroutineScope {
    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PRICE = "extra_price"
        const val EXTRA_HERO_RES = "extra_hero_res"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_GALLERY = "extra_gallery" // IntArrayList
    }
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    private val _name = MutableLiveData("Tesla Model 3")
    val name: LiveData<String> = _name

    private val _pricePerDay = MutableLiveData("$30/day")
    val pricePerDay: LiveData<String> = _pricePerDay

    private val _description = MutableLiveData(
        "A car with high specs that are rented at an affordable price"
    )
    val description: LiveData<String> = _description

    private val _heroRes = MutableLiveData(R.drawable.suv)
    val heroRes: LiveData<Int> = _heroRes

    private val _gallery = MutableLiveData<List<Int>>(emptyList())
    val gallery: LiveData<List<Int>> = _gallery

    fun loadFromIntent(i: Intent?) {
        if (i == null) return
        i.getStringExtra(CarDetailsScreen_View_Model.EXTRA_NAME)?.let { _name.value = it }
        i.getStringExtra(CarDetailsScreen_View_Model.EXTRA_PRICE)?.let { _pricePerDay.value = it }
        i.getIntExtra(CarDetailsScreen_View_Model.EXTRA_HERO_RES, 0).takeIf { it != 0 }?.let { _heroRes.value = it }
        i.getStringExtra(CarDetailsScreen_View_Model.EXTRA_DESC)?.let { _description.value = it }
        i.getIntegerArrayListExtra(CarDetailsScreen_View_Model.EXTRA_GALLERY)?.let { _gallery.value = it }
            ?: run { _gallery.value = defaultGallery() }
    }

    private fun defaultGallery(): List<Int> = listOf(
        R.drawable.suv, R.drawable.suv,
        R.drawable.suv, R.drawable.suv
    )

    override fun onCleared() {
        super.onCleared(); job.cancel()
    }
}
