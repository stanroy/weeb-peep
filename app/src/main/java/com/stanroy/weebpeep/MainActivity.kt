package com.stanroy.weebpeep

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.stanroy.weebpeep.databinding.ActivityMainBinding
import com.stanroy.weebpeep.presentation.util.AnimePagingAdapter
import com.stanroy.weebpeep.presentation.viewmodel.FavouriteAnimeViewModel
import com.stanroy.weebpeep.presentation.viewmodel.FavouriteAnimeViewModelFactory
import com.stanroy.weebpeep.presentation.viewmodel.UpcomingAnimeViewModel
import com.stanroy.weebpeep.presentation.viewmodel.UpcomingAnimeViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var upcomingAnimeViewModelFactory: UpcomingAnimeViewModelFactory
    lateinit var upcomingAnimeViewModel: UpcomingAnimeViewModel

    @Inject
    lateinit var favouriteAnimeViewModelFactory: FavouriteAnimeViewModelFactory
    lateinit var favouriteAnimeViewModel: FavouriteAnimeViewModel

    @Inject
    lateinit var animePagingAdapter: AnimePagingAdapter

    @Inject
    lateinit var sharedPref: SharedPreferences

    lateinit var customTabsIntent: CustomTabsIntent

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setExitAnim(R.anim.exit_to_top)
            .setEnterAnim(R.anim.enter_to_next)
            .setPopUpTo(navController.graph.startDestination, false)
            .build()

        binding.bnvMenu.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.upcomingAnimeFragment -> {
                    navController.navigate(R.id.upcomingAnimeFragment, null, options)
                }

                R.id.favouriteAnimeFragment -> {
                    navController.navigate(R.id.favouriteAnimeFragment, null, options)
                }
            }
            true
        }

        binding.bnvMenu.setOnNavigationItemReselectedListener { item ->
            return@setOnNavigationItemReselectedListener
        }

        upcomingAnimeViewModel =
            ViewModelProvider(
                this,
                upcomingAnimeViewModelFactory
            ).get(UpcomingAnimeViewModel::class.java)

        favouriteAnimeViewModel =
            ViewModelProvider(
                this,
                favouriteAnimeViewModelFactory
            ).get(FavouriteAnimeViewModel::class.java)

        initializeCustomTabs()

    }

    private fun initializeCustomTabs() {
        val customTabsIntentBuilder = CustomTabsIntent.Builder()
        customTabsIntentBuilder.setShowTitle(true)
        val blueMunsell = ContextCompat.getColor(
            applicationContext,
            R.color.blue_munsell
        )
        val params = CustomTabColorSchemeParams.Builder()
            .setNavigationBarColor(blueMunsell)
            .setToolbarColor(blueMunsell)
            .build()
        customTabsIntentBuilder.setDefaultColorSchemeParams(params)
        customTabsIntent = customTabsIntentBuilder.build()
    }


}


// use cases:
//
//        (base)
//        - get upcoming anime
//        - save anime
//        - show saved anime
//        - delete saved anime
