package ru.geekbrains.moviesearch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlin.collections.ArrayList

@Parcelize
data class ArrayFilms(
        val arrayFilms: List<Genre> = listOf(
                Genre("ужасы", getDefaultListFilm("ужасы")),
                Genre("комедия", getDefaultListFilm("комедия")),
                Genre("триллер", getDefaultListFilm("триллер")),
                Genre("драма", getDefaultListFilm("драма"))
        )
) : Parcelable

fun getDefaultListFilm(str: String) = ArrayList<Film>(
    listOf(
        Film(str + "1", 2002, 5, "Среди древних народов, населявших просторы Евразии..."),
        Film(str + "2", 2004, 6, "Параллельно нашему миру существует иной — мир..."),
        Film(str + "3", 2007, 7, "В Новый Орлеан завозят капсулы, которые дают проглотившим их суперспособности..."),
        Film(str + "4", 2010, 8, "Суперкиллер Джон Уик после нарушения кодекса тайной гильдии ассасинов..."),
        Film(str + "5", 2015, 9, "Инсценировав собственную смерть, сознательный миллиардер начинает новую жизнь...")
    )
)
