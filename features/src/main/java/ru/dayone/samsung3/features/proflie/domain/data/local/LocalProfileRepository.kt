package ru.dayone.samsung3.features.proflie.domain.data.local

interface LocalProfileRepository {
    suspend fun getAvatarUri() : String

    suspend fun saveAvatarUri(uri: String) : Boolean
}