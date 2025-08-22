package com.sapotos.ayarental.presentation.selectCategoryScreeen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sapotos.ayarental.R
import com.sapotos.ayarental.data.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.*

class SelectCategoryScreen_View_Model(application: Application):AndroidViewModel(application),CoroutineScope {


    private val job: Job =Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main +job

    val categories = MutableLiveData(
        listOf(
            Category("SUV", R.drawable.suv),
            Category("Sedan",      R.drawable.convertiblet),
            Category("Hatchback",  R.drawable.suv),
            Category("Convertible",R.drawable.convertiblet)
        )
    )
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}