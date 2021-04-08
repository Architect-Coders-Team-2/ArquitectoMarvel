package com.architectcoders.arquitectomarvel.model

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.arquitectomarvel.R
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.math.BigInteger
import java.security.MessageDigest

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
