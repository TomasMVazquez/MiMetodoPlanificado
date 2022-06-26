package com.applications.toms.usecases.cycle

import com.applications.toms.data.Either
import com.applications.toms.data.repository.CycleRepository
import com.applications.toms.domain.MyCycle
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.usecases.UseCase

class GetCycleUseCase(
    private val cycleRepository: CycleRepository
): UseCase<Unit, MyCycle>() {

    override suspend fun buildUseCase(input: Unit): Either<MyCycle, ErrorStates> =
        cycleRepository.getMyCycle()

}
