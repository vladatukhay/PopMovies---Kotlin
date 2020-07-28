package com.example.myudacitypopmovies.ui.moviedetails

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myudacitypopmovies.R
import com.example.myudacitypopmovies.databinding.ActivityDetailsBinding
import com.example.myudacitypopmovies.ui.moviedetails.cast.CastAdapter
import com.example.myudacitypopmovies.ui.moviedetails.reviews.ReviewsAdapter
import com.example.myudacitypopmovies.ui.moviedetails.trailers.TrailersAdapter
import com.example.myudacitypopmovies.utils.Constants
import com.example.myudacitypopmovies.utils.Injection
import com.example.myudacitypopmovies.utils.UiUtils
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.snackbar.Snackbar

class DetailsActivity : AppCompatActivity() {
    private var mBinding: ActivityDetailsBinding? = null
    private var mViewModel: MovieDetailsViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieId = intent.getLongExtra(EXTRA_MOVIE_ID, DEFAULT_ID.toLong())
        if (movieId == DEFAULT_ID.toLong()) {
            closeOnError()
            return
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        mBinding.setLifecycleOwner(this)
        mViewModel = obtainViewModel()
        mViewModel!!.init(movieId)
        setupTrailersAdapter()
        setupCastAdapter()
        setupReviewsAdapter()

        // observe result
        mViewModel!!.result.observe(this, Observer { resource ->
            if (resource.data != null &&
                    resource.data.movie != null) {
                mViewModel!!.isFavorite = resource.data.movie!!.isFavorite
                invalidateOptionsMenu()
            }
            mBinding.setResource(resource)
            mBinding.setMovieDetails(resource.data)
        })

        // handle retry event in case of network failure
        mBinding.networkState.retryButton.setOnClickListener { mViewModel!!.retry(movieId) }
        // Observe snackbar messages
        mViewModel!!.snackbarMessage.observe(this, Observer { message -> Snackbar.make(mBinding.getRoot(), message!!, Snackbar.LENGTH_SHORT).show() })
    }

    private fun setupTrailersAdapter() {
        val listTrailers = mBinding!!.movieDetailsInfo.listTrailers
        listTrailers.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        listTrailers.setHasFixedSize(true)
        listTrailers.adapter = TrailersAdapter()
        ViewCompat.setNestedScrollingEnabled(listTrailers, false)
    }

    private fun setupCastAdapter() {
        val listCast = mBinding!!.movieDetailsInfo.listCast
        listCast.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        listCast.adapter = CastAdapter()
        ViewCompat.setNestedScrollingEnabled(listCast, false)
    }

    private fun setupReviewsAdapter() {
        val listReviews = mBinding!!.movieDetailsInfo.listReviews
        listReviews.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        listReviews.adapter = ReviewsAdapter()
        ViewCompat.setNestedScrollingEnabled(listReviews, false)
    }

    private fun obtainViewModel(): MovieDetailsViewModel {
        val factory = Injection.provideViewModelFactory(this)
        return ViewModelProvider(this, factory).get(MovieDetailsViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.movie_details, menu)
        UiUtils.tintMenuIcon(this, menu.findItem(R.id.action_share), R.color.white)
        val favoriteItem = menu.findItem(R.id.action_favorite)
        if (mViewModel!!.isFavorite) {
            favoriteItem.setIcon(R.drawable.ic_favorite_black_24dp)
                    .setTitle(R.string.action_remove_from_favorites)
        } else {
            favoriteItem.setIcon(R.drawable.ic_favorite_border_black_24dp)
                    .setTitle(R.string.action_favorite)
        }
        UiUtils.tintMenuIcon(this, favoriteItem, R.color.white)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                val movieDetails = mViewModel!!.result.value!!.data
                val shareIntent = ShareCompat.IntentBuilder.from(this)
                        .setType("text/plain")
                        .setSubject(movieDetails!!.movie!!.title + " movie trailer")
                        .setText("Check out " + movieDetails.movie!!.title + " movie trailer at " +
                                Uri.parse(Constants.YOUTUBE_WEB_URL +
                                        movieDetails.trailers[0].key)
                        )
                        .createChooserIntent()
                var flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                if (Build.VERSION.SDK_INT >= 21) flags = flags or Intent.FLAG_ACTIVITY_NEW_DOCUMENT
                shareIntent.addFlags(flags)
                if (shareIntent.resolveActivity(packageManager) != null) {
                    startActivity(shareIntent)
                }
                true
            }
            R.id.action_favorite -> {
                mViewModel!!.onFavoriteClicked()
                invalidateOptionsMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun closeOnError() {
        throw IllegalArgumentException("Access denied.")
    }

    /**
     * sets the title on the toolbar only if the toolbar is collapsed
     */
    private fun handleCollapsedToolbarTitle() {
        mBinding!!.appbar.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                // verify if the toolbar is completely collapsed and set the movie name as the title
                if (scrollRange + verticalOffset == 0) {
                    mBinding!!.collapsingToolbar.title = mViewModel!!.result.value!!.data!!.movie!!.title
                    isShow = true
                } else if (isShow) {
                    // display an empty string when toolbar is expanded
                    mBinding!!.collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })
    }

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
        private const val DEFAULT_ID = -1
    }
}