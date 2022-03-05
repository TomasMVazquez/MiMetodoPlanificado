package com.applications.toms.data

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

enum class EitherState {
    SUCCESS,
    FAILURE
}

sealed class Either<out T, out E> {
    data class Success<out T>(val data: T): Either<T, Nothing>()
    data class Failure<out E>(val error: E): Either<Nothing, E>()
}

fun eitherEmpty() = Either.Success(Unit)

fun <T> eitherSuccess(data: T) = Either.Success(data)

fun <T> eitherFailure(error: T) = Either.Failure(error)

@OptIn(ExperimentalContracts::class)
inline fun <T, E> Either<T, E>.onSuccess(action: (T) -> Unit): Either<T, E> {
    contract { callsInPlace(action,InvocationKind.AT_MOST_ONCE) }
    if (this is Either.Success) action(data)
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T, E> Either<T, E>.onFailure(action: (E) -> Unit): Either<T, E> {
    contract { callsInPlace(action,InvocationKind.AT_MOST_ONCE) }
    if (this is Either.Failure) action(error)
    return this
}