package ru.geekbrains.moviesearch.model

import kotlin.collections.ArrayList

data class ArrayFilms(
    val arrayFilms: List<Genre> = getDefaultArrayFilms()
)

fun getDefaultArrayFilms() = ArrayList<Genre>(
    listOf(
        Genre("ужасы", getDefaultListFilm("ужасы")),
        Genre("комедия", getDefaultListFilm("комедия")),
        Genre("триллер", getDefaultListFilm("триллер")),
        Genre("драма", getDefaultListFilm("драма"))
    )
)

fun getDefaultListFilm(str: String) = ArrayList<Film>(
    listOf(
        Film(str + "1", 2002, 5),
        Film(str + "2", 2004, 6),
        Film(str + "3", 2007, 7),
        Film(str + "4", 2010, 8),
        Film(str + "5", 2015, 9)
    )
)
