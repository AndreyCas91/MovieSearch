package ru.geekbrains.moviesearch.view.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.geekbrains.moviesearch.R
import ru.geekbrains.moviesearch.databinding.FragmentListFilmsBinding
import ru.geekbrains.moviesearch.model.*
import ru.geekbrains.moviesearch.view.details.DetailsFragment

class ListFilmsFragment : Fragment(){

    private var _binding: FragmentListFilmsBinding? = null
    private val binding get() = _binding!!

//    private val viewModel: MainViewModel by lazy {
//        ViewModelProvider(this).get(MainViewModel::class.java)
//    }

    private val onLoadListenerGenres: GenreLoader.GenresLoaderListener =
            object : GenreLoader.GenresLoaderListener{
                override fun onLoaded(arrayGenreDTO: ArrayGenreDTO) {
                    displayGenres(arrayGenreDTO)
                }

                override fun onFailed(throwable: Throwable) {
                }
            }

    private val adapter: WholeListAdapter =
        WholeListAdapter(object : OnItemViewClickListener {
            override fun onItemViewClick(films: FilmsDTO) {
                val manager = activity?.supportFragmentManager
                if(manager != null) {
                    val bundle = Bundle()
                    bundle.putParcelable(DetailsFragment.BUNDLE_KEY, films)
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
    ): View {
        _binding = FragmentListFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initLoader()
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
//        viewModel.getFilmFromLocalSource()
    }

//    private fun renderData(data: AppState){
//        when(data){
//            is AppState.Success -> {
//                binding.flLoading.visibility = View.GONE
//                setData(data.filmData)
//            }
//            is AppState.Loading -> {
//                binding.flLoading.visibility - View.VISIBLE
//            }
//            is AppState.Error -> {
//                binding.flLoading.visibility = View.GONE
//                binding.layoutFragmentListFilms.showSnackBar(
//                        getString(R.string.error),
//                        getString(R.string.reload),
//                        {viewModel.getFilmFromLocalSource()}
//                )
//            }
//        }
//    }

//    private fun View.showSnackBar(
//            text: String,
//            actionText: String,
//            action: (View) -> Unit,
//            length: Int = Snackbar.LENGTH_INDEFINITE
//    ){
//        Snackbar.make(this, text, length).setAction(actionText, action).show()
//    }

//    private fun setData(filmData: ArrayGenreDTO) {
//        filmData?.genres?.let { adapter.setItems(it) }
//    }

    private fun initAdapter(){
        binding.rvFragmentListFilms.layoutManager = LinearLayoutManager(this.context)
        binding.rvFragmentListFilms.adapter = adapter
    }

    private fun initLoader(){
        val loader = GenreLoader(onLoadListenerGenres)
        loader.loadGenres()
    }

    private fun displayGenres(arrayGenreDTO: ArrayGenreDTO){

        arrayGenreDTO?.genres?.let { adapter.setItemsGenre(it) }
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
        fun onItemViewClick(filmsDTO : FilmsDTO)
    }
}