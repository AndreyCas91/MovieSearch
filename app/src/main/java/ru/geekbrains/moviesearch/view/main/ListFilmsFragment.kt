package ru.geekbrains.moviesearch.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.geekbrains.moviesearch.R
import ru.geekbrains.moviesearch.model.ArrayFilms
import ru.geekbrains.moviesearch.databinding.FragmentListFilmsBinding
import ru.geekbrains.moviesearch.model.Film
import ru.geekbrains.moviesearch.view.details.DetailsFragment
import ru.geekbrains.moviesearch.viewmodel.AppState
import ru.geekbrains.moviesearch.viewmodel.MainViewModel

class ListFilmsFragment : Fragment(){

    private var _binding: FragmentListFilmsBinding? = null
    private val binding get() = _binding!!

    private val adapter: WholeListAdapter =
        WholeListAdapter(object : OnItemViewClickListener {
            override fun onItemViewClick(film: Film) {
                val manager = activity?.supportFragmentManager
                if(manager != null)
                {
                    val bundle = Bundle()
                    bundle.putParcelable(DetailsFragment.BUNDLE_KEY, film)
                    manager.beginTransaction()
                            .replace(R.id.layout_container, DetailsFragment.newInstance(bundle))
                            .addToBackStack(null)
                            .commit()
                }
            }

        })

    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getFilmFromLocalSource()
    }

    private fun renderData(data: AppState){
        when(data){
            is AppState.Success -> {
                setData(data.filmData)
            }
            //Загрузку и ошибку пока не реализовал
        }
    }

    private fun setData(filmData: ArrayFilms) {
        adapter.setItems(filmData.arrayFilms)
    }

    private fun initAdapter(){
        binding.rvFragmentListFilms.layoutManager = LinearLayoutManager(this.context)
        binding.rvFragmentListFilms.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() =
            ListFilmsFragment()
    }

    interface OnItemViewClickListener{
        fun onItemViewClick(film : Film)
    }
}