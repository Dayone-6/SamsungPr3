package ru.dayone.samsung3.features.proflie.domain.repository

interface ProfileRepository {
    suspend fun getLocalImageUri() : String

    suspend fun saveLocalAvatarUri(uri: String) : Boolean
}