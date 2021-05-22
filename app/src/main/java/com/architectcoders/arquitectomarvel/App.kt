package com.architectcoders.arquitectomarvel

import android.app.Application
import android.util.Log
import com.architectcoders.arquitectomarvel.di.DaggerMarvelComponent
import com.architectcoders.arquitectomarvel.di.MarvelComponent
import org.jetbrains.annotations.NotNull
import timber.log.Timber

class App: Application() {

    lateinit var component: MarvelComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerMarvelComponent
            .factory()
            .create(this)


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
//            crashlytics().setCustomKey(
//                "Log.ERROR", message
//            )
        } else if (priority == Log.WARN) {
//            crashlytics().setCustomKey(
//                "Log.WARN", message
//            )
        }
    }
}
