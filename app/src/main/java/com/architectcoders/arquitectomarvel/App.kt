package com.architectcoders.arquitectomarvel

import android.app.Application
import android.util.Log
import org.jetbrains.annotations.NotNull
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }
}

class ReleaseTree : @NotNull Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR) {
            // TODO
        } else if (priority == Log.WARN) {
            // TODO
        }
    }
}
