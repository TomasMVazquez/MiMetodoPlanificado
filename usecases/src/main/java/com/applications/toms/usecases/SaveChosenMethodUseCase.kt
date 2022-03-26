package com.applications.toms.usecases

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.repository.ChosenMethodRepository
import com.applications.toms.domain.MethodChosen
import com.applications.toms.domain.enums.ErrorStates
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class SaveChosenMethodUseCase(
    private val chosenMethodRepository: ChosenMethodRepository
) : UseCase<MethodChosen, SaveChosenMethodUseCase.Result>() {


    override suspend fun buildUseCase(input: MethodChosen): Either<Result, ErrorStates> =
        when (chosenMethodRepository.saveChosenMethod(input)) {
            is Either.Success -> {
                eitherSuccess(
                    Result(
                        eitherState = EitherState.SUCCESS,
                        notificationsState = input.notifications,
                        notificationTimeInMillis = input.notificationTime.convertToTimeInMills(),
                        alarmState = input.alarm,
                        alarmTimeInMillis = input.alarmTime.convertToTimeInMills()
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
        val notificationTimeInMillis: Long = 0L,
        val alarmState: Boolean,
        val alarmTimeInMillis: Long = 0L,
    )

    private fun String.convertToTimeInMills(): Long =
        try {
            val myTime = LocalTime.parse(this, DateTimeFormatter.ofPattern("HH:mm"))
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, myTime.hour)
            calendar.set(Calendar.MINUTE, myTime.minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.timeInMillis
        } catch (t: Throwable) {
            0L
        }

}
