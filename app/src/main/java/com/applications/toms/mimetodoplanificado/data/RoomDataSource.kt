package com.applications.toms.mimetodoplanificado.data

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.domain.MethodChosen
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.mimetodoplanificado.data.mapper.toDatabaseModel
import com.applications.toms.mimetodoplanificado.data.mapper.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RoomDataSource(db: MyDatabase): LocalDataSource {

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yy")

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
            if (method == null)
                eitherFailure(ErrorStates.NOT_SAVED)
            else
                eitherSuccess(Pair(method.toModel(formatter),LocalDate.parse(method.nextCycle, formatter)))
        }

}
