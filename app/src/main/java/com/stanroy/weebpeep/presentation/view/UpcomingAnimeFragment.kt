package com.stanroy.weebpeep.presentation.view

import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.stanroy.weebpeep.MainActivity
import com.stanroy.weebpeep.R
import com.stanroy.weebpeep.data.api.Status
import com.stanroy.weebpeep.databinding.FragmentUpcomingAnimeBinding
import com.stanroy.weebpeep.presentation.util.AnimePagingAdapter
import com.stanroy.weebpeep.presentation.viewmodel.UpcomingAnimeViewModel
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber


class UpcomingAnimeFragment : Fragment() {
    private lateinit var binding: FragmentUpcomingAnimeBinding
    private lateinit var viewModel: UpcomingAnimeViewModel
    private lateinit var animeAdapter: AnimePagingAdapter
    private lateinit var sharedPref: SharedPreferences
    private lateinit var customTabsIntent: CustomTabsIntent


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_refresh, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val currentTime = System.currentTimeMillis()
        val timeOutInMillis = 50000L


        if (sharedPref.getLong(getString(R.string.preference_time_pressed), 0L) != 0L) {
            val savedTime = sharedPref.getLong(
                getString(R.string.preference_time_pressed),
                0L
            ) + timeOutInMillis
            val diff = savedTime - currentTime

            Timber.d("Diff: $diff")
            if (diff < 0) {
                with(sharedPref.edit()) {
                    putLong(getString(R.string.preference_time_pressed), currentTime)
                    apply()
                }
                getRefreshedList()
            } else {
                Snackbar.make(
                    requireView(),
                    getString(R.string.refresh_timeout_message),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        } else {
            with(sharedPref.edit()) {
                putLong(getString(R.string.preference_time_pressed), currentTime)
                getRefreshedList()
                apply()
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_upcoming_anime,
                container, false
            )

        sharedPref = (activity as MainActivity).sharedPref
        viewModel = (activity as MainActivity).upcomingAnimeViewModel
        initializeRecyclerView()
        initializeCustomTabs()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.checkForDataChange()

        viewModel.status.observe(viewLifecycleOwner, Observer {
            val progressBar = binding.pbResourceLoading
            when (it.first) {
                Status.SUCCESS -> {
                    Timber.d("Success ${it.second}")
                    progressBar.visibility = View.GONE
                }

                Status.LOADING -> {
                    Timber.d(" Loading ${it.second}")
                    progressBar.visibility = View.VISIBLE
                }

                Status.ERR_API -> {
                    Snackbar.make(
                        requireView(),
                        "${it.second}",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                    progressBar.visibility = View.VISIBLE
                }

                Status.ERR_CACHE_INTERNET -> {
                    binding.ivNoWifi.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
                else -> progressBar.visibility = View.GONE
            }
        })

        getPagedList()

        animeAdapter.setOnFabAddClick { anime ->
            viewModel.addToFavourites(anime.malId)
        }

        animeAdapter.setOnImageClick { url ->
            customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showWebPageInfoMessage()
    }

    private fun showWebPageInfoMessage() {
        if (!sharedPref.getBoolean(getString(R.string.preference_mal_message), false)) {
            val message = Snackbar.make(
                requireView(),
                getString(R.string.web_page_info_msg),
                Snackbar.LENGTH_INDEFINITE
            )
            message.setAction("OK") {
                with(sharedPref.edit()) {
                    putBoolean(getString(R.string.preference_mal_message), true)
                    apply()
                }
            }
            message.show()
        }
    }

    private fun initializeCustomTabs() {

        customTabsIntent = (activity as MainActivity).customTabsIntent
    }

    private fun getPagedList() {
        viewModel.animePagedList.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launchWhenCreated {
                it.collectLatest {
                    Timber.d("COLLECTING")
                    animeAdapter.submitData(lifecycle, it)
                    animeAdapter.notifyDataSetChanged()
                    Timber.d("${animeAdapter.itemCount}")
                }
            }
        })
    }

    private fun getRefreshedList() {
        animeAdapter.submitData(lifecycle, PagingData.empty())
        animeAdapter.notifyDataSetChanged()
        viewModel.cleanRefreshAnimeList()
    }

    private fun initializeRecyclerView() {
        animeAdapter = (activity as MainActivity).animePagingAdapter
        animeAdapter.shouldHideFab = false
        animeAdapter.submitData(lifecycle, PagingData.empty())
        binding.rvAnimeList.apply {
            adapter = animeAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

}
