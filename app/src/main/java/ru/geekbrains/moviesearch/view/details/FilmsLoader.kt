package ru.geekbrains.moviesearch.view.details

import android.os.Build
import android.os.Handler
import androidx.annotation.RequiresApi
import ru.geekbrains.moviesearch.BuildConfig
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.logging.Logger

@RequiresApi(Build.VERSION_CODES.N)
class FilmsLoader(private val listener: FilmsLoaderListener) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadFilms(){
        try{
            val uri = URL("https://www.themoviedb.org")
            val handler = Handler()

            Thread(Runnable {
                lateinit var urlConnection: HttpURLConnection

                try {
                    urlConnection = uri.openConnection() as HttpURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.addRequestProperty(
                        "nonKey",
                        BuildConfig....
                    )
                    urlConnection.readTimeout = 10000

                    val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))

                    /*тут пока пусто */
                    handler.post { }
                } catch (e: Exception){
                   handler.post { listener.onFailed() }
                } finally {
                    urlConnection.disconnect()
                }
            }).start()


        } catch (){
            listener.onFailed()
        }
    }
}

interface FilmsLoaderListener{
    fun onLoaded()
    fun onFailed()
}