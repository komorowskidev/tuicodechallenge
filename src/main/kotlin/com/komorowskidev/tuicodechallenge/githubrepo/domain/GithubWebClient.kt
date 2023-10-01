package com.komorowskidev.tuicodechallenge.githubrepo.domain

import com.komorowskidev.tuicodechallenge.githubrepo.GithubConfiguration
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Flux

@Service
class GithubWebClient(webClientBuilder: WebClient.Builder, private val githubConfiguration: GithubConfiguration) {
    private val webClient = webClientBuilder.baseUrl(githubConfiguration.baseUrl).build()

    fun getRepositories(ownerName: String): Flux<GithubRepository> {
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/users/{ownerName}/repos").build(ownerName)
            }
            .header(githubConfiguration.headerName, githubConfiguration.headerValue)
            .retrieve()
            .bodyToFlux(GithubRepository::class.java)
            .onErrorResume(WebClientResponseException.NotFound::class.java) {
                throw UserNotFoundException("User $ownerName does not exist")
            }
    }

    fun getBranches(ownerName: String, repoName: String): Flux<Branch> {
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/repos/{ownerName}/{repoName}/branches").build(ownerName, repoName)
            }
            .header(githubConfiguration.headerName, githubConfiguration.headerValue)
            .retrieve()
            .bodyToFlux(Branch::class.java)
            .onErrorResume(WebClientResponseException.NotFound::class.java) { Flux.empty() }
    }

}
