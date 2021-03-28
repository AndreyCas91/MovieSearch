package ru.geekbrains.moviesearch.viewmodel

import ru.geekbrains.moviesearch.model.ArrayFilms

sealed class AppState {
    data class Success(val filmData: ArrayFilms) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}