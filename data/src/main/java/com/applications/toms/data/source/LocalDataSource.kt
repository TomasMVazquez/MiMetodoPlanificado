package com.applications.toms.data.source

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.domain.MethodChosen

interface LocalDataSource {

    suspend fun saveChosenMethod(chosenMethod: MethodChosen): Either<EitherState, EitherState>

}