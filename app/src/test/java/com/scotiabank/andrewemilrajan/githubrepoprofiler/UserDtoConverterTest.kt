package com.scotiabank.andrewemilrajan.githubrepoprofiler

import com.scotiabank.andrewemilrajan.githubrepoprofiler.converters.UserDtoConverter
import com.scotiabank.andrewemilrajan.githubrepoprofiler.dtos.DtoUser
import com.scotiabank.andrewemilrajan.githubrepoprofiler.utils.StringHelper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

/**
 * Created by andrewemilrajan on 2019-09-22.
 */

class UserDtoConverterTest {

    private val name = "The Octocat"
    private val avatarUrl = "https://avatars3.githubusercontent.com/u/583231?v=4"

    private var dtoUser: DtoUser? = null
    private lateinit var userDtoConverter: UserDtoConverter

    @Before
    fun setup() {
        dtoUser = DtoUser(name, avatarUrl)
        userDtoConverter = UserDtoConverter()
    }

    @Test
    fun convertNullDtoUserToUser() {
        dtoUser = null

        val user = userDtoConverter.convert(dtoUser)
        assertNull(user)
    }

    @Test
    fun convertDtoUserWithNullNameAndAvatarUrlToUser() {
        dtoUser?.let {
            it.name = null
            it.avatarUrl = null
        }

        val user = userDtoConverter.convert(dtoUser)
        user?.let {
            assertEquals(it.name, StringHelper.EMPTY)
            assertEquals(it.avatarUrl, StringHelper.EMPTY)
        }
    }

    @Test
    fun convertDtoUserToUser() {
        val user = userDtoConverter.convert(dtoUser)
        user?.let {
            assertEquals(it.name, name)
            assertEquals(it.avatarUrl, avatarUrl)
        }
    }
}