package com.applications.toms.usecases.painscale

import com.applications.toms.data.Either
import com.applications.toms.data.repository.PainScaleRepository
import com.applications.toms.domain.PainScaleModel
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.usecases.UseCase

class SavePainScaleUseCase(
    private val painScaleRepository: PainScaleRepository
): UseCase<PainScaleModel, PainScaleModel>() {

    override suspend fun buildUseCase(input: PainScaleModel): Either<PainScaleModel, ErrorStates> =
        painScaleRepository.savePainScale(input)

}
