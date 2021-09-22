package com.architectcoders.arquitectomarvel.utils

import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import timber.log.Timber
import java.io.IOException

class MockWebserverDispatcher {

    internal inner class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when {
                request.path.isNullOrBlank() -> MockResponse().setResponseCode(400)
                request.path?.contains("/v1/public/characters?") == true ->
                    MockResponse().setResponseCode(200)
                        .setBody(readJsonFile("marvelCharacters.json"))
                request.path?.contains("/v1/public/characters/1011334?") == true ->
                    MockResponse().setResponseCode(200).setBody(readJsonFile("3DMan.json"))
                request.path?.contains("/v1/public/characters/1017100?") == true ->
                    MockResponse().setResponseCode(200).setBody(readJsonFile("aBombHas.json"))
                request.path?.contains("/v1/public/characters/1009144?") == true ->
                    MockResponse().setResponseCode(200).setBody(readJsonFile("aim.json"))
                request.path?.contains("/v1/public/characters/1011334/comics?") == true ->
                    MockResponse().setResponseCode(200).setBody(readJsonFile("3DManComics.json"))
                request.path?.contains("/v1/public/characters/1017100/comics?") == true ->
                    MockResponse().setResponseCode(200).setBody(readJsonFile("aBombHasComics.json"))
                request.path?.contains("/v1/public/characters/1009144/comics?") == true ->
                    MockResponse().setResponseCode(200).setBody(readJsonFile("aimComics.json"))
                else -> MockResponse().setResponseCode(400)
            }
        }
    }

    internal inner class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse =
            MockResponse().setResponseCode(400)
    }

    private fun readJsonFile(jsonFile: String): String {
        val context = InstrumentationRegistry.getInstrumentation().context
        return try {
            context.assets.open(jsonFile).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            Timber.e(ioException)
            ""
        }
    }
}
