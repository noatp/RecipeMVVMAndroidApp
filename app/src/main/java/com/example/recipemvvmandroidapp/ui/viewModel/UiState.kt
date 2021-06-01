package com.example.recipemvvmandroidapp.ui.viewModel

sealed class UiState<out R>{
    data class Success<out T>(val states: T): UiState<T>()
    data class Error(val exception: Exception): UiState<Nothing>()
}