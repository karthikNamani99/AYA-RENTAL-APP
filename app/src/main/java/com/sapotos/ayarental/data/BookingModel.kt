package com.sapotos.ayarental.presentation.profile

data class BookingItem(
    val carName: String,
    val bookingId: String,
    val address: String,
    val startLabel: String,   // e.g. "Sat, 7 June, 5:30pm"
    val endLabel: String,     // e.g. "Mon, 9 June, 6:30pm"
    val paidLabel: String,    // e.g. "$660"
    val imageRes: Int         // drawable car
)
