package com.architectcoders.arquitectomarvel

import android.app.Application
import android.content.Context
import android.util.Log
import com.architectcoders.arquitectomarvel.model.CredentialApiRepositoryImpl
import com.architectcoders.arquitectomarvel.model.RetrofitDataSource
import com.architectcoders.arquitectomarvel.model.database.ResultDatabase
import com.architectcoders.arquitectomarvel.model.database.RoomDataSource
import com.architectcoders.module.data.CredentialsApiRepository
import com.architectcoders.module.data.LocalDataSource
import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.data.RemoteDataSource
import org.jetbrains.annotations.NotNull
import timber.log.Timber

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

fun getRepository(context: Context): MarvelRepository {
    val roomDataSource: LocalDataSource = RoomDataSource(ResultDatabase.getInstance(context))
    val credentialsApiRepository: CredentialsApiRepository = CredentialApiRepositoryImpl()
    val retrofitDataSource: RemoteDataSource = RetrofitDataSource(credentialsApiRepository)
    return MarvelRepository(
        roomDataSource,
        retrofitDataSource,
        credentialsApiRepository
    )
}
