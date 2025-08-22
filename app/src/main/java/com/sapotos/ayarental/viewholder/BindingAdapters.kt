package com.sapotos.ayarental.presentation.common

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.sapotos.ayarental.R

@BindingAdapter("imageUri")
fun ImageView.bindImageUri(uri: Uri?) {
    if (uri != null) setImageURI(uri) else setImageResource(R.drawable.ic_user)
}
