package ru.dayone.samsung3.features.proflie.data.local.repository

import android.content.SharedPreferences
import ru.dayone.common.utils.KEY_AVATAR_URI
import ru.dayone.samsung3.features.proflie.domain.data.local.LocalProfileRepository
import javax.inject.Inject

class LocalProfileRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences
) : LocalProfileRepository {
    override suspend fun getAvatarUri(): String {
        return prefs.getString(KEY_AVATAR_URI, "") ?: ""
    }

    override suspend fun saveAvatarUri(uri: String) : Boolean{
        return prefs.edit().putString(KEY_AVATAR_URI, uri).commit()
    }
}