package com.komorowskidev.tuicodechallenge.githubrepo.api

data class RepositoryDto(
    val repositoryName: String,
    val ownerLogin: String,
    val branches: List<BranchDto>
)
