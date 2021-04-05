package ru.geekbrains.moviesearch.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val adapter: WholeListAdapter =
        WholeListAdapter(object : OnItemViewClickListener {
            override fun onItemViewClick(film: Film) {
                val manager = activity?.supportFragmentManager
                if(manager != null) {
                    val bundle = Bundle()
                    bundle.putParcelable(DetailsFragment.BUNDLE_KEY, film)
                    manager.beginTransaction()
                            .replace(R.id.layout_container, DetailsFragment.newInstance(bundle))
                            .addToBackStack(null)
                            .commit()
                }
            }
        })

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
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getFilmFromLocalSource()
    }

    private fun renderData(data: AppState){
        when(data){
            is AppState.Success -> {
                binding.flLoading.visibility = View.GONE
                setData(data.filmData)
            }
            is AppState.Loading -> {
                binding.flLoading.visibility - View.VISIBLE
            }
            is AppState.Error -> {
                binding.flLoading.visibility = View.GONE
                binding.layoutFragmentListFilms.showSnackBar(
                        getString(R.string.error),
                        getString(R.string.reload),
                        {viewModel.getFilmFromLocalSource()}
                )
            }
        }
    }

    private fun View.showSnackBar(
            text: String,
            actionText: String,
            action: (View) -> Unit,
            length: Int = Snackbar.LENGTH_INDEFINITE
    ){
        Snackbar.make(this, text, length).setAction(actionText, action).show()
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