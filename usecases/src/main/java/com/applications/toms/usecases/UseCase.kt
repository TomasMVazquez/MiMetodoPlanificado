package com.applications.toms.usecases

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.onFailure

abstract class UseCase<T, R> protected constructor(
) {

    //TODO CHANGE ERROR RETURN FOR A MORE DETAIL ONE
    protected abstract suspend fun buildUseCase(input: T): Either<R,EitherState>

    suspend fun execute(input: T) =
        try {
            buildUseCase(input)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            eitherFailure(EitherState.FAILURE)
        }

}