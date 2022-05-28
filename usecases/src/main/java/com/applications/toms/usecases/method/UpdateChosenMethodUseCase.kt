package com.applications.toms.usecases.method

import com.applications.toms.data.Either
import com.applications.toms.data.repository.ChosenMethodRepository
import com.applications.toms.domain.MethodChosen
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.usecases.UseCase

class UpdateChosenMethodUseCase(
    private val chosenMethodRepository: ChosenMethodRepository
) : UseCase<UpdateChosenMethodUseCase.Input, MethodChosen>() {


    override suspend fun buildUseCase(input: Input): Either<MethodChosen, ErrorStates> {
        val updatedMethod = input.methodChosen.copy(
            isNotificationEnable = input.notificationsState,
            notificationTime = input.notificationTime,
            isAlarmEnable = input.alarmState,
            alarmTime = input.alarmTime
        )
        return chosenMethodRepository.updateChosenMethod(updatedMethod)
    }

    data class Input(
        val methodChosen: MethodChosen,
        val notificationsState: Boolean,
        val notificationTime: String? = null,
        val alarmState: Boolean,
        val alarmTime: String? = null
    )
}
