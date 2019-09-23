package com.scotiabank.andrewemilrajan.githubrepoprofiler.requests

import android.app.Activity

import com.scotiabank.andrewemilrajan.githubrepoprofiler.converters.UserDtoConverter
import com.scotiabank.andrewemilrajan.githubrepoprofiler.dtos.DtoUser
import com.scotiabank.andrewemilrajan.githubrepoprofiler.interfaces.GitHubApi
import com.scotiabank.andrewemilrajan.githubrepoprofiler.models.User

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by andrewemilrajan on 2019-09-22.
 */

class FetchUserRequest(private val activity: Activity, private val userId: String) : Callback<DtoUser> {

    fun launch() {
        val retrofit = Retrofit.Builder()
                .baseUrl(GitHubApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val gitHubApi = retrofit.create(GitHubApi::class.java)

        val call = gitHubApi.getDtoUser(userId)
        call.enqueue(this)
    }

    override fun onResponse(call: Call<DtoUser>, response: Response<DtoUser>) {
        if (response.isSuccessful) {
            val callback = activity as? Callback
            val user = UserDtoConverter().convert(response.body())
            if (user != null) {
                callback?.handleFetchUserSuccess(user)
            }
        }
    }

    override fun onFailure(call: Call<DtoUser>, t: Throwable) {
        t.printStackTrace()
    }

    interface Callback {

        /**
         * Handle the process of successfully fetching the user,
         * @param user to be referenced.
         */
        fun handleFetchUserSuccess(user: User)
    }
}
