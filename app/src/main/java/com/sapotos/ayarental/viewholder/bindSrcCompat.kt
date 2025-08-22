package com.sapotos.ayarental.ui.binding

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("srcCompat")
fun bindSrcCompat(view: ImageView, @DrawableRes resId: Int?) {
    if (resId != null && resId != 0) {
        view.setImageResource(resId)
    } else {
        view.setImageDrawable(null)
    }
}
