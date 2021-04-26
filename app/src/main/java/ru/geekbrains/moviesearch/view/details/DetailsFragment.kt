package ru.geekbrains.moviesearch.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geekbrains.moviesearch.databinding.FragmentDetailsBinding
import ru.geekbrains.moviesearch.model.FilmsDTO

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val filmBundle: FilmsDTO? by lazy {
        arguments?.getParcelable<FilmsDTO>(BUNDLE_KEY)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filmBundle?.apply {
            binding.tvDetailsName.text = this.original_title
            binding.tvDetailsDescription.text = this.overview
            binding.tvDetailsYear.text = this.release_date
            binding.tvDetailsRank.text = this.popularity.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
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


