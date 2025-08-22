package com.sapotos.ayarental.presentation.login_page

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class LoginScreen_View_Model(app: Application)
    : AndroidViewModel(app), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    // Inputs
    val email = MutableLiveData("")
    val password = MutableLiveData("")

    // Errors (null/empty = no error)
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()

    // One-shot success flag
    val loginSuccess = MutableLiveData<Boolean>()

    fun onLoginClick() {
        val e = email.value?.trim().orEmpty()
        val p = password.value.orEmpty()

        // --- Email rules: required, <= 100 chars, valid email ---
        emailError.value = when {
            e.isEmpty() -> "Email is required"
            e.length > 100 -> "Max 100 characters"
            !Patterns.EMAIL_ADDRESS.matcher(e).matches() -> "Enter a valid email"
            else -> null
        }

        // --- Password rules: at least 6 characters (\"above 5\"), you can cap at 50 ---
        passwordError.value = when {
            p.isEmpty() -> "Password is required"
            p.length < 6 -> "Password must be at least 6 characters"
            p.length > 50 -> "Max 50 characters"
            else -> null
        }

        loginSuccess.value = emailError.value == null && passwordError.value == null
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
