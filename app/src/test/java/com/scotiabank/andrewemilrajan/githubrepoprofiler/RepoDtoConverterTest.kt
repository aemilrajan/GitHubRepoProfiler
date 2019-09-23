package com.scotiabank.andrewemilrajan.githubrepoprofiler

import com.scotiabank.andrewemilrajan.githubrepoprofiler.converters.RepoDtoConverter
import com.scotiabank.andrewemilrajan.githubrepoprofiler.dtos.DtoRepo
import com.scotiabank.andrewemilrajan.githubrepoprofiler.models.Repo
import com.scotiabank.andrewemilrajan.githubrepoprofiler.utils.StringHelper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

/**
 * Created by andrewemilrajan on 2019-09-22.
 */

class RepoDtoConverterTest {

    private val primaryName = "git-consortium"
    private val primaryDescription = "This repo is for demonstration purposes only."
    private val primaryUpdatedAt = "2019-03-26T02:39:00Z"
    private val primaryUpdatedAtFormatted = "Mar 26, 2019 2:39:00 AM"
    private val primaryStargazersCount = 9
    private val primaryForks = 34

    private val secondaryName = "hello-world"
    private val secondaryDescription = "My first repository on GitHub."
    private val secondaryUpdatedAt = "2019-09-19T02:50:07Z"
    private val secondaryUpdatedAtFormatted = "Sep 19, 2019 2:50:07 AM"
    private val secondaryStargazersCount = 18
    private val secondaryForks = 48

    private var primaryDtoRepo: DtoRepo? = null
    private var secondaryDtoRepo: DtoRepo? = null
    private var dtoRepos: ArrayList<DtoRepo?>? = null
    private lateinit var repoDtoConverter: RepoDtoConverter

    @Before
    fun setup() {
        primaryDtoRepo = DtoRepo(primaryName, primaryDescription, primaryUpdatedAt, primaryStargazersCount, primaryForks)
        secondaryDtoRepo = DtoRepo(secondaryName, secondaryDescription, secondaryUpdatedAt, secondaryStargazersCount, secondaryForks)
        setupDtoRepos()

        repoDtoConverter = RepoDtoConverter()
    }

    // Convert Singular Tests

    @Test
    fun convertNullDtoRepoToRepo() {
        primaryDtoRepo = null

        val repo = repoDtoConverter.convertSingular(primaryDtoRepo)
        assertNull(repo)
    }

    @Test
    fun convertDtoRepoWithNullNameDescriptionAndUpdatedAtToRepo() {
        setupDtoRepoWithNullNameDescriptionAndUpdatedAt(primaryDtoRepo)

        val repo = repoDtoConverter.convertSingular(primaryDtoRepo)
        repo?.let {
            assertNameDescriptionAndUpdatedAtAreEmpty(it)
            assertPrimaryStargazersCountAndForks(it)
        }
    }

    @Test
    fun convertDtoRepoToRepo() {
        val repo = repoDtoConverter.convertSingular(primaryDtoRepo)
        repo?.let {
            assertPrimaryRepoFields(it)
        }
    }

    // Convert Multiple Tests

    @Test
    fun convertNullDtoReposToRepos() {
        dtoRepos = null

        val repos = repoDtoConverter.convert(dtoRepos)
        assertEquals(repos, ArrayList<Repo>())
    }

    @Test
    fun convertDtoReposWithNullDtoRepoToRepos() {
        dtoRepos = ArrayList()
        dtoRepos?.add(null)

        val repos = repoDtoConverter.convert(dtoRepos)
        assertEquals(repos, ArrayList<Repo>())
    }

    @Test
    fun convertDtoReposWithNullNameDescriptionAndUpdatedAtToRepos() {
        setupDtoRepoWithNullNameDescriptionAndUpdatedAt(primaryDtoRepo)
        setupDtoRepoWithNullNameDescriptionAndUpdatedAt(secondaryDtoRepo)

        setupDtoRepos()

        val repos = repoDtoConverter.convert(dtoRepos)

        val primaryRepo = repos[0]
        assertNameDescriptionAndUpdatedAtAreEmpty(primaryRepo)
        assertPrimaryStargazersCountAndForks(primaryRepo)

        val secondaryRepo = repos[1]
        assertNameDescriptionAndUpdatedAtAreEmpty(secondaryRepo)
        assertSecondaryStargazersCountAndForks(secondaryRepo)
    }

    @Test
    fun convertDtoReposToRepos() {
        val repos = repoDtoConverter.convert(dtoRepos)

        val primaryRepo = repos[0]
        assertPrimaryRepoFields(primaryRepo)

        val secondaryRepo = repos[1]
        assertEquals(secondaryRepo.name, secondaryName)
        assertEquals(secondaryRepo.description, secondaryDescription)
        assertEquals(secondaryRepo.updatedAt, secondaryUpdatedAtFormatted)
        assertSecondaryStargazersCountAndForks(secondaryRepo)
    }

    // Helper Methods

    private fun setupDtoRepos() {
        dtoRepos = ArrayList()
        dtoRepos?.let {
            it.add(primaryDtoRepo)
            it.add(secondaryDtoRepo)
        }
    }

    private fun setupDtoRepoWithNullNameDescriptionAndUpdatedAt(dtoRepo: DtoRepo?) {
        dtoRepo?.let {
            it.name = null
            it.description = null
            it.updatedAt = null
        }
    }

    private fun assertNameDescriptionAndUpdatedAtAreEmpty(repo: Repo) {
        assertEquals(repo.name, StringHelper.EMPTY)
        assertEquals(repo.description, StringHelper.EMPTY)
        assertEquals(repo.updatedAt, StringHelper.EMPTY)
    }

    private fun assertPrimaryStargazersCountAndForks(repo: Repo) {
        assertEquals(repo.stargazersCount, primaryStargazersCount)
        assertEquals(repo.forks, primaryForks)
    }

    private fun assertSecondaryStargazersCountAndForks(repo: Repo) {
        assertEquals(repo.stargazersCount, secondaryStargazersCount)
        assertEquals(repo.forks, secondaryForks)
    }

    private fun assertPrimaryRepoFields(primaryRepo: Repo) {
        assertEquals(primaryRepo.name, primaryName)
        assertEquals(primaryRepo.description, primaryDescription)
        assertEquals(primaryRepo.updatedAt, primaryUpdatedAtFormatted)
        assertPrimaryStargazersCountAndForks(primaryRepo)
    }
}