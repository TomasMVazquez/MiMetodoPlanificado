package com.applications.toms.usecases

import com.applications.toms.data.Either
import com.applications.toms.data.repository.ChosenMethodRepository
import com.applications.toms.domain.MethodChosen
import com.applications.toms.domain.enums.ErrorStates

class UpdateChosenMethodUseCase(
    private val chosenMethodRepository: ChosenMethodRepository
) : UseCase<UpdateChosenMethodUseCase.Input, MethodChosen>() {


    override suspend fun buildUseCase(input: Input): Either<MethodChosen, ErrorStates> {
        val updatedMethod = input.methodChosen.copy(
            notifications = input.notificationsState,
            notificationTime = input.notificationTime,
            alarm = input.alarmState,
            alarmTime = input.alarmTime
        )
        return chosenMethodRepository.updateChosenMethod(updatedMethod)
    }

    data class Input(
        val methodChosen: MethodChosen,
        val notificationsState: Boolean,
        val notificationTime: String = "",
        val alarmState: Boolean,
        val alarmTime: String = ""
    )
}
