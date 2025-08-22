package com.sapotos.ayarental.presentation.fragments.hostCarFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class HostCarFragment_View_Model(application: Application):AndroidViewModel(application),CoroutineScope {


    private val job: Job =Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main +job
    // Form fields (observable)



    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}