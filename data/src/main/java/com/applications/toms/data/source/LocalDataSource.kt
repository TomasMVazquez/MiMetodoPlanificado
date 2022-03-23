package com.applications.toms.data.source

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.domain.MethodChosen
import com.applications.toms.domain.enums.ErrorStates
import java.time.LocalDate

interface LocalDataSource {

    suspend fun saveChosenMethod(chosenMethod: MethodChosen): Either<EitherState, ErrorStates>

    suspend fun getChosenMethod(): Either<Pair<MethodChosen, LocalDate>, ErrorStates>

    suspend fun deleteChosenMethod(): Either<EitherState, ErrorStates>
}
