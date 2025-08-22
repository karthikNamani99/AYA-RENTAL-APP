package com.sapotos.ayarental.presentation.fragments.bookingFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sapotos.ayarental.presentation.profile.BookingItem
import com.sapotos.ayarental.R

class BookingFragment_View_Model(app: Application) : AndroidViewModel(app) {

    private val _bookings = MutableLiveData<List<BookingItem>>(emptyList())
    val bookings: LiveData<List<BookingItem>> = _bookings

    fun loadSample() {
        _bookings.value = listOf(
            BookingItem(
                carName = "Mercedes-Benz",
                bookingId = "CZ2215",
                address = "2464 Royal Ln. Mesa, New Jersey 45463",
                startLabel = "Sat, 7 June, 5:30pm",
                endLabel = "Mon, 9 June, 6:30pm",
                paidLabel = "$660",
                imageRes = R.drawable.suv
            ),
            BookingItem(
                carName = "Mercedes-Benz",
                bookingId = "CZ2216",
                address = "2464 Royal Ln. Mesa, New Jersey 45463",
                startLabel = "Tue, 11 June, 10:00am",
                endLabel = "Thu, 13 June, 4:15pm",
                paidLabel = "$540",
                imageRes = R.drawable.suv
            )
        )
    }

    fun delete(item: BookingItem) {
        _bookings.value = _bookings.value?.toMutableList()?.apply { remove(item) }
    }
}
