package com.example.studentsquadspb.android.di

import com.example.studentsquadspb.android.data.remote.ProfileRepository
import com.example.studentsquadspb.android.data.remote.ProfileRepositoryImpl
import com.example.studentsquadspb.android.data.remote.SquadListRepository
import com.example.studentsquadspb.android.data.remote.SquadListRepositoryImpl
import com.example.studentsquadspb.android.data.SquadRepository
import com.example.studentsquadspb.android.data.SquadRepositoryImpl
import com.example.studentsquadspb.android.data.remote.SquadDetailRepository
import com.example.studentsquadspb.android.data.remote.SquadDetailRepositoryImpl
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

    // список отрядов (с сервера)
    @Binds
    @Singleton
    abstract fun bindSquadListRepository(impl: SquadListRepositoryImpl): SquadListRepository

    @Binds
    @Singleton
    abstract fun bindSquadDetailRepository(impl: SquadDetailRepositoryImpl): SquadDetailRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository
}