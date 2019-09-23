package com.scotiabank.andrewemilrajan.githubrepoprofiler.requests

import android.app.Activity
import com.scotiabank.andrewemilrajan.githubrepoprofiler.converters.RepoDtoConverter
import com.scotiabank.andrewemilrajan.githubrepoprofiler.dtos.DtoRepo
import com.scotiabank.andrewemilrajan.githubrepoprofiler.interfaces.GitHubApi
import com.scotiabank.andrewemilrajan.githubrepoprofiler.models.Repo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by andrewemilrajan on 2019-09-22.
 */

class FetchReposRequest(private val activity: Activity, private val userId: String) : Callback<ArrayList<DtoRepo?>> {

    fun launch() {
        val retrofit = Retrofit.Builder()
                .baseUrl(GitHubApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val gitHubApi = retrofit.create(GitHubApi::class.java)

        val call = gitHubApi.getDtoRepos(userId)
        call.enqueue(this)
    }

    override fun onResponse(call: Call<ArrayList<DtoRepo?>>, response: Response<ArrayList<DtoRepo?>>) {
        if (response.isSuccessful) {
            val callback = activity as? Callback
            val repos = RepoDtoConverter().convert(response.body())
            callback?.handleFetchReposSuccess(repos)
        }
    }

    override fun onFailure(call: Call<ArrayList<DtoRepo?>>, t: Throwable) {
        t.printStackTrace()
    }

    interface Callback {

        /**
         * Handle the proccess of successfully fetching the repos
         *
         * @param repos
         */
        fun handleFetchReposSuccess(repos: ArrayList<Repo>)
    }
}
