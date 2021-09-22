package com.architectcoders.arquitectomarvel.ui.common

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {

    val idlingResource = CountingIdlingResource("countingIdlingResource")

    init {
        idlingResource.dumpStateToLogs()
    }

    fun increment() = idlingResource.increment()

    fun decrement() {
        if (!idlingResource.isIdleNow) {
            idlingResource.decrement()
        }
    }
}

inline fun <T> wrapEspressoIdlingResource(function: () -> T): T {
    EspressoIdlingResource.increment()
    return try {
        function()
    } finally {
        EspressoIdlingResource.decrement()
    }
}
