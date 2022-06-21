package com.applications.toms.data.repository

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.source.LocalPainScaleDataSource
import com.applications.toms.domain.PainScaleModel
import com.applications.toms.domain.enums.ErrorStates

class PainScaleRepository(
    private val localDataSource: LocalPainScaleDataSource
) {
    suspend fun savePainScale(model: PainScaleModel): Either<PainScaleModel, ErrorStates> =
        localDataSource.savePainScale(model)

    suspend fun getPainScales(): Either<List<PainScaleModel>, ErrorStates> =
        localDataSource.getPainScales()

    suspend fun deletePainScale(model: PainScaleModel): Either<EitherState, ErrorStates> =
        localDataSource.deletePainScale(model)
}