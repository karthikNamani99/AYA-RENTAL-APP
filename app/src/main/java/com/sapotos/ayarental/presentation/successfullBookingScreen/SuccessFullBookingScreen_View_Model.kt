// com/sapotos/ayarental/presentation/successfullBookingScreen/SuccessFullBookingScreen_View_Model.kt
package com.sapotos.ayarental.presentation.successfullBookingScreen

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.sapotos.ayarental.constants.Constants
import kotlinx.coroutines.*

class SuccessFullBookingScreen_View_Model(app: Application)
    : AndroidViewModel(app), CoroutineScope {

    private val job = Job()
    override val coroutineContext = Dispatchers.Main + job

    // Inputs
    val carName = MutableLiveData("")
    val pricePerDay = MutableLiveData(0)
    val pickupLoc = MutableLiveData("")
    val dropLoc = MutableLiveData("")
    val pickupDateText = MutableLiveData("")
    val dropDateText = MutableLiveData("")

    // Derived
    val pickupReturnLine: LiveData<String> = pickupLoc.map { p ->
        val d = dropLoc.value.orEmpty()
        if (p.equals(d, true))
            "$p - Same location"
        else
            "$p — $d"
    }

    val tripDatesLine: LiveData<String> = pickupDateText.map { s ->
        val e = dropDateText.value.orEmpty()
        if (s.isBlank() || e.isBlank()) (s + e) else "$s - $e"
    }

    val oneDayRentLine: LiveData<String> = pricePerDay.map { "One day rent: $${it}" }

    val totalDays = MutableLiveData(1)
    val daysAmount = MutableLiveData(0)

    val totalDaysLine: LiveData<String> = totalDays.map { d ->
        "Total of $d day${if (d>1) "s" else ""}: $${daysAmount.value ?: 0}"
    }
    val totalFeesLine: LiveData<String> = daysAmount.map { "Total Fees: $${it}.00" }

    fun loadFromIntent(i: Intent) {
        // Keys used by your previous screen
        val name = i.getStringExtra("extra_name") ?: i.getStringExtra( Constants.EXTRA_NAME)
        val price = i.getIntExtra("extra_price_per_day", i.getIntExtra( Constants.EXTRA_PRICE, 0))
        val pLoc = i.getStringExtra("extra_pickup_loc") ?: i.getStringExtra( Constants.EXTRA_PICKUP_LOC)
        val dLoc = i.getStringExtra("extra_return_loc") ?: i.getStringExtra( Constants.EXTRA_DROP_LOC)
        val pDate = i.getStringExtra("extra_pickup_date_text") ?: i.getStringExtra( Constants.EXTRA_PICKUP_DATE)
        val dDate = i.getStringExtra("extra_return_date_text") ?: i.getStringExtra( Constants.EXTRA_DROP_DATE)

        carName.value = name ?: ""
        pricePerDay.value = price
        pickupLoc.value = pLoc ?: ""
        dropLoc.value = dLoc ?: ""
        pickupDateText.value = pDate ?: ""
        dropDateText.value = dDate ?: ""

        // Try reading millis (if provided); otherwise, estimate 1 day when texts are missing.
        val startMs = i.getLongExtra(Constants.EXTRA_PICKUP_MILLIS, -1)
        val endMs   = i.getLongExtra(Constants.EXTRA_DROP_MILLIS, -1)

        val days = when {
            startMs > 0 && endMs > startMs -> ((endMs - startMs + 86_400_000L - 1)/86_400_000L).toInt()
            else -> 1
        }
        totalDays.value = if (days < 1) 1 else days
        daysAmount.value = (pricePerDay.value ?: 0) * (totalDays.value ?: 1)
    }

    override fun onCleared() { super.onCleared(); job.cancel() }
}
