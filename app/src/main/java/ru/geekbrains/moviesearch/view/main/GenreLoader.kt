package ru.geekbrains.moviesearch.view.main

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import ru.geekbrains.moviesearch.model.ArrayGenreDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

@RequiresApi(Build.VERSION_CODES.N)
class GenreLoader(private val listener: GenresLoaderListener) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadGenres() {
        try {

            val mainHandler = Handler(Looper.getMainLooper())

            val uri = URL("https://api.themoviedb.org/3/genre/movie/list?api_key=959ec4426e0d46221498e4923f9d30d6&language=ru_RU")

            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection

                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 100000

                    val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))

                    val arrayGenreDTO: ArrayGenreDTO = Gson().fromJson(getLines(bufferedReader), ArrayGenreDTO::class.java)
                    mainHandler.post {
                        listener.onLoaded(arrayGenreDTO)
                    }
                } catch (e: Exception){
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    listener.onFailed(e)
                } finally {
                    urlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            listener.onFailed(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    interface GenresLoaderListener {
        fun onLoaded(arrayGenreDTO: ArrayGenreDTO)
        fun onFailed(throwable: Throwable)
    }
}