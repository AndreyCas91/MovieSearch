package ru.geekbrains.moviesearch.model

class RepositoryImpl : Repository {
    override fun getFilmsFromServer(): ArrayFilms {
        return ArrayFilms()
    }

    override fun getFilmsFromLocalStorage(): ArrayFilms {
        return ArrayFilms()
    }
}