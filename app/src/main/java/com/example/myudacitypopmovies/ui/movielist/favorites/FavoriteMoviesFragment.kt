package com.example.myudacitypopmovies.ui.movielist.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myudacitypopmovies.R
import com.example.myudacitypopmovies.databinding.FragmentFavoriteMoviesBinding
import com.example.myudacitypopmovies.ui.movielist.MainActivity
import com.example.myudacitypopmovies.utils.Injection.provideViewModelFactory
import com.example.myudacitypopmovies.utils.ItemOffsetDecoration

class FavoriteMoviesFragment : Fragment() {
    private var viewModel: FavoritesViewModel? = null
    private var binding: FragmentFavoriteMoviesBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity?)!!.supportActionBar!!.title = getString(R.string.favorites)
        viewModel = obtainViewModel(activity)
        setupListAdapter()
    }

    private fun setupListAdapter() {
        val recyclerView = binding!!.moviesList.rvMovieList
        val favoritesAdapter = FavoritesAdapter()
        val layoutManager = GridLayoutManager(activity, 2)

        // setup recyclerView
        recyclerView.adapter = favoritesAdapter
        recyclerView.layoutManager = layoutManager
        val itemDecoration = ItemOffsetDecoration(activity!!, R.dimen.item_offset)
        recyclerView.addItemDecoration(itemDecoration)

        // observe favorites list
        viewModel!!.favoriteListLiveData.observe(viewLifecycleOwner, Observer { movieList ->
            if (movieList.isEmpty()) {
                // TODO: 11/7/2018 optimize this
                // display empty state since there is no favorites in database
                binding!!.moviesList.rvMovieList.visibility = View.GONE
                binding!!.emptyState.visibility = View.VISIBLE
            } else {
                binding!!.moviesList.rvMovieList.visibility = View.VISIBLE
                binding!!.emptyState.visibility = View.GONE
                favoritesAdapter.submitList(movieList)
            }
        })
    }

    private fun obtainViewModel(activity: FragmentActivity?): FavoritesViewModel {
        val factory = provideViewModelFactory(activity!!)
        return ViewModelProvider(activity, factory).get(FavoritesViewModel::class.java)
    }

    companion object {
        fun newInstance(): FavoriteMoviesFragment {
            return FavoriteMoviesFragment()
        }
    }
}