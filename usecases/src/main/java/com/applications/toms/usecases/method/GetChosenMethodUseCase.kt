package com.applications.toms.usecases.method

import com.applications.toms.data.Either
import com.applications.toms.data.repository.ChosenMethodRepository
import com.applications.toms.domain.MethodChosen
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.usecases.UseCase
import java.time.LocalDate

class GetChosenMethodUseCase(
    private val chosenMethodRepository: ChosenMethodRepository
) : UseCase<Unit, Pair<MethodChosen, LocalDate>>() {

    override suspend fun buildUseCase(input: Unit): Either<Pair<MethodChosen, LocalDate>, ErrorStates> =
        chosenMethodRepository.getChosenMethod()

}