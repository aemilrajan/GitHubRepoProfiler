package com.scotiabank.andrewemilrajan.githubrepoprofiler.models

import com.scotiabank.andrewemilrajan.githubrepoprofiler.utils.StringHelper

/**
 * Created by andrewemilrajan on 2019-09-22.
 */

data class User(var name: String = StringHelper.EMPTY, var avatarUrl: String = StringHelper.EMPTY)

