package com.applications.toms.usecases.method

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.repository.ChosenMethodRepository
import com.applications.toms.domain.MethodChosen
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.usecases.UseCase

class SaveChosenMethodUseCase(
    private val chosenMethodRepository: ChosenMethodRepository
) : UseCase<MethodChosen, SaveChosenMethodUseCase.Result>() {


    override suspend fun buildUseCase(input: MethodChosen): Either<Result, ErrorStates> =
        when (chosenMethodRepository.saveChosenMethod(input)) {
            is Either.Success -> {
                eitherSuccess(
                    Result(
                        eitherState = EitherState.SUCCESS,
                        notificationsState = input.isNotificationEnable,
                        notificationTimeInMillis = input.notificationTime,
                        alarmState = input.isAlarmEnable,
                        alarmTimeInMillis = input.alarmTime
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
        val notificationTimeInMillis: String? = null,
        val alarmState: Boolean,
        val alarmTimeInMillis: String? = null,
    )

}
