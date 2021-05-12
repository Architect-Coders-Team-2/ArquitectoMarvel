package com.architectcoders.arquitectomarvel.ui.main.mainActivityDi

import com.architectcoders.arquitectomarvel.ui.main.MainViewModel
import dagger.Subcomponent

@Subcomponent(modules = [MainActivityModule::class])
interface MainActivityComponent {
    val mainViewModel: MainViewModel
}
