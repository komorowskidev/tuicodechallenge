package com.komorowskidev.tuicodechallenge.githubrepo.api

import com.komorowskidev.tuicodechallenge.githubrepo.domain.GithubRepoService
import com.komorowskidev.tuicodechallenge.githubrepo.domain.type.Repository
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.http.MediaType
import reactor.core.publisher.Flux

class GithubRepositoryControllerTest {

    private val githubRepoServiceMock: GithubRepoService = mock()
    private val controller = GithubRepositoryController(githubRepoServiceMock)

    @Test
    fun `getRepositoriesByUserName should call service and return result`() {
        // given
        val userName = "user"
        val acceptHeader = MediaType.APPLICATION_JSON_VALUE
        val repositories = Flux.empty<Repository>()
        whenever(githubRepoServiceMock.readRepositories(userName)).thenReturn(repositories)

        // when
        val actual = controller.getRepositoriesByUserName(userName, acceptHeader)

        // then
        assertSame(repositories, actual)
    }

    @Test
    fun `getRepositoriesByUserName should throw XmlResponseException when acceptHeader is XML`() {
        // given
        val userName = "user"
        val acceptHeader = MediaType.APPLICATION_XML_VALUE
        val repositories = Flux.empty<Repository>()
        whenever(githubRepoServiceMock.readRepositories(userName)).thenReturn(repositories)

        // when
        assertThrows<XmlResponseException> {
            controller.getRepositoriesByUserName(userName, acceptHeader)
        }

        // then
        verify(githubRepoServiceMock, never()).readRepositories(any())
    }
}
