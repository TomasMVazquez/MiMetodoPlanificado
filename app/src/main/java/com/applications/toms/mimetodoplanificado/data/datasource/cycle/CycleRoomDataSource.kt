package com.applications.toms.mimetodoplanificado.data.datasource.cycle

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.source.LocalCycleDataSource
import com.applications.toms.domain.MyCycle
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.mimetodoplanificado.data.MyCycleDatabase
import com.applications.toms.mimetodoplanificado.data.mapper.toDatabaseModel
import com.applications.toms.mimetodoplanificado.data.mapper.toModel
import com.applications.toms.mimetodoplanificado.ui.utils.methods.TOTAL_CYCLE_DAYS
import com.applications.toms.mimetodoplanificado.ui.utils.toFormattedString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CycleRoomDataSource(db: MyCycleDatabase) : LocalCycleDataSource {

    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yy")

    private val dao = db.databaseDao()

    override suspend fun saveMyCycle(myCycle: MyCycle): Either<MyCycle, ErrorStates> =
        withContext(Dispatchers.IO) {
            val dbModel = myCycle.toDatabaseModel()
            val cycle = dao.getCycle()
            val response = if (cycle != null) {
                dao.updateCycle(cycle.copy(
                    startDate = dbModel.startDate,
                    nextCycle = dbModel.nextCycle,
                    totalDaysCycle = dbModel.totalDaysCycle
                ))
            } else {
                dao.insert(dbModel).toInt()
            }
            if (response > 0) eitherSuccess(myCycle)
            else eitherFailure(ErrorStates.NOT_SAVED)
        }

    override suspend fun getMyCycle(): Either<MyCycle, ErrorStates> =
        withContext(Dispatchers.IO) {
            val cycle = dao.getCycle()
            if (cycle == null) eitherFailure(ErrorStates.NOT_SAVED)
            else {
                val nextCycle = LocalDate.parse(cycle.nextCycle, formatter)
                if (LocalDate.now().isAfter(nextCycle) || LocalDate.now().isEqual(nextCycle)) {
                    val newCycle = cycle.copy(
                        startDate = nextCycle.toFormattedString(),
                        nextCycle = nextCycle.plusDays(TOTAL_CYCLE_DAYS).toFormattedString()
                    )
                    val response = dao.updateCycle(newCycle)
                    if (response > 0)
                        eitherSuccess(cycle.toModel(formatter))
                    else
                        eitherFailure(ErrorStates.NOT_SAVED)
                } else {
                    eitherSuccess(cycle.toModel(formatter))
                }
            }
        }

    override suspend fun deleteMyCycle(): Either<EitherState, ErrorStates> =
        withContext(Dispatchers.IO) {
            val cycle = dao.getCycle()
            if (cycle == null) {
                eitherFailure(ErrorStates.NOT_SAVED)
            } else {
                val response = dao.deleteCycle(cycle.id)
                if (response > 0) eitherSuccess(EitherState.SUCCESS) else eitherFailure(ErrorStates.NOT_SAVED)
            }
        }

}
