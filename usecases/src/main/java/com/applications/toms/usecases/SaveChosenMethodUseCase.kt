package com.applications.toms.usecases

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.data.repository.ChosenMethodRepository
import com.applications.toms.domain.MethodChosen
import com.applications.toms.domain.enums.ErrorStates

class SaveChosenMethodUseCase(
    private val chosenMethodRepository: ChosenMethodRepository
): UseCase<MethodChosen, EitherState>() {


    override suspend fun buildUseCase(input: MethodChosen): Either<EitherState, ErrorStates> =
        chosenMethodRepository.saveChosenMethod(input)
            .onSuccess { eitherSuccess(EitherState.SUCCESS) }
            .onFailure { eitherFailure(ErrorStates.GENERIC) }


}
