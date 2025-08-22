package com.sapotos.ayarental.presentation.booking

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class BookingConfirmation_View_Model(app: Application)
    : AndroidViewModel(app), CoroutineScope {
    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PRICE = "extra_price"
        const val EXTRA_HERO_RES = "extra_hero_res"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_PICKUP_LOC = "extra_pickup_loc"
        const val EXTRA_DROP_LOC = "extra_drop_loc"
        const val EXTRA_PICKUP_DATE = "extra_pickup_date"
        const val EXTRA_DROP_DATE = "extra_drop_date" // IntArrayList
    }
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val withDriver = MutableLiveData(false)

    val pickLocation = MutableLiveData("")
    val returnLocation = MutableLiveData("")
    val pickDateTime = MutableLiveData("")
    val returnDateTime = MutableLiveData("")

    var pickMillis: Long = System.currentTimeMillis()
    var returnMillis: Long = System.currentTimeMillis()

    fun isFormValid(): Boolean =
        !pickLocation.value.isNullOrBlank() &&
                !returnLocation.value.isNullOrBlank() &&
                !pickDateTime.value.isNullOrBlank() &&
                !returnDateTime.value.isNullOrBlank()

    override fun onCleared() {
        super.onCleared(); job.cancel()
    }
}
