package com.kirabium.relayance.di

import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideCustomerRepository(): CustomerRepository {
        return CustomerRepository()
    }
}
