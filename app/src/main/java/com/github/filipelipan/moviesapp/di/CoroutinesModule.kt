package com.github.filipelipan.moviesapp.di

import com.github.filipelipan.moviesapp.infraestructure.coroutines.CoroutineDispatcherProvider
import com.github.filipelipan.moviesapp.infraestructure.coroutines.CoroutineDispatcherProviderDefault
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @Singleton
    @Provides
    fun getCoroutineDispatcherProvider(): CoroutineDispatcherProvider {
        return CoroutineDispatcherProviderDefault()
    }
}
