package com.applications.toms.data.source

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.domain.PainScaleModel
import com.applications.toms.domain.enums.ErrorStates


interface LocalPainScaleDataSource {

    suspend fun savePainScale(model: PainScaleModel): Either<PainScaleModel, ErrorStates>

    suspend fun getPainScales(): Either<List<PainScaleModel>, ErrorStates>

    suspend fun deletePainScale(model: PainScaleModel): Either<EitherState, EitherState>

}