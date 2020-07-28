package com.example.myudacitypopmovies.ui.movielist.discover

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.myudacitypopmovies.R
import com.example.myudacitypopmovies.ui.movielist.MoviesFilterType
import com.example.myudacitypopmovies.utils.Injection.provideViewModelFactory
import com.example.myudacitypopmovies.utils.ItemOffsetDecoration

class DiscoverMoviesFragment : Fragment() {
    private var viewModel: DiscoverMoviesViewModel? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_discover_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = obtainViewModel(activity)
        setupListAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
        //MenuItem sort = menu.findItem(R.id.action_sort_by);
        if (viewModel!!.currentSorting == MoviesFilterType.POPULAR) {
            menu.findItem(R.id.action_popular_movies).isChecked = true
        } else {
            menu.findItem(R.id.action_top_rated).isChecked = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.groupId == R.id.menu_sort_group) {
            viewModel!!.setSortMoviesBy(item.itemId)
            item.isChecked = true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupListAdapter() {
        val recyclerView: RecyclerView = activity!!.findViewById(R.id.rv_movie_list)
        val discoverMoviesAdapter = DiscoverMoviesAdapter(viewModel!!)
        val layoutManager = GridLayoutManager(activity, resources.getInteger(R.integer.span_count))

        // draw network status and errors messages to fit the whole row(3 spans)
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (discoverMoviesAdapter.getItemViewType(position) == R.layout.item_network_state) {
                    layoutManager.spanCount
                } else 1
            }
        }

        // setup recyclerView
        recyclerView.adapter = discoverMoviesAdapter
        recyclerView.layoutManager = layoutManager
        val itemDecoration = ItemOffsetDecoration(activity!!, R.dimen.item_offset)
        recyclerView.addItemDecoration(itemDecoration)

        // observe paged list
        viewModel!!.pagedList.observe(viewLifecycleOwner, Observer { movies -> discoverMoviesAdapter.submitList(movies) })

        // observe network state
        viewModel!!.networkState.observe(viewLifecycleOwner, Observer { resource -> discoverMoviesAdapter.setNetworkState(resource!!) })
    }

    companion object {
        fun newInstance(): DiscoverMoviesFragment {
            return DiscoverMoviesFragment()
        }

        fun obtainViewModel(activity: FragmentActivity?): DiscoverMoviesViewModel {
            val factory = provideViewModelFactory(activity!!)
            return ViewModelProvider(activity, factory).get(DiscoverMoviesViewModel::class.java)
        }
    }
}