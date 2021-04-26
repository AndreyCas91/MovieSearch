package ru.geekbrains.moviesearch.view.main

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import ru.geekbrains.moviesearch.model.ArrayFilmsDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

@RequiresApi(Build.VERSION_CODES.N)
class ArrayFilmsLoader(private val listener: FilmsLoaderListener, private val id: Int) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadArrayFilms() {

        try {

            val mainHandler = Handler(Looper.getMainLooper())

            val uri = URL("https://api.themoviedb.org/3/discover/movie?api_key=959ec4426e0d46221498e4923f9d30d6&with_genres=${id}&page=1&language=ru_RU")

            Thread(Runnable {

                lateinit var uriConnection: HttpsURLConnection

                try {
                    uriConnection = uri.openConnection() as HttpsURLConnection
                    uriConnection.requestMethod = "GET"
                    uriConnection.readTimeout = 10000

                    val bufferedReader = BufferedReader(InputStreamReader(uriConnection.inputStream))

                    val arrayFilmsDTO: ArrayFilmsDTO = Gson().fromJson(getLines(bufferedReader), ArrayFilmsDTO::class.java)

                    mainHandler.post {
                        listener.onLoaded(arrayFilmsDTO)
                    }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    listener.onFailed(e)
                } finally {
                    uriConnection.disconnect()
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

    interface FilmsLoaderListener {
        fun onLoaded(arrayFilmsDTO: ArrayFilmsDTO)
        fun onFailed(throwable: Throwable)
    }
}