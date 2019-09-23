package com.scotiabank.andrewemilrajan.githubrepoprofiler.converters

import com.scotiabank.andrewemilrajan.githubrepoprofiler.dtos.DtoUser
import com.scotiabank.andrewemilrajan.githubrepoprofiler.models.User
import com.scotiabank.andrewemilrajan.githubrepoprofiler.utils.StringHelper

/**
 * Created by andrewemilrajan on 2019-09-22.
 */

class UserDtoConverter {

    fun convert(dtoUser: DtoUser?): User? {
        dtoUser ?: return null

        return User().apply {
            name = dtoUser.name ?: StringHelper.EMPTY
            avatarUrl = dtoUser.avatarUrl ?: StringHelper.EMPTY
        }
    }
}
