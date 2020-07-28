package com.example.myudacitypopmovies.ui.movielist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.myudacitypopmovies.LoadingActivity
import com.example.myudacitypopmovies.R
import com.example.myudacitypopmovies.ui.movielist.discover.DiscoverMoviesFragment
import com.example.myudacitypopmovies.ui.movielist.favorites.FavoriteMoviesFragment
import com.example.myudacitypopmovies.utils.ActivityUtils.replaceFragmentInActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val intent = Intent(this, LoadingActivity::class.java)
            startActivity(intent)
            setupViewFragment()
        }
        setupToolbar()
        setupBottomNavigation()
    }

    private fun setupViewFragment() {
        // show discover movies fragment by default
        val discoverMoviesFragment = DiscoverMoviesFragment.newInstance()
        replaceFragmentInActivity(
                supportFragmentManager, discoverMoviesFragment, R.id.fragment_container)
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_discover -> {
                    replaceFragmentInActivity(
                            supportFragmentManager, DiscoverMoviesFragment.newInstance(),
                            R.id.fragment_container)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_favorites -> {
                    replaceFragmentInActivity(
                            supportFragmentManager, FavoriteMoviesFragment.newInstance(),
                            R.id.fragment_container)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }
}