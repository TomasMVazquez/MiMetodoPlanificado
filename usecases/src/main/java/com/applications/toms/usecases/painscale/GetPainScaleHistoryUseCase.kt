package com.applications.toms.usecases.painscale

import com.applications.toms.data.Either
import com.applications.toms.data.repository.PainScaleRepository
import com.applications.toms.domain.LineChartEntity
import com.applications.toms.domain.PainScaleCard
import com.applications.toms.domain.PainScaleModel
import com.applications.toms.domain.PieChartEntity
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.usecases.UseCase

class GetPainScaleHistoryUseCase(
    private val painScaleRepository: PainScaleRepository
): UseCase<List<PainScaleCard>, Triple<List<PainScaleModel>, List<LineChartEntity>, List<PieChartEntity>>>() {

    override suspend fun buildUseCase(input: List<PainScaleCard>):
            Either<Triple<List<PainScaleModel>, List<LineChartEntity>, List<PieChartEntity>>, ErrorStates> =
        painScaleRepository.getPainScaleHistory(input)

}
