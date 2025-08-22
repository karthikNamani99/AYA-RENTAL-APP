package com.sapotos.ayarental.presentation.signup_page

import android.app.Application
import android.content.Intent
import android.util.Patterns
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sapotos.ayarental.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.coroutines.CoroutineContext

class SignUpPageMain_View_Model(application: Application):AndroidViewModel(application),CoroutineScope {


    private val job: Job =Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main +job



    // Form fields (two-way bound)
    val fullName = MutableLiveData("")
    val email = MutableLiveData("")
    val mobile = MutableLiveData("")
    val password = MutableLiveData("")
    val confirmPassword = MutableLiveData("")
    val termsAccepted = MutableLiveData(false)

    // Errors
    val nameError = MutableLiveData<String?>(null)
    val emailError = MutableLiveData<String?>(null)
    val mobileError = MutableLiveData<String?>(null)
    val passwordError = MutableLiveData<String?>(null)
    val confirmPasswordError = MutableLiveData<String?>(null)

    // Actions from UI


    sealed class Nav { object ToHome : Nav() /* add more destinations here */ }

    private val _nav = MutableLiveData<Event<Nav>>()   // <-- this is the 'nav' you were missing
    val nav: LiveData<Event<Nav>> get() = _nav

    fun onRegisterClick() {
        clearErrors()
        if (validateAll()) {
            // TODO call API, then on success:
            _nav.value = Event(Nav.ToHome)
        }
    }

    private fun validateAll(): Boolean {
        var ok = true
        val name = fullName.value?.trim().orEmpty()
        val mail = email.value?.trim().orEmpty()
        val phone = mobile.value?.trim().orEmpty()
        val pass = password.value.orEmpty()
        val cpass = confirmPassword.value.orEmpty()

        if (name.isEmpty()) { nameError.value = "Enter full name"; ok = false }
        else if (name.length > 100) { nameError.value = "Max 100 characters"; ok = false }

        if (mail.isEmpty()) { emailError.value = "Enter email"; ok = false }
        else if (mail.length > 100) { emailError.value = "Max 100 characters"; ok = false }
        else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) { emailError.value = "Invalid email"; ok = false }

        if (!Regex("^[0-9]{10}$").matches(phone)) { mobileError.value = "Enter 10-digit mobile"; ok = false }

        if (pass.length < 5) { passwordError.value = "Min 5 characters"; ok = false }
        else if (pass.length > 20) { passwordError.value = "Max 20 characters"; ok = false }

        if (cpass != pass) { confirmPasswordError.value = "Passwords do not match"; ok = false }

        if (termsAccepted.value != true) ok = false

        return ok
    }

    private fun clearErrors() {
        nameError.value = null
        emailError.value = null
        mobileError.value = null
        passwordError.value = null
        confirmPasswordError.value = null
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}