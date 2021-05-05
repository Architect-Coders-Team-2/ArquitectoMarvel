package com.architectcoders.arquitectomarvel.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.arquitectomarvel.model.characters.Result

@BindingAdapter("items")
fun RecyclerView.setItems(itemList: List<Result>?) {
    (adapter as? AdapterList)?.submitList(itemList ?: emptyList())
}
