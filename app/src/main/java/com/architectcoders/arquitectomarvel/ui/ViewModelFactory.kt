package com.architectcoders.arquitectomarvel.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.architectcoders.data.repository.CharacterRepository

class ViewModelFactory(private val characterRepository: CharacterRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.getConstructor(characterRepository::class.java)
            .newInstance(characterRepository)
}
