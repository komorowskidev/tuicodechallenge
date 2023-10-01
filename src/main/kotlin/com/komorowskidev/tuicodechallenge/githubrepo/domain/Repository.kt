package com.komorowskidev.tuicodechallenge.githubrepo.domain

data class Repository(
    val repositoryName: String,
    val ownerLogin: String,
    val branches: List<Branch>
)
