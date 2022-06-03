package com.applications.toms.data.source

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.domain.MyCycle
import com.applications.toms.domain.enums.ErrorStates

interface LocalCycleDataSource {

    suspend fun saveMyCycle(myCycle: MyCycle): Either<MyCycle, ErrorStates>

    suspend fun getMyCycle(): Either<MyCycle, ErrorStates>

    suspend fun deleteMyCycle(): Either<EitherState, ErrorStates>

}
