package com.applications.toms.usecases

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.data.repository.ChosenMethodRepository
import com.applications.toms.domain.enums.ErrorStates

class DeleteChosenMethodUseCase(
    private val chosenMethodRepository: ChosenMethodRepository
) : UseCase<Unit, EitherState>() {

    override suspend fun buildUseCase(input: Unit): Either<EitherState, ErrorStates> =
        chosenMethodRepository.deleteChosenMethod()
            .onSuccess { eitherSuccess(it) }
            .onFailure { eitherFailure(it) }


}