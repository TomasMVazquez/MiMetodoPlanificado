package com.applications.toms.usecases.painscale

import com.applications.toms.data.Either
import com.applications.toms.data.repository.PainScaleRepository
import com.applications.toms.domain.LineChartEntity
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.usecases.UseCase

class GetLineChartHistoryUseCase(
    private val painScaleRepository: PainScaleRepository
) : UseCase<Unit, List<LineChartEntity>>() {

    override suspend fun buildUseCase(input: Unit): Either<List<LineChartEntity>, ErrorStates> =
        painScaleRepository.getLineChartHistory()

}
