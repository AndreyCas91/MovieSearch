package ru.geekbrains.moviesearch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArrayFilmsDTO(
        val results: List<FilmsDTO>?
): Parcelable

@Parcelize
data class FilmsDTO(
        val release_date: String?, // дата релиза
        val original_title: String?, // название
        val popularity: Float?, //  рейтинг
        val overview: String? //  описание
): Parcelable