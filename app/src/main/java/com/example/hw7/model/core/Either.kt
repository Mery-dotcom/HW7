package com.example.hw7.model.core

sealed class Either<out L, out R> {

    data class Success<R>(val success: R) : Either<Nothing, R>()
    data class Error<L>(val error: L) : Either<L, Nothing>()
}