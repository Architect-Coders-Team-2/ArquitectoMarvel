package com.architectcoders.arquitectomarvel.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CredentialsEntity(
    @PrimaryKey
    val id: Int = 0,
    val password: String,
    val recoveryHint: String
)
