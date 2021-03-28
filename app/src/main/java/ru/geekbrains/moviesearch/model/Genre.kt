package ru.geekbrains.moviesearch.model

data class Genre (
    val genre: String,
    val arrayFilm: List<Film>
)