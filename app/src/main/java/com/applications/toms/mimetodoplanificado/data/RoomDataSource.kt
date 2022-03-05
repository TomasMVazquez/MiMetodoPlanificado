package com.applications.toms.mimetodoplanificado.data

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.domain.MethodChosen
import com.applications.toms.mimetodoplanificado.data.mapper.toDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomDataSource(db: MyDatabase): LocalDataSource {

    private val dao = db.databaseDao()

    override suspend fun saveChosenMethod(chosenMethod: MethodChosen): Either<EitherState,EitherState> =
        withContext(Dispatchers.IO) {
            val dbModel = chosenMethod.toDatabaseModel()
            if (dbModel == null) {
                eitherFailure(EitherState.FAILURE)
            } else {
                val response = dao.insert(dbModel)
                if (response > 0) eitherSuccess(EitherState.SUCCESS) else eitherFailure(EitherState.FAILURE)
            }
        }

    
}