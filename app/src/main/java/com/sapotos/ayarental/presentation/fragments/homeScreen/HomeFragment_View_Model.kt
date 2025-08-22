package com.sapotos.ayarental.presentation.fragments.homeScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sapotos.ayarental.interfaces.NavigationInterface
import com.sapotos.ayarental.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.coroutines.CoroutineContext

class HomeFragment_View_Model(application: Application):AndroidViewModel(application),CoroutineScope {


    private val job: Job =Job()
    private val _openResults = MutableLiveData<Event<Unit>>()
    val openResults: LiveData<Event<Unit>> = _openResults
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main +job
    // Form fields (observable)
    val pickAt = MutableLiveData("")
    val dropAt = MutableLiveData("")
    val pickDate = MutableLiveData("")
    val pickTime = MutableLiveData("")
    val dropDate = MutableLiveData("")
    val dropTime = MutableLiveData("")

    fun onPickLocationClick(isPickup: Boolean) { /* open place picker */ }

    fun onSearchClick() { /* validate + navigate */
        _openResults.value = Event(Unit)
    }

    // Keep the full selection if you need it later
    var pickDateTimeMillis: Long = System.currentTimeMillis()
    var dropDateTimeMillis: Long = System.currentTimeMillis()

    // Will be set by Activity
    var routingListener: NavigationInterface? = null

    // Called from XML
    fun onPickDateClick(isPickup: Boolean) { routingListener?.requestDateTimePickUp(isPickup) }
    fun onPickTimeClick(isPickup: Boolean) { routingListener?.requestDateTimePickUp(isPickup) }
    fun onDropDateClick(isDrop: Boolean)   { routingListener?.requestDateTimePickUp(false) }
    fun onDropTimeClick(isDrop: Boolean)   { routingListener?.requestDateTimePickUp(false) }

    /** Activity calls this after user selected date *and* time */
    fun setDateTime(isPickup: Boolean, millis: Long) {
        val cal = Calendar.getInstance().apply { timeInMillis = millis }
        val d = SimpleDateFormat("MMM dd, yyyy", Locale.US).format(cal.time)
        val t = SimpleDateFormat("hh:mm a",   Locale.US).format(cal.time)
        if (isPickup) {
            pickDate.value = d; pickTime.value = t; pickDateTimeMillis = millis
        } else {
            dropDate.value = d; dropTime.value = t;  dropDateTimeMillis = millis
        }
    }



    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}