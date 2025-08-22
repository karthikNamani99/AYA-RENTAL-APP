package com.sapotos.ayarental.presentation.idetificationScreen

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.sapotos.ayarental.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

enum class DocumentType { NATIONAL_ID, PASSPORT }

class IdentificationScreen_View_Model(app: Application)
    : AndroidViewModel(app), CoroutineScope {
    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PRICE = "extra_price"
        const val EXTRA_HERO_RES = "extra_hero_res"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_PICKUP_LOC = "extra_pickup_loc"
        const val EXTRA_DROP_LOC = "extra_drop_loc"
        const val EXTRA_PICKUP_DATE = "extra_pickup_date"
        const val EXTRA_DROP_DATE = "extra_drop_date" // IntArrayList
    }
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val docType = MutableLiveData(DocumentType.NATIONAL_ID)
    val idNumber = MutableLiveData("")

    val frontUri = MutableLiveData<Uri?>(null)
    val backUri  = MutableLiveData<Uri?>(null)

    fun isValid(): Boolean = !idNumber.value.isNullOrBlank()

    val idHint: LiveData<String> = docType.map { kind ->
        if (kind == DocumentType.NATIONAL_ID)
            getApplication<Application>().getString(R.string.national_id_number)
        else
            getApplication<Application>().getString(R.string.passport_number)
    }

    override fun onCleared() { super.onCleared(); job.cancel() }
}
