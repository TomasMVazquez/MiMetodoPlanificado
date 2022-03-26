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
): UseCase<MethodChosen, SaveChosenMethodUseCase.Result>() {


    override suspend fun buildUseCase(input: MethodChosen): Either<Result, ErrorStates> =
        when(chosenMethodRepository.saveChosenMethod(input)) {
            is Either.Success -> {
                eitherSuccess(
                    Result(
                        EitherState.SUCCESS,
                        input.notifications,
                        input.alarm
                    )
                )
            }
            is Either.Failure -> {
                eitherFailure(ErrorStates.NOT_SAVED)
            }
        }

    data class Result(
        val eitherState: EitherState,
        val notificationsState: Boolean,
        val alarmState: Boolean
    )

}
