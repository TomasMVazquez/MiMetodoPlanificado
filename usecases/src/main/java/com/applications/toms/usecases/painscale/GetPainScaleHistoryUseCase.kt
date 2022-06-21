package com.applications.toms.usecases.painscale

import com.applications.toms.data.Either
import com.applications.toms.data.repository.PainScaleRepository
import com.applications.toms.domain.PainScaleModel
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.usecases.UseCase

class GetPainScaleHistoryUseCase(
    private val painScaleRepository: PainScaleRepository
): UseCase<Unit, List<PainScaleModel>>() {

    override suspend fun buildUseCase(input: Unit): Either<List<PainScaleModel>, ErrorStates> =
        painScaleRepository.getPainScales()

}
