package com.applications.toms.data.repository

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.source.LocalMethodDataSource
import com.applications.toms.domain.MethodChosen
import com.applications.toms.domain.enums.ErrorStates
import java.time.LocalDate

class ChosenMethodRepository(
    private val localDataSource: LocalMethodDataSource
) {

    suspend fun saveChosenMethod(chosenMethod: MethodChosen): Either<EitherState,ErrorStates> =
        localDataSource.saveChosenMethod(chosenMethod = chosenMethod)

    suspend fun getChosenMethod(): Either<Pair<MethodChosen, LocalDate>, ErrorStates> =
        localDataSource.getChosenMethod()

    suspend fun deleteChosenMethod(): Either<EitherState,ErrorStates> =
        localDataSource.deleteChosenMethod()

    suspend fun updateChosenMethod(chosenMethod: MethodChosen): Either<MethodChosen, ErrorStates> =
        localDataSource.updateChosenMethod(chosenMethod = chosenMethod)
}
