package com.komorowskidev.tuicodechallenge.githubrepo.domain.type

data class GithubRepository(
    val name: String,
    val owner: RepositoryOwner,
    val fork: Boolean,
)
