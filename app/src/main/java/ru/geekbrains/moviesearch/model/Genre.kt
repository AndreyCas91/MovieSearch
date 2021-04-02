package ru.geekbrains.moviesearch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre (
    val genre: String,
    val listFilm: List<Film>
) : Parcelable