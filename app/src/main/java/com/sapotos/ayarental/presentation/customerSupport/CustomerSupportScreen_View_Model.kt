package com.sapotos.ayarental.presentation.customerSupport

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class CustomerSupportScreen_View_Model(app: Application) : AndroidViewModel(app) {

    // You can load these from remote/config later
    val phone = MutableLiveData("(704) 555-0127")
    val email = MutableLiveData("kenzilawson@example.com")
}
