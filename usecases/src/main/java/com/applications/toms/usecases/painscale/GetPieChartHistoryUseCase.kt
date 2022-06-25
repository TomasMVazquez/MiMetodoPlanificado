package com.applications.toms.usecases.painscale

import com.applications.toms.data.Either
import com.applications.toms.data.repository.PainScaleRepository
import com.applications.toms.domain.PainScaleCard
import com.applications.toms.domain.PieChartEntity
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.usecases.UseCase

class GetPieChartHistoryUseCase(
    private val painScaleRepository: PainScaleRepository
) : UseCase<List<PainScaleCard>, List<PieChartEntity>>() {

    override suspend fun buildUseCase(input: List<PainScaleCard>): Either<List<PieChartEntity>, ErrorStates> =
        painScaleRepository.getPieChartHistory(input)

}
