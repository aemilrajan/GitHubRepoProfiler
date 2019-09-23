package com.scotiabank.andrewemilrajan.githubrepoprofiler.dtos

import com.google.gson.annotations.SerializedName

/**
 * Created by andrewemilrajan on 2019-09-22.
 */

data class DtoRepo(@SerializedName("name") var name: String?,
                   @SerializedName("description") var description: String?,
                   @SerializedName("updated_at") var updatedAt: String?,
                   @SerializedName("stargazers_count") var stargazersCount: Int,
                   @SerializedName("forks") var forks: Int)
