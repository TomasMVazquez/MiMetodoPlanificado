package com.applications.toms.mimetodoplanificado.data.method

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.source.LocalMethodDataSource
import com.applications.toms.domain.MethodChosen
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.mimetodoplanificado.data.MyMethodDatabase
import com.applications.toms.mimetodoplanificado.data.mapper.toDatabaseModel
import com.applications.toms.mimetodoplanificado.data.mapper.toModel
import com.applications.toms.mimetodoplanificado.ui.utils.methods.TOTAL_CYCLE_DAYS
import com.applications.toms.mimetodoplanificado.ui.utils.toFormattedString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MethodRoomDataSource(db: MyMethodDatabase): LocalMethodDataSource {

    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yy")

    private val dao = db.databaseDao()

    override suspend fun saveChosenMethod(chosenMethod: MethodChosen): Either<EitherState,ErrorStates> =
        withContext(Dispatchers.IO) {
            val dbModel = chosenMethod.toDatabaseModel()
            if (dbModel == null) {
                eitherFailure(ErrorStates.EMPTY)
            } else {
                val response = dao.insert(dbModel)
                if (response > 0) eitherSuccess(EitherState.SUCCESS) else eitherFailure(ErrorStates.NOT_SAVED)
            }
        }

    override suspend fun getChosenMethod(): Either<Pair<MethodChosen,LocalDate>, ErrorStates> =
        withContext(Dispatchers.IO) {
            val method = dao.getMethod()
            if (method == null) eitherFailure(ErrorStates.NOT_SAVED)
            else {
                val nextCycle = LocalDate.parse(method.nextCycle, formatter)
                if (LocalDate.now().isAfter(nextCycle) || LocalDate.now().isEqual(nextCycle)){
                    val updatedMethod = method.copy(
                        startDate = nextCycle.toFormattedString(),
                        nextCycle = nextCycle.plusDays(TOTAL_CYCLE_DAYS).toFormattedString()
                    )
                    val response = dao.updateMethod(updatedMethod)
                    if (response > 0)
                        eitherSuccess(Pair(updatedMethod.toModel(formatter), LocalDate.parse(updatedMethod.nextCycle, formatter)))
                    else
                        eitherFailure(ErrorStates.NOT_SAVED)
                } else
                    eitherSuccess(Pair(method.toModel(formatter), LocalDate.parse(method.nextCycle, formatter)))
            }
        }

    override suspend fun deleteChosenMethod(): Either<EitherState, ErrorStates> =
        withContext(Dispatchers.IO) {
            val method = dao.getMethod()
            if (method == null)
                eitherFailure(ErrorStates.NOT_SAVED)
            else {
                val response = dao.deleteMethod(method.methodChosen)
                if (response > 0) eitherSuccess(EitherState.SUCCESS) else eitherFailure(ErrorStates.GENERIC)
            }
        }

    override suspend fun updateChosenMethod(chosenMethod: MethodChosen): Either<MethodChosen, ErrorStates> =
        withContext(Dispatchers.IO) {
            val dbModel = chosenMethod.toDatabaseModel()
            if (dbModel == null) {
                eitherFailure(ErrorStates.EMPTY)
            } else {
                val response = dao.updateMethod(dbModel)
                if (response > 0)
                    eitherSuccess(chosenMethod)
                else
                    eitherFailure(ErrorStates.NOT_SAVED)
            }
        }

}
