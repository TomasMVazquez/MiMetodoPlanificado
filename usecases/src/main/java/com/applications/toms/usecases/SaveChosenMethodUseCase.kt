package com.applications.toms.usecases

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.data.repository.ChosenMethodRepository
import com.applications.toms.domain.MethodChosen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveChosenMethodUseCase(
    private val chosenMethodRepository: ChosenMethodRepository
): FlowUseCase<MethodChosen, Either<EitherState, EitherState>>() {

    override fun prepareFlow(input: MethodChosen): Flow<Either<EitherState, EitherState>> = flow {
        chosenMethodRepository.saveChosenMethod()
            .onSuccess { eitherSuccess(EitherState.SUCCESS) }
            .onFailure { eitherFailure(EitherState.FAILURE) }
    }

}
