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
                    retrieveMockResponse(200, "marvelCharacters.json")
                request.path?.contains("/v1/public/characters/1011334?") == true ->
                    retrieveMockResponse(200, "3DMan.json")
                request.path?.contains("/v1/public/characters/1017100?") == true ->
                    retrieveMockResponse(200, "aBombHas.json")
                request.path?.contains("/v1/public/characters/1009144?") == true ->
                    retrieveMockResponse(200, "aim.json")
                request.path?.contains("/v1/public/characters/1011334/comics?") == true ->
                    retrieveMockResponse(200, "3DManComics.json")
                request.path?.contains("/v1/public/characters/1017100/comics?") == true ->
                    retrieveMockResponse(200, "aBombHasComics.json")
                request.path?.contains("/v1/public/characters/1009144/comics?") == true ->
                    retrieveMockResponse(200, "aimComics.json")
                else -> MockResponse().setResponseCode(400)
            }
        }
    }

    fun retrieveMockResponse(code: Int, json: String): MockResponse =
        MockResponse().setResponseCode(code).setBody(readJsonFile(json))

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
