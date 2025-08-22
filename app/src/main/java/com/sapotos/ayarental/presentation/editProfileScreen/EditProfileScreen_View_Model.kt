package com.sapotos.ayarental.presentation.editProfileScreen

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class EditProfileScreen_View_Model(app: Application) :
    AndroidViewModel(app), CoroutineScope {

    val name = MutableLiveData("")
    val email = MutableLiveData("")
    val phone = MutableLiveData("")
    val address = MutableLiveData("")

    val photoUri = MutableLiveData<Uri?>(null)

    private val job: Job = Job()
    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + job

    fun setPhotoUri(uri: Uri) { photoUri.value = uri }
    fun clearPhoto() { photoUri.value = null }

    override fun onCleared() {
        super.onCleared(); job.cancel()
    }
}
