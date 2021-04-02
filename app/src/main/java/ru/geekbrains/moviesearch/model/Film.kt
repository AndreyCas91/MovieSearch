package ru.geekbrains.moviesearch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film (
    val name: String,
    val year: Int,
    val rank: Int,
    val description: String
) : Parcelable