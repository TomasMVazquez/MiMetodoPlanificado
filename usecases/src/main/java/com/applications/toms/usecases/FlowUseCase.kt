package com.applications.toms.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

abstract class FlowUseCase<T,R>(protected open val coroutineContext: CoroutineContext = Dispatchers.IO) {

    fun prepare(input: T) = prepareFlow(input).flowOn(coroutineContext)

    protected abstract fun prepareFlow(input: T): Flow<R>

}