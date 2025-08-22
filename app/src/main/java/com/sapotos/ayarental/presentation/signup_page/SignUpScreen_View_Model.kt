package com.sapotos.ayarental.presentation.signup_page

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class SignUpScreen_View_Model(application: Application):AndroidViewModel(application),CoroutineScope {


    private val job: Job =Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main +job





    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}