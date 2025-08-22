package com.sapotos.ayarental.presentation.fragments.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class ProfileFragment_View_Model(app: Application)
    : AndroidViewModel(app), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val name = MutableLiveData("Cameron Williamson")
    val phone = MutableLiveData("(219) 555-0114")

    override fun onCleared() { super.onCleared(); job.cancel() }
}
