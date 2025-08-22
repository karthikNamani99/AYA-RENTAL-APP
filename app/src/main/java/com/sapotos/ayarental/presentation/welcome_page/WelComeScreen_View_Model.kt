package com.sapotos.ayarental.presentation.welcome_page

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sapotos.ayarental.interfaces.NavigationInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class WelComeScreen_View_Model(application: Application):AndroidViewModel(application),CoroutineScope {


    private val job: Job =Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main +job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}