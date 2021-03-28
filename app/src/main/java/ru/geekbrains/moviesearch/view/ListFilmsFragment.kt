package ru.geekbrains.moviesearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.geekbrains.moviesearch.model.ArrayFilms
import ru.geekbrains.moviesearch.databinding.FragmentListFilmsBinding
import ru.geekbrains.moviesearch.viewmodel.AppState
import ru.geekbrains.moviesearch.viewmodel.MainViewModel

class ListFilmsFragment : Fragment(){

    private var _binding: FragmentListFilmsBinding? = null
    private val binding get() = _binding!!

    private val adapter: WholeListAdapter = WholeListAdapter()

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
        initTest()
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
        }
    }

    private fun setData(filmData: ArrayFilms) {
        adapter.setItems(filmData.arrayFilms)
    }

    private fun initTest(){
        binding.rvFragmentListFilms.layoutManager = LinearLayoutManager(this.context)
        binding.rvFragmentListFilms.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ListFilmsFragment()
    }
}