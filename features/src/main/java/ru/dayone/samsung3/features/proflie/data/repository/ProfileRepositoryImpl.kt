package ru.dayone.samsung3.features.proflie.data.repository

import ru.dayone.samsung3.features.proflie.domain.data.local.LocalProfileRepository
import ru.dayone.samsung3.features.proflie.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val localRepository: LocalProfileRepository
) : ProfileRepository {
    override suspend fun getLocalImageUri(): String {
        return localRepository.getAvatarUri()
    }

    override suspend fun saveLocalAvatarUri(uri: String): Boolean {
        return localRepository.saveAvatarUri(uri)
    }
}