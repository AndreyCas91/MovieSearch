package ru.geekbrains.moviesearch.model

interface Repository {
    fun getFilmsFromServer(): ArrayFilms
    fun getFilmsFromLocalStorage(): ArrayFilms
}