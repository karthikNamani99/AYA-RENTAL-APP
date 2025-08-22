package com.sapotos.ayarental.presentation.cardetails

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sapotos.ayarental.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

data class Spec(val iconRes: Int, val title: String, val value: String)

class CarDetailsScreen_View_Model(application: Application)
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

    // Exposed state
    private val _name = MutableLiveData("Tesla Model 3")
    val name: LiveData<String> = _name

    private val _pricePerDay = MutableLiveData("$30/day")
    val pricePerDay: LiveData<String> = _pricePerDay

    private val _description = MutableLiveData(
        "A car with high specs that are rented at an affordable price"
    )
    val description: LiveData<String> = _description

    private val _heroRes = MutableLiveData(R.drawable.suv) // fallback drawable
    val heroRes: LiveData<Int> = _heroRes

    private val _specs = MutableLiveData(defaultSpecs())
    val specs: LiveData<List<Spec>> = _specs

    private val _gallery = MutableLiveData(defaultGallery())
    val gallery: LiveData<List<Int>> = _gallery

    fun loadFromIntent(i: Intent?) {
        if (i == null) return
        i.getStringExtra(EXTRA_NAME)?.let { _name.value = it }
        i.getStringExtra(EXTRA_PRICE)?.let { _pricePerDay.value = it }
        i.getIntExtra(EXTRA_HERO_RES, 0).takeIf { it != 0 }?.let { _heroRes.value = it }
        i.getStringExtra(EXTRA_DESC)?.let { _description.value = it }
        i.getIntegerArrayListExtra(EXTRA_GALLERY)?.let { _gallery.value = it }
    }

    private fun defaultSpecs(): List<Spec> = listOf(
        Spec(R.drawable.clock, "TOP SPEED", "250 km/h"),
        Spec(R.drawable.ic_wifi, "WIFI", "Yes"),
        Spec(R.drawable.ic_seat, "SEATS", "5"),
        Spec(R.drawable.ic_sensor, "SENSOR", "Adaptive"),
        Spec(R.drawable.ic_bluetooth, "BLUETOOTH", "Yes"),
        Spec(R.drawable.ic_door, "DOOR", "4")
    )

    private fun defaultGallery(): List<Int> = listOf(
        R.drawable.suv, R.drawable.suv,
        R.drawable.suv, R.drawable.suv
    )

    override fun onCleared() {
        super.onCleared(); job.cancel()
    }
}
