package com.applications.toms.usecases.cycle

import com.applications.toms.data.Either
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.data.repository.CycleRepository
import com.applications.toms.domain.MyCycle
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.usecases.UseCase
import java.time.LocalDate

class SaveCycleUseCase(
    private val cycleRepository: CycleRepository
): UseCase<SaveCycleUseCase.Input, MyCycle>() {

    override suspend fun buildUseCase(input: Input): Either<MyCycle, ErrorStates> {
        val newCycle = MyCycle(
            startDate = input.startDate,
            nextCycle = input.startDate.plusDays(input.totalCycleDays),
            totalDaysCycle = input.totalCycleDays
        )
        return cycleRepository.saveMyCycle(newCycle)
            .onSuccess { eitherSuccess(it) }
            .onFailure { eitherFailure(it) }
    }

    data class Input(
        val startDate: LocalDate,
        val totalCycleDays: Long
    )

}