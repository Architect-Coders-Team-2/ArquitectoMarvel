package com.architectcoders.usecases

import com.architectcoders.data.repository.InternetAvaibleRepo

class UseCaseInternetConnection(private val internetAvaibleRepo: InternetAvaibleRepo) {

    fun invoke(avaible: (Boolean) -> Unit) {
        return internetAvaibleRepo.checkInternet(avaible)
    }
}