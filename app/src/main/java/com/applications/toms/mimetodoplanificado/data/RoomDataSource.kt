package com.applications.toms.mimetodoplanificado.data

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.domain.MethodChosen
import com.applications.toms.mimetodoplanificado.data.mapper.toDatabaseModel

class RoomDataSource(db: MyDatabase): LocalDataSource {

    private val dao = db.databaseDao

    override suspend fun saveChosenMethod(chosenMethod: MethodChosen): Either<EitherState,EitherState> {
        val dbModel = chosenMethod.toDatabaseModel() ?: return eitherFailure(EitherState.FAILURE)
        val response = dao.insert(dbModel)
        return if (response > 0) eitherSuccess(EitherState.SUCCESS) else eitherFailure(EitherState.FAILURE)
    }
    
}