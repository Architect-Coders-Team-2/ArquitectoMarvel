package com.architectcoders.arquitectomarvel.ui.detail.characterDetailActivityDi

import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailViewModel
import dagger.Subcomponent

@Subcomponent(modules = [CharacterDetailActivityModule::class])
interface CharacterDetailActivityComponent {
    val characterDetailViewModel: CharacterDetailViewModel
}
