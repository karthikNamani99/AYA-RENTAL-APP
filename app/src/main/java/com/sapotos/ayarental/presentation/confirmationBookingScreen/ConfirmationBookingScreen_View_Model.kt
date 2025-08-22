package com.sapotos.ayarental.presentation.confirmationBookingScreen

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.sapotos.ayarental.constants.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ConfirmationBookingScreen_View_Model(app: Application)
    : AndroidViewModel(app), CoroutineScope {

    private val job = Job()
    override val coroutineContext = Dispatchers.Main + job

    // ---- inputs from Intent ----
    val carName = MutableLiveData("")
    val pricePerDay = MutableLiveData(0)                // keep it numeric (Int)
    val pickupLocation = MutableLiveData("")
    val returnLocation = MutableLiveData("")
    val pickupDateText = MutableLiveData("")            // e.g., "7 December 2021"
    val returnDateText = MutableLiveData("")
//    val carImageRes = MutableLiveData(0)                // drawable res id

    // also allow millis if previous screen passes them (preferred)
    private var pickupMillis: Long? = null
    private var returnMillis: Long? = null

    // ---- derived values ----
    val pricePerDayLabel: LiveData<String> = pricePerDay.map { "\$${it}/day" }
    val perDayAmountText: LiveData<String> = pricePerDay.map { "$" + it }

    val totalDays: LiveData<Int> = object : MutableLiveData<Int>() { init { value = 1 } }
    val totalDaysLabel: LiveData<String> = totalDays.map { d -> "Total $d day rent" }

    val totalAmountText: LiveData<String> = object : MutableLiveData<String>() {}.apply {
        value = "$0"
    }

    /** Call this from Activity after creating the VM */
    fun loadFromIntent(i: Intent) {
        carName.value = i.getStringExtra(Constants.EXTRA_NAME) ?: carName.value

        // 1. Try getting int extra
        val priceFromInt = i.getIntExtra(Constants.EXTRA_PRICE, -1)

        // 2. If not valid, try parsing the string version
        val priceFromString = if (priceFromInt < 0) {
            parsePrice(i.getStringExtra(Constants.EXTRA_PRICE))
        } else priceFromInt

        // 3. Store always as Int
        pricePerDay.value = priceFromString

//    carImageRes.value = i.getIntExtra(Constants.EXTRA_HERO_RES, 0)

        pickupLocation.value = i.getStringExtra(Constants.EXTRA_PICKUP_LOC) ?: ""
        returnLocation.value = i.getStringExtra(Constants.EXTRA_DROP_LOC) ?: ""

        pickupMillis = i.getLongExtra(Constants.EXTRA_PICKUP_MILLIS, 0L).takeIf { it > 0 }
        returnMillis = i.getLongExtra(Constants.EXTRA_DROP_MILLIS, 0L).takeIf { it > 0 }

        pickupDateText.value = i.getStringExtra(Constants.EXTRA_PICKUP_DATE) ?: pickupDateText.value
        returnDateText.value = i.getStringExtra(Constants.EXTRA_DROP_DATE) ?: returnDateText.value

        recomputeTotals()

    }

    private fun parsePrice(raw: String?): Int {
        if (raw.isNullOrBlank()) return 0
        val m = Regex("""\d+""").find(raw) ?: return 0
        return m.value.toInt()
    }

    private fun parseDateToMillis(s: String?): Long? {
        if (s.isNullOrBlank()) return null
        val patterns = listOf(
            "d MMMM yyyy",     // 7 December 2021
            "MMM dd, yyyy",    // Dec 07, 2021
            "yyyy-MM-dd"       // 2021-12-07
        )
        for (p in patterns) {
            try {
                val fmt = java.text.SimpleDateFormat(p, java.util.Locale.getDefault())
                fmt.isLenient = false
                return fmt.parse(s)?.time
            } catch (_: Exception) { /* try next */ }
        }
        return null
    }

    private fun computeDays(startMs: Long, endMs: Long): Int {
        val dayMs = 86_400_000L
        val diff = endMs - startMs
        if (diff <= 0) return 1
        // round up to next day if partial
        val days = ((diff + dayMs - 1) / dayMs).toInt()
        return if (days < 1) 1 else days
    }

    private fun recomputeTotals() {
        val start = pickupMillis ?: parseDateToMillis(pickupDateText.value)
        val end   = returnMillis ?: parseDateToMillis(returnDateText.value)
        val perDay = pricePerDay.value ?: 0

        val days = if (start != null && end != null) computeDays(start, end) else 1
        (totalDays as MutableLiveData).value = days

        (totalAmountText as MutableLiveData).value = "$" + (days * perDay)
    }

    // call when any source changes from UI (optional)
    fun onDatesChanged() = recomputeTotals()
    fun onPriceChanged() = recomputeTotals()

    override fun onCleared() { super.onCleared(); job.cancel() }
}
