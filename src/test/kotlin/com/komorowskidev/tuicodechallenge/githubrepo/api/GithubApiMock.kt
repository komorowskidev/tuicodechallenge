package com.komorowskidev.tuicodechallenge.githubrepo.api

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration

class GithubApiMock {

    private val wireMockServer = WireMockServer(WireMockConfiguration.options().dynamicPort()).apply { start() }

    val baseUrl: String = wireMockServer.baseUrl()

    fun reset() {
        wireMockServer.resetAll()
    }

    fun stubForRepos(ownerName: String, repoName: String) {
        val mockedRepositories = "[{\"name\": \"$repoName\", \"fork\": false}]"
        wireMockServer.stubFor(
            WireMock.get(WireMock.urlEqualTo("/users/$ownerName/repos")).willReturn(
                WireMock.aResponse().withStatus(200).withHeader("Content-Type", "application/json")
                    .withBody(mockedRepositories),
            ),
        )
    }

    fun stubForBranches(ownerName: String, repoName: String, branchName: String, sha: String) {
        val mockedBranches = "[{\"name\": \"$branchName\", \"commit\": {\"sha\": \"$sha\"}}]"
        wireMockServer.stubFor(
            WireMock.get(WireMock.urlEqualTo("/repos/$ownerName/$repoName/branches")).willReturn(
                WireMock.aResponse().withStatus(200).withHeader("Content-Type", "application/json")
                    .withBody(mockedBranches),
            ),
        )
    }

    fun stubForReposAndReturnNotFound(ownerName: String) {
        wireMockServer.stubFor(
            WireMock.get(WireMock.urlEqualTo("/users/$ownerName/repos"))
                .willReturn(WireMock.aResponse().withStatus(404)),
        )
    }
}
