package com.architectcoders.arquitectomarvel

import android.app.Application
import android.util.Log
import androidx.viewbinding.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import org.jetbrains.annotations.NotNull
import timber.log.Timber

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }
}

/** todo: Consultar con el equipo. Eliminar si no se considera
 * Personalizamos los eventos de Timber para la release build de la app
 * De forma que podemos reutilizar estas llamadas para monitorear desde crashlitics u otro sistema
 */
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
