package com.sapotos.ayarental.presentation.fragments.homeScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sapotos.ayarental.interfaces.NavigationInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.coroutines.CoroutineContext

class SearchVehicleFragment_View_Model(application: Application):AndroidViewModel(application),CoroutineScope {


    private val job: Job =Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main +job
    // Form fields (observable)


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}