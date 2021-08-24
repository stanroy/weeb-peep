package com.stanroy.weebpeep.presentation.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.stanroy.weebpeep.MainActivity
import com.stanroy.weebpeep.R
import com.stanroy.weebpeep.databinding.FragmentFavAnimeBinding
import com.stanroy.weebpeep.presentation.util.AnimePagingAdapter
import com.stanroy.weebpeep.presentation.viewmodel.FavouriteAnimeViewModel
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

class FavouriteAnimeFragment : Fragment() {

    private lateinit var binding: FragmentFavAnimeBinding
    private lateinit var viewModel: FavouriteAnimeViewModel
    private lateinit var animeAdapter: AnimePagingAdapter
    private lateinit var customTabsIntent: CustomTabsIntent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fav_anime, container, false)
        binding.lifecycleOwner = this

        viewModel = (activity as MainActivity).favouriteAnimeViewModel

        binding.viewModel = viewModel


        initializeRecyclerView()
        initializeCustomTabs()
        viewModel.fetchFavouriteFromDB()
        getPagedList()


        animeAdapter.setOnFabRmvClick { anime ->
            viewModel.removeFromFavourites(anime.malId)
        }

        animeAdapter.setOnImageClick { url ->
            customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
        }



        return binding.root
    }

    private fun getPagedList() {
        viewModel.animePagedList.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launchWhenCreated {
                it.collectLatest {
                    Timber.d("Collecting Saved")
                    animeAdapter.submitData(it)
                    animeAdapter.notifyDataSetChanged()
                }
            }
        })

    }

    private fun initializeRecyclerView() {
        animeAdapter = (activity as MainActivity).animePagingAdapter
        animeAdapter.shouldHideFab = true
        animeAdapter.submitData(lifecycle, PagingData.empty())
        binding.rvFavAnime.apply {
            adapter = animeAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun initializeCustomTabs() {
        customTabsIntent = (activity as MainActivity).customTabsIntent
    }
}
