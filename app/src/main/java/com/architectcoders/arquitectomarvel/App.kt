package com.architectcoders.arquitectomarvel

import android.app.Application
import android.util.Log
import com.architectcoders.arquitectomarvel.di.ArquitectoMarvelComponent
import com.architectcoders.arquitectomarvel.di.DaggerArquitectoMarvelComponent
import org.jetbrains.annotations.NotNull
import timber.log.Timber

class App : Application() {

    lateinit var component: ArquitectoMarvelComponent
        private set

    override fun onCreate() {
        super.onCreate()
        component = DaggerArquitectoMarvelComponent.factory().create(this)
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
