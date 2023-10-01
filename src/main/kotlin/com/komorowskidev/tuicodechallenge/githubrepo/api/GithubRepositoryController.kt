package com.komorowskidev.tuicodechallenge.githubrepo.api

import com.komorowskidev.tuicodechallenge.githubrepo.domain.GithubRepoService
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
    ): Flux<RepositoryDto> {
        if(acceptHeader.contains(MediaType.APPLICATION_XML_VALUE)) {
            throw XmlResponseException("Format $acceptHeader is not supported")
        }
        githubRepoService.readRepositories(userName)

//        throw ResponseStatusException(HttpStatus.NOT_FOUND, "User $userName does not exist")

        val branchDto1 = BranchDto("branchName1", "sha1")
        val branchDto2 = BranchDto("branchName2", "sha2")
        val branchDto3 = BranchDto("branchName3", "sha3")
        val repo1 = RepositoryDto("repoName1", "owner1", listOf(branchDto1, branchDto2))
        val repo2 = RepositoryDto("repoName2", "owner2", listOf(branchDto3))
        return Flux.just(repo1, repo2)
    }
}
