package com.applications.toms.data.repository

import com.applications.toms.data.Either
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.domain.MethodChosen

class ChosenMethodRepository(
    private val localDataSource: LocalDataSource
) {

    //TODO CHANGE SECOND BY AN ERROR STATE
    suspend fun saveChosenMethod(): Either<MethodChosen,String> {
        return eitherFailure("")
    }

}