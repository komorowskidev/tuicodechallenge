package com.komorowskidev.tuicodechallenge.githubrepo

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "github")
data class GithubConfiguration(
    val baseUrl: String,
    val headerName: String,
    val headerValue: String,
)
