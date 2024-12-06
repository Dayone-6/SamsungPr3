package ru.dayone.samsung3.features.proflie.data.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.dayone.samsung3.features.proflie.data.local.repository.LocalProfileRepositoryImpl
import ru.dayone.samsung3.features.proflie.data.repository.ProfileRepositoryImpl
import ru.dayone.samsung3.features.proflie.domain.repository.ProfileRepository


@Module
@InstallIn(SingletonComponent::class)
class ProfileModule {

    @Provides
    fun provideProfileRepository(localProfileRepositoryImpl: LocalProfileRepositoryImpl): ProfileRepository = ProfileRepositoryImpl(localProfileRepositoryImpl)

    @Provides
    fun provideLocalRepository(prefs: SharedPreferences) = LocalProfileRepositoryImpl(prefs)

    @Provides
    fun providePrefs(@ApplicationContext context: Context) = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
}