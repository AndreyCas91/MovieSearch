package ru.geekbrains.moviesearch.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geekbrains.moviesearch.databinding.FragmentDetailsBinding
import ru.geekbrains.moviesearch.model.ArrayFilms
import ru.geekbrains.moviesearch.model.Film

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmBundle: ArrayFilms

    private val onLoadListener : FilmsLoaderListener =
        object : FilmsLoaderListener{
            override fun onLoaded() {
                TODO("Not yet implemented")
            }

            override fun onFailed() {
                TODO("Not yet implemented")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmBundle = arguments?.getParcelable(BUNDLE_KEY) ?: ArrayFilms()

        val loader = FilmsLoader(onLoadListener)
        loader.loadFilms()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        const val BUNDLE_KEY = "films"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}


