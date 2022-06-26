package com.applications.toms.mimetodoplanificado.di

import com.applications.toms.data.repository.ChosenMethodRepository
import com.applications.toms.data.repository.CycleRepository
import com.applications.toms.data.repository.PainScaleRepository
import com.applications.toms.data.source.LocalCycleDataSource
import com.applications.toms.data.source.LocalMethodDataSource
import com.applications.toms.data.source.LocalPainScaleDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun chosenMethodRepositoryProvider(localDataSource: LocalMethodDataSource) =
        ChosenMethodRepository(localDataSource)

    @Provides
    @Singleton
    fun cycleRepositoryProvider(localCycleDataSource: LocalCycleDataSource) =
        CycleRepository(localCycleDataSource)

    @Provides
    @Singleton
    fun painScaleRepositoryProvider(localDataSource: LocalPainScaleDataSource) =
        PainScaleRepository(localDataSource)

}