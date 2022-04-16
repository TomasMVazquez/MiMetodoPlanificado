package com.applications.toms.usecases

import com.applications.toms.data.Either
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.data.repository.ChosenMethodRepository
import com.applications.toms.domain.MethodChosen
import com.applications.toms.domain.enums.ErrorStates
import java.time.LocalDate

class GetChosenMethodUseCase(
    private val chosenMethodRepository: ChosenMethodRepository
) : UseCase<Unit, Pair<MethodChosen, LocalDate>>() {

    override suspend fun buildUseCase(input: Unit): Either<Pair<MethodChosen, LocalDate>, ErrorStates> =
        chosenMethodRepository.getChosenMethod()
            .onSuccess { eitherSuccess(it) }
            .onFailure { eitherFailure(it) }
}