package com.komorowskidev.tuicodechallenge.githubrepo

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponse(
    val status: Int,
    @JsonProperty("Message") val message : String,
)
