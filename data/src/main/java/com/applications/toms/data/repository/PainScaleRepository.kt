package com.applications.toms.data.repository

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.source.LocalPainScaleDataSource
import com.applications.toms.domain.LineChartEntity
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

    suspend fun getLineChartHistory(): Either<List<LineChartEntity>, ErrorStates> =
        when (val result = localDataSource.getPainScales()) {
            is Either.Failure -> {
                eitherFailure(ErrorStates.GENERIC)
            }
            is Either.Success -> {
                if (result.data.isEmpty()) eitherSuccess(emptyList())
                else {
                    eitherSuccess(
                        List(28) { index ->
                            val day = index + 1
                            val dayList = result.data.filter { it.dayOfCycle == day }.map { it.painScale }
                            LineChartEntity(
                                value = if (dayList.isEmpty()) 0f else dayList.average().toFloat(),
                                label = day.toString()
                            )
                        }
                    )
                }
            }
        }
}