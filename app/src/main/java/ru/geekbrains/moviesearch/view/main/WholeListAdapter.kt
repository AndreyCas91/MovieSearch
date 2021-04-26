package ru.geekbrains.moviesearch.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import ru.geekbrains.moviesearch.R
import ru.geekbrains.moviesearch.model.ArrayFilmsDTO
import ru.geekbrains.moviesearch.model.GenreDTO

class WholeListAdapter(private var onItemViewClickListener: ListFilmsFragment.OnItemViewClickListener?) : RecyclerView.Adapter<WholeListAdapter.WholeListViewHolder>() {

    private val listGenre: ArrayList<GenreDTO> = ArrayList()

    fun setItemsGenre(item: List<GenreDTO>){
        listGenre.clear()
        listGenre.addAll(item)
        notifyDataSetChanged()
    }

    fun removeListener() {
        onItemViewClickListener = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WholeListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_films, parent, false)
        return WholeListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listGenre.size
    }

    override fun onBindViewHolder(holder: WholeListViewHolder, position: Int) {
        holder.onBind(listGenre[position])
    }

    inner class WholeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val onLoadListenerFilms : ArrayFilmsLoader.FilmsLoaderListener =
                object : ArrayFilmsLoader.FilmsLoaderListener{
                    override fun onLoaded(arrayFilmsDTO: ArrayFilmsDTO) {
                        arrayFilmsDTO.results?.let { adapter.setItems(it) }
                    }

                    override fun onFailed(throwable: Throwable) {

                    }
                }

        private lateinit var textView: MaterialTextView
        private lateinit var recyclerView: RecyclerView
        private val adapter: ListAdapter = ListAdapter(onItemViewClickListener)

        fun onBind(item: GenreDTO){
            initView()

            textView.text = item.name

            item.id?.let { initLoader(it) }
        }

        private fun initLoader(item: Int){
            val loader = ArrayFilmsLoader(onLoadListenerFilms, item)
            loader.loadArrayFilms()
        }

        private fun initView(){
            textView = itemView.findViewById(R.id.tv_list_genre)
            recyclerView = itemView.findViewById(R.id.rv_item_list_films)
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapter
        }
    }
}