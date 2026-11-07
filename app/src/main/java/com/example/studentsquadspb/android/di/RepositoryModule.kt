package com.example.studentsquadspb.android.di

import com.example.studentsquadspb.android.data.SquadRepository
import com.example.studentsquadspb.android.data.SquadRepositoryImpl
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
    abstract fun bindSquadRepository(impl: SquadRepositoryImpl): SquadRepository
}