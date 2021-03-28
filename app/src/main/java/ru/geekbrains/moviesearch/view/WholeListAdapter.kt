package ru.geekbrains.moviesearch.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import ru.geekbrains.moviesearch.model.Genre
import ru.geekbrains.moviesearch.R

class WholeListAdapter : RecyclerView.Adapter<WholeListAdapter.ViewHolder>() {

    private val listGenres: ArrayList<Genre> = ArrayList()

    fun setItems(item: List<Genre>){
        listGenres.clear()
        listGenres.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_films, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listGenres.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(listGenres[position], position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var textView: MaterialTextView
        private lateinit var recyclerView: RecyclerView
        private val adapter: ListAdapter = ListAdapter()

        fun onBind(item: Genre, position: Int){
            initView()

            textView.text = item.genre
            adapter.setItems(item.arrayFilm)
        }

        private fun initView(){
            textView = itemView.findViewById(R.id.tv_list_genre)
            recyclerView = itemView.findViewById(R.id.rv_item_list_films)
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapter
        }
    }
}