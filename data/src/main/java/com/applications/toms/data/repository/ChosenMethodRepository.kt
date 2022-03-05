package com.applications.toms.data.repository

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.domain.MethodChosen

class ChosenMethodRepository(
    private val localDataSource: LocalDataSource
) {

    suspend fun saveChosenMethod(chosenMethod: MethodChosen): Either<EitherState,EitherState> =
        localDataSource.saveChosenMethod(chosenMethod = chosenMethod)

}