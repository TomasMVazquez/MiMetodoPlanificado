package com.applications.toms.data.repository

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.source.LocalCycleDataSource
import com.applications.toms.domain.MyCycle
import com.applications.toms.domain.enums.ErrorStates

class CycleRepository(
    private val localDataSource: LocalCycleDataSource
) {
    suspend fun saveMyCycle(myCycle: MyCycle): Either<MyCycle, ErrorStates> =
        localDataSource.saveMyCycle(myCycle)

    suspend fun getMyCycle(): Either<MyCycle, ErrorStates> =
        localDataSource.getMyCycle()

    suspend fun deleteMyCycle(): Either<EitherState, ErrorStates> =
        localDataSource.deleteMyCycle()

}
