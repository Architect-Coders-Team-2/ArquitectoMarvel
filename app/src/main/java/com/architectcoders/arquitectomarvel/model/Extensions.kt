package com.architectcoders.arquitectomarvel.model

import android.content.Context
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.arquitectomarvel.R
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.properties.Delegates

val String.md5: String
    get() {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(this.toByteArray())).toString(16).padStart(32, '0')
    }

fun ImageView.loadUrl(url: String?, extension: String? = "") {
    Glide.with(this)
        .asBitmap()
        .load(if (extension.isNullOrEmpty()) url else "$url.$extension")
        .centerCrop()
        .error(R.drawable.marvel_error)
        .into(this)
}

fun FloatingActionButton.loadImage(
    @RawRes @DrawableRes resourceIdActive: Int,
    @RawRes @DrawableRes resourceIdInactive: Int,
    isActive: Boolean
) {
    Glide.with(this)
        .asBitmap()
        .load(if (isActive) resourceIdActive else resourceIdInactive)
        .error(R.drawable.marvel_error)
        .into(this)
}

fun RecyclerView.autoFitColumnsForGridLayout(columnWidthInDP: Float) {
    val numberOfColumns: Int =
        ((resources.displayMetrics.widthPixels / resources.displayMetrics.density) / columnWidthInDP).toInt()
    layoutManager = GridLayoutManager(context, numberOfColumns)
}


fun <T> Context.toast(msgResource: T, length: Int = Toast.LENGTH_SHORT) {
    when (msgResource) {
        is Int -> Toast.makeText(this, msgResource, length).show()
        is String -> Toast.makeText(this, msgResource, length).show()
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> FragmentActivity.getViewModel(crossinline factory: () -> T): T {

    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProvider(this, vmFactory).get()
}

inline fun <reified T : Activity> Context.intentFor(body: Intent.() -> Unit): Intent =
    Intent(this, T::class.java).apply(body)

inline fun <reified T : Activity> Context.startActivity(options: Bundle? = null,
                                                        body: Intent.() -> Unit) {
    startActivity(intentFor<T>(body),options)
}
