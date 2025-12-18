package com.example.speakez.di

import com.example.speakez.data.remote.OpenAiService
import com.example.speakez.domain.service.LLMService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun bindLlmService(openAiService: OpenAiService): LLMService
}
