package com.applications.toms.mimetodoplanificado.data.datasource.painscale

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.source.LocalPainScaleDataSource
import com.applications.toms.domain.PainScaleModel
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.mimetodoplanificado.data.MyPainScaleDatabase
import com.applications.toms.mimetodoplanificado.data.mapper.toDatabaseModel
import com.applications.toms.mimetodoplanificado.data.mapper.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter

class PainScaleRoomDataSource(db: MyPainScaleDatabase) : LocalPainScaleDataSource {

    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yy")

    private val dao = db.databaseDao()

    override suspend fun savePainScale(model: PainScaleModel): Either<PainScaleModel, ErrorStates> =
        withContext(Dispatchers.IO) {
            val dbModel = model.toDatabaseModel()
            val hasBeenAlreadySaved = dao.checkIfDateExistsAlready(dbModel.date) == 1
            val response = if (hasBeenAlreadySaved) {
                dao.updatePainScale(dbModel)
            } else {
                dao.insert(dbModel).toInt()
            }
            if (response > 0) eitherSuccess(model)
            else eitherFailure(ErrorStates.NOT_SAVED)
        }

    override suspend fun getPainScales(): Either<List<PainScaleModel>, ErrorStates> =
        withContext(Dispatchers.IO) {
            val response = dao.getPainScales()
            if (response.isEmpty()) eitherFailure(ErrorStates.EMPTY)
            else eitherSuccess(response.map { it.toModel(formatter) })
        }

    override suspend fun deletePainScale(model: PainScaleModel): Either<EitherState, EitherState> =
        withContext(Dispatchers.IO) {
            val dbModel = model.toDatabaseModel()
            val response = dao.deletePainScaleFromDate(dbModel.date)
            if (response > 0) eitherSuccess(EitherState.SUCCESS)
            else eitherFailure(EitherState.FAILURE)
        }

}