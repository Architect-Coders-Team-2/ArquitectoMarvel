package com.architectcoders.arquitectomarvel.ui

import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("loading")
fun ProgressBar.loading(isLoading: Boolean?) {
    isVisible = isLoading ?: false
}
