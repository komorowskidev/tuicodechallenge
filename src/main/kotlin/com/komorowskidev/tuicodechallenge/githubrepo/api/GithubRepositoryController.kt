package com.komorowskidev.tuicodechallenge.githubrepo.api

import com.komorowskidev.tuicodechallenge.githubrepo.XmlResponseException
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api")
class GithubRepositoryController {

    @GetMapping(value = ["user/{userName}/repositories"], produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun getRepositoriesByUserName(@PathVariable userName: String, @RequestHeader("Accept") acceptHeader: String): Flux<Repository> {
        if(acceptHeader.contains(MediaType.APPLICATION_XML_VALUE)) {
            throw XmlResponseException("Format $acceptHeader is not supported")
        }

//        throw ResponseStatusException(HttpStatus.NOT_FOUND, "User $userName does not exist")

        val branch1 = Branch("branchName1", "sha1")
        val branch2 = Branch("branchName2", "sha2")
        val branch3 = Branch("branchName3", "sha3")
        val repo1 = Repository("repoName1", "owner1", listOf(branch1, branch2))
        val repo2 = Repository("repoName2", "owner2", listOf(branch3))
        return Flux.just(repo1, repo2)
    }
}