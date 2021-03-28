package ru.geekbrains.moviesearch.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import ru.geekbrains.moviesearch.model.Film
import ru.geekbrains.moviesearch.R

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private val ListFilms: ArrayList<Film> = ArrayList()

    fun setItems(item: List<Film>){
        ListFilms.clear()
        ListFilms.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ListFilms.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(ListFilms.get(position), position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var textView: MaterialTextView

        fun onBind(item: Film, position: Int) {
            initView()

            textView.text = item.name
        }

        fun initView() {
            textView = itemView.findViewById(R.id.tv_item_nameFilm)
        }
    }
}