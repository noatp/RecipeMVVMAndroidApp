package com.example.recipemvvmandroidapp.domain.useCase

sealed class UseCaseResult<out R>{
    data class Success<out T>(val resultValue: T): UseCaseResult<T>()
    data class Error(val exception: Exception): UseCaseResult<Nothing>()
}