package com.example.speakez.di

import com.example.speakez.data.repository.AudioRepositoryImpl
import com.example.speakez.domain.repository.AudioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AudioModule {

    @Provides
    @Singleton
    fun provideAudioRepository(impl: AudioRepositoryImpl): AudioRepository = impl
}
