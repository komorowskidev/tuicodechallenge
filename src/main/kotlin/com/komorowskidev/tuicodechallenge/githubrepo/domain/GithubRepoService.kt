package com.komorowskidev.tuicodechallenge.githubrepo.domain

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class GithubRepoService(private val githubWebClient: GithubWebClient) {

    fun readRepositories(ownerName: String): Flux<Repository> {
        return githubWebClient.getRepositories(ownerName).filter {
            !it.fork
        }.flatMap { repo ->
            githubWebClient.getBranches(ownerName, repo.name).collectList().map { branches ->
                Repository(repo.name, ownerName, branches)
            }
        }
    }
}
