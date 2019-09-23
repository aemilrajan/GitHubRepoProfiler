package com.scotiabank.andrewemilrajan.githubrepoprofiler.dtos

import com.google.gson.annotations.SerializedName

/**
 * Created by andrewemilrajan on 2019-09-22.
 */

data class DtoUser(@SerializedName("name") var name: String?,
                   @SerializedName("avatar_url") var avatarUrl: String?)
