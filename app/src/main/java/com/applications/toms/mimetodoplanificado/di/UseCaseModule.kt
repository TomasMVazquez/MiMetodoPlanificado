package com.applications.toms.mimetodoplanificado.di

import com.applications.toms.data.repository.ChosenMethodRepository
import com.applications.toms.usecases.SaveChosenMethodUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun saveChosenMethodProvider(chosenMethodRepository: ChosenMethodRepository) =
        SaveChosenMethodUseCase(chosenMethodRepository)

}