package com.scotiabank.andrewemilrajan.githubrepoprofiler.interfaces

import com.scotiabank.andrewemilrajan.githubrepoprofiler.dtos.DtoRepo
import com.scotiabank.andrewemilrajan.githubrepoprofiler.dtos.DtoUser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by andrewemilrajan on 2019-09-22.
 */

interface GitHubApi {

    @GET("{userId}")
    fun getDtoUser(@Path("userId") userId: String): Call<DtoUser>

    @GET("{userId}/repos")
    fun getDtoRepos(@Path("userId") userId: String): Call<ArrayList<DtoRepo?>>

    companion object {
        const val BASE_URL = "https://api.github.com/users/"
    }
}
