package com.example.speakez.di

import com.example.speakez.data.repository.SimulationRepositoryImpl
import com.example.speakez.data.repository.TranscriptionRepositoryImpl
import com.example.speakez.domain.repository.SimulationRepository
import com.example.speakez.domain.repository.TranscriptionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSimulationRepository(simulationRepositoryImpl: SimulationRepositoryImpl): SimulationRepository

    @Binds
    abstract fun bindTranscriptionRepository(transcriptionRepositoryImpl: TranscriptionRepositoryImpl): TranscriptionRepository
}
