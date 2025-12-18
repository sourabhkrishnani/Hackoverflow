package com.example.speakez.di

import com.example.speakez.data.repository.AudioRepositoryImpl
import com.example.speakez.data.repository.SimulationRepositoryImpl
import com.example.speakez.domain.repository.AudioRepository
import com.example.speakez.domain.repository.SimulationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAudioRepository(audioRepositoryImpl: AudioRepositoryImpl): AudioRepository

    @Binds
    @Singleton
    abstract fun bindSimulationRepository(simulationRepositoryImpl: SimulationRepositoryImpl): SimulationRepository
}
