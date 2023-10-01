package com.komorowskidev.tuicodechallenge.githubrepo.api

import com.komorowskidev.tuicodechallenge.githubrepo.domain.GithubRepoService
import com.komorowskidev.tuicodechallenge.githubrepo.domain.Repository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api")
class GithubRepositoryController(private val githubRepoService: GithubRepoService) {

    @GetMapping(value = ["user/{userName}/repositories"], produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun getRepositoriesByUserName(
        @PathVariable userName: String,
        @RequestHeader("Accept") acceptHeader: String
    ): Flux<Repository> {
        if(acceptHeader.contains(MediaType.APPLICATION_XML_VALUE)) {
            throw XmlResponseException("Format $acceptHeader is not supported")
        }
        return githubRepoService.readRepositories(userName)
    }
}
