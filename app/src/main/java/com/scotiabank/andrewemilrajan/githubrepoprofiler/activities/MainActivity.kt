package com.scotiabank.andrewemilrajan.githubrepoprofiler.activities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.scotiabank.andrewemilrajan.githubrepoprofiler.R
import com.scotiabank.andrewemilrajan.githubrepoprofiler.adapters.RepoListAdapter
import com.scotiabank.andrewemilrajan.githubrepoprofiler.fragments.RepoInfoBottomSheetFragment
import com.scotiabank.andrewemilrajan.githubrepoprofiler.models.Repo
import com.scotiabank.andrewemilrajan.githubrepoprofiler.models.User
import com.scotiabank.andrewemilrajan.githubrepoprofiler.requests.FetchReposRequest
import com.scotiabank.andrewemilrajan.githubrepoprofiler.requests.FetchUserRequest
import com.scotiabank.andrewemilrajan.githubrepoprofiler.utils.StringHelper
import com.scotiabank.andrewemilrajan.githubrepoprofiler.viewholders.RepoListViewHolder
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * Created by andrewemilrajan on 2019-09-22.
 */

class MainActivity : AppCompatActivity(), FetchUserRequest.Callback, FetchReposRequest.Callback,
        RepoListViewHolder.Listener, RepoInfoBottomSheetFragment.Listener {

    private val tagRepoInfoBottomSheet = "TAG_REPO_INFO_BOTTOM_SHEET"

    private val transitionUpwardsDelay = 250L

    private lateinit var searchBoxHeader: TextView
    private lateinit var searchBox: EditText
    private lateinit var avatar: ImageView
    private lateinit var name: TextView
    private lateinit var publicRepositoryList: RecyclerView

    private lateinit var transitionUpwards: Animation

    private var userId = StringHelper.EMPTY

    private var repoViewModel: Repo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchBoxHeader = findViewById(R.id.search_box_header)
        searchBox = findViewById(R.id.search_box)
        avatar = findViewById(R.id.avatar)
        name = findViewById(R.id.name)
        publicRepositoryList = findViewById(R.id.public_repository_list)

        transitionUpwards = AnimationUtils.loadAnimation(this, R.anim.transition_upwards)

        searchBox.setOnClickListener {
            searchBox.background.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_ATOP)
            searchBoxHeader.visibility = View.VISIBLE
        }

        val searchButton = findViewById<Button>(R.id.search_button)
        searchButton.setOnClickListener {
            userId = searchBox.text.toString()

            // Dismiss the soft keyboard
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(searchBox.windowToken, 0)

            // Hide previous data if it is present
            avatar.visibility = View.GONE
            name.visibility = View.GONE
            publicRepositoryList.visibility = View.GONE

            // Fetch the user information
            val fetchUserRequest = FetchUserRequest(this, userId)
            fetchUserRequest.launch()
        }
    }

    override fun handleFetchUserSuccess(user: User) {
        avatar.setImageDrawable(getDrawableFromUrl(user.avatarUrl))

        avatar.visibility = View.VISIBLE
        avatar.startAnimation(transitionUpwards)

        name.text = user.name
        name.visibility = View.VISIBLE
        name.startAnimation(transitionUpwards)

        // Fetch the repo information
        val fetchReposRequest = FetchReposRequest(this, userId)
        fetchReposRequest.launch()
    }

    @Throws(IOException::class)
    private fun getDrawableFromUrl(url: String): Drawable {
        val bitmap: Bitmap

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val httpURLConnection = URL(url).openConnection() as HttpURLConnection
        httpURLConnection.connect()

        val inputStream = httpURLConnection.inputStream
        bitmap = BitmapFactory.decodeStream(inputStream)

        return BitmapDrawable(resources, bitmap)
    }

    override fun handleFetchReposSuccess(repos: ArrayList<Repo>) {
        val repoListAdapter = RepoListAdapter(repos, this)
        publicRepositoryList.setHasFixedSize(true)
        publicRepositoryList.layoutManager = LinearLayoutManager(this)
        publicRepositoryList.adapter = repoListAdapter

        publicRepositoryList.postDelayed({
            publicRepositoryList.visibility = View.VISIBLE
            publicRepositoryList.startAnimation(transitionUpwards)
        }, transitionUpwardsDelay)
    }

    override fun onRepoItemClicked(repoViewModel: Repo) {
        this.repoViewModel = repoViewModel
        val repoInfoBottomSheetFragment = RepoInfoBottomSheetFragment()
        repoInfoBottomSheetFragment.show(supportFragmentManager, tagRepoInfoBottomSheet)
    }

    override val repoLastUpdatedDate: String
        get() = repoViewModel?.updatedAt ?: StringHelper.EMPTY

    override val repoStarCount: String
        get() = Integer.valueOf(repoViewModel?.stargazersCount ?: 0).toString()

    override val repoForkCount: String
        get() = Integer.valueOf(repoViewModel?.forks ?: 0).toString()
}
