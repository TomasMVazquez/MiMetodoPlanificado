package com.applications.toms.usecases.cycle

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.data.repository.CycleRepository
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.usecases.UseCase

class DeleteCycleUseCase(
    private val cycleRepository: CycleRepository
): UseCase<Unit, EitherState>() {

    override suspend fun buildUseCase(input: Unit): Either<EitherState, ErrorStates> =
        cycleRepository.deleteMyCycle()
            .onSuccess { eitherSuccess(it) }
            .onFailure { eitherFailure(it) }
}