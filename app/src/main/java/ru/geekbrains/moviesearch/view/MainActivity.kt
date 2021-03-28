package ru.geekbrains.moviesearch.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.moviesearch.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.layout_container, ListFilmsFragment.newInstance())
                .commitNow()
        }
    }
}