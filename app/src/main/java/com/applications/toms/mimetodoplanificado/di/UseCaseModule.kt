package com.applications.toms.mimetodoplanificado.di

import com.applications.toms.data.repository.ChosenMethodRepository
import com.applications.toms.data.repository.CycleRepository
import com.applications.toms.data.repository.PainScaleRepository
import com.applications.toms.usecases.cycle.DeleteCycleUseCase
import com.applications.toms.usecases.cycle.GetCycleUseCase
import com.applications.toms.usecases.cycle.SaveCycleUseCase
import com.applications.toms.usecases.method.DeleteChosenMethodUseCase
import com.applications.toms.usecases.method.GetChosenMethodUseCase
import com.applications.toms.usecases.method.SaveChosenMethodUseCase
import com.applications.toms.usecases.method.UpdateChosenMethodUseCase
import com.applications.toms.usecases.painscale.DeletePainScaleUseCase
import com.applications.toms.usecases.painscale.GetLineChartHistoryUseCase
import com.applications.toms.usecases.painscale.GetPainScaleHistoryUseCase
import com.applications.toms.usecases.painscale.SavePainScaleUseCase
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

    @Provides
    @ViewModelScoped
    fun getChosenMethodUseCase(chosenMethodRepository: ChosenMethodRepository) =
        GetChosenMethodUseCase(chosenMethodRepository)

    @Provides
    @ViewModelScoped
    fun deleteChosenMethodUseCase(chosenMethodRepository: ChosenMethodRepository) =
        DeleteChosenMethodUseCase(chosenMethodRepository)

    @Provides
    @ViewModelScoped
    fun updateChosenMethodUseCase(chosenMethodRepository: ChosenMethodRepository) =
        UpdateChosenMethodUseCase(chosenMethodRepository)

    @Provides
    @ViewModelScoped
    fun getCycleUseCase(cycleRepository: CycleRepository) =
        GetCycleUseCase(cycleRepository)

    @Provides
    @ViewModelScoped
    fun saveCycleUseCase(cycleRepository: CycleRepository) =
        SaveCycleUseCase(cycleRepository)

    @Provides
    @ViewModelScoped
    fun deleteCycleUseCase(cycleRepository: CycleRepository) =
        DeleteCycleUseCase(cycleRepository)

    @Provides
    @ViewModelScoped
    fun savePainScaleUseCase(painScaleRepository: PainScaleRepository) =
        SavePainScaleUseCase(painScaleRepository)

    @Provides
    @ViewModelScoped
    fun getPainScaleHistoryUseCase(painScaleRepository: PainScaleRepository) =
        GetPainScaleHistoryUseCase(painScaleRepository)

    @Provides
    @ViewModelScoped
    fun getLineChartHistoryUseCase(painScaleRepository: PainScaleRepository) =
        GetLineChartHistoryUseCase(painScaleRepository)

    @Provides
    @ViewModelScoped
    fun deletePainScaleUseCase(painScaleRepository: PainScaleRepository) =
        DeletePainScaleUseCase(painScaleRepository)
}
