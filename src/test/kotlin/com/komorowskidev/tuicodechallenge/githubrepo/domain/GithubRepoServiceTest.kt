package com.komorowskidev.tuicodechallenge.githubrepo.domain

import com.komorowskidev.tuicodechallenge.githubrepo.domain.type.Branch
import com.komorowskidev.tuicodechallenge.githubrepo.domain.type.Commit
import com.komorowskidev.tuicodechallenge.githubrepo.domain.type.GithubRepository
import com.komorowskidev.tuicodechallenge.githubrepo.domain.type.Repository
import com.komorowskidev.tuicodechallenge.githubrepo.domain.type.RepositoryOwner
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class GithubRepoServiceTest {

    private val githubWebClientMock: GithubWebClient = mock()
    private val service = GithubRepoService(githubWebClientMock)

    @Test
    fun `readRepositories should call webClient and return repositories`() {
        // given
        val ownerName = "owner"
        val repositoryName1 = "repo1"
        val repositoryName2 = "repo2"
        val fork = true
        val githubRepository1 = GithubRepository(repositoryName1, RepositoryOwner(ownerName), !fork)
        val githubRepository2 = GithubRepository(repositoryName2, RepositoryOwner(ownerName), !fork)
        val branch1 = Branch("branch1", Commit("sha1"))
        val branch2 = Branch("branch2", Commit("sha2"))
        val branch3 = Branch("branch3", Commit("sha3"))
        val expectedRepository1 = Repository(repositoryName1, ownerName, listOf(branch1, branch2))
        val expectedRepository2 = Repository(repositoryName2, ownerName, listOf(branch3))
        whenever(githubWebClientMock.getRepositories(ownerName)).thenReturn(
            Flux.just(
                githubRepository1,
                githubRepository2,
            ),
        )
        whenever(githubWebClientMock.getBranches(ownerName, repositoryName1)).thenReturn(Flux.just(branch1, branch2))
        whenever(githubWebClientMock.getBranches(ownerName, repositoryName2)).thenReturn(Flux.just(branch3))

        // when
        val actual = service.readRepositories(ownerName)

        // then
        StepVerifier.create(actual)
            .expectNext(expectedRepository1)
            .expectNext(expectedRepository2)
            .verifyComplete()
    }

    @Test
    fun `readRepositories should ignore fork`() {
        // given
        val ownerName = "owner"
        val repositoryName1 = "repo1"
        val repositoryName2 = "repo2"
        val fork = true
        val githubRepository1 = GithubRepository(repositoryName1, RepositoryOwner(ownerName), fork)
        val githubRepository2 = GithubRepository(repositoryName2, RepositoryOwner(ownerName), !fork)
        val branch3 = Branch("branch3", Commit("sha3"))
        val expectedRepository2 = Repository(repositoryName2, ownerName, listOf(branch3))
        whenever(githubWebClientMock.getRepositories(ownerName)).thenReturn(
            Flux.just(
                githubRepository1,
                githubRepository2,
            ),
        )
        whenever(githubWebClientMock.getBranches(ownerName, repositoryName2)).thenReturn(Flux.just(branch3))

        // when
        val actual = service.readRepositories(ownerName)

        // then
        StepVerifier.create(actual)
            .expectNext(expectedRepository2)
            .verifyComplete()
    }
}
