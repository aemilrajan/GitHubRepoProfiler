package com.scotiabank.andrewemilrajan.githubrepoprofiler.converters

import android.annotation.SuppressLint
import com.scotiabank.andrewemilrajan.githubrepoprofiler.dtos.DtoRepo
import com.scotiabank.andrewemilrajan.githubrepoprofiler.models.Repo
import com.scotiabank.andrewemilrajan.githubrepoprofiler.utils.StringHelper
import java.text.SimpleDateFormat

/**
 * Created by andrewemilrajan on 2019-09-22.
 */

class RepoDtoConverter {

    private val returnedLastUpdatedDateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private val expectedLastUpdatedDateFormat = "MMM dd, yyyy h:mm:ss a"

    fun convert(dtoRepos: ArrayList<DtoRepo?>?): ArrayList<Repo> {
        dtoRepos ?: return ArrayList()

        val repos = ArrayList<Repo>()
        for (dtoRepo in dtoRepos) {
            val repo = convertSingular(dtoRepo)
            if (repo != null) {
                repos.add(repo)
            }
        }
        return repos
    }

    @SuppressLint("SimpleDateFormat")
    fun convertSingular(dtoRepo: DtoRepo?): Repo? {
        dtoRepo ?: return null

        return Repo().apply {
            name = dtoRepo.name ?: StringHelper.EMPTY
            description = dtoRepo.description ?: StringHelper.EMPTY

            val dtoRepoUpdatedAt = dtoRepo.updatedAt
            if (dtoRepoUpdatedAt != null) {
                var simpleDateFormat = SimpleDateFormat(returnedLastUpdatedDateFormat)
                val lastUpdatedDate = simpleDateFormat.parse(dtoRepoUpdatedAt)
                simpleDateFormat = SimpleDateFormat(expectedLastUpdatedDateFormat)
                updatedAt = simpleDateFormat.format(lastUpdatedDate)
            } else {
                updatedAt = StringHelper.EMPTY
            }

            stargazersCount = dtoRepo.stargazersCount
            forks = dtoRepo.forks
        }
    }
}
