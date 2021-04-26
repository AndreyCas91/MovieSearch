package ru.geekbrains.moviesearch.model

data class ArrayGenreDTO(
        val genres: List<GenreDTO>?
)

data class GenreDTO(
    val id: Int?,
    val name: String?
)