package com.komorowskidev.tuicodechallenge.githubrepo.api

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [WireMockInitializer::class])
class GithubRepositoryIT(@Autowired private val githubApiMock: GithubApiMock) {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun beforeEach() {
        githubApiMock.reset()
    }

    @Test
    fun `should call external API and return repositories`() {
        val userName = "name1"
        val repoName = "repo1"
        val branchName = "branch1"
        val sha = "sha1"
        githubApiMock.stubForRepos(userName, repoName)
        githubApiMock.stubForBranches(userName, repoName, branchName, sha)
        webTestClient
            .get()
            .uri("/api/users/$userName/repositories")
            .header("Accept", "application/json")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].repositoryName").isEqualTo(repoName)
            .jsonPath("$[0].ownerLogin").isEqualTo(userName)
            .jsonPath("$[0].branches[0].name").isEqualTo(branchName)
            .jsonPath("$[0].branches[0].commit.sha").isEqualTo(sha)
    }

    @Test
    fun `should return 404 when user not found`() {
        val userName = "name1"
        githubApiMock.stubForReposAndReturnNotFound(userName)
        webTestClient
            .get()
            .uri("/api/users/$userName/repositories")
            .header("Accept", "application/json")
            .exchange()
            .expectStatus().isEqualTo(404)
    }

    @Test
    fun `should return 406 when accept header is xml`() {
        webTestClient
            .get()
            .uri("/api/users/userName/repositories")
            .header("Accept", "application/xml")
            .exchange()
            .expectStatus().isEqualTo(406)
    }
}
