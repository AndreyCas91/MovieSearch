package ru.geekbrains.moviesearch.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import java.lang.StringBuilder

class MainBroadcastReceiver : BroadcastReceiver() {
    private var counter = 0

    override fun onReceive(context: Context?, intent: Intent?) {
        StringBuilder().apply {
            append("Сообщение от системы")
            append("Изменение подключения к Internet")
            toString().also {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}