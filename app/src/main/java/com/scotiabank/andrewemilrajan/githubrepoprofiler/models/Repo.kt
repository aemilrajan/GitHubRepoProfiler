package com.scotiabank.andrewemilrajan.githubrepoprofiler.models

import com.scotiabank.andrewemilrajan.githubrepoprofiler.utils.StringHelper
import java.io.Serializable

/**
 * Created by andrewemilrajan on 2019-09-22.
 */

data class Repo(var name: String = StringHelper.EMPTY, var description: String = StringHelper.EMPTY,
                var updatedAt: String = StringHelper.EMPTY, var stargazersCount: Int = 0,
                var forks: Int = 0) : Serializable
