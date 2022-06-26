package com.applications.toms.usecases.painscale

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.repository.PainScaleRepository
import com.applications.toms.domain.PainScaleModel
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.usecases.UseCase

class DeletePainScaleUseCase(
    private val painScaleRepository: PainScaleRepository
): UseCase<PainScaleModel, EitherState>() {

    override suspend fun buildUseCase(input: PainScaleModel): Either<EitherState, ErrorStates> =
        painScaleRepository.deletePainScale(input)

}
