package com.komorowskidev.tuicodechallenge.githubrepo.api

import com.komorowskidev.tuicodechallenge.githubrepo.api.Branch

data class Repository(
    val repositoryName: String,
    val ownerLogin: String,
    val branches: List<Branch>
)
