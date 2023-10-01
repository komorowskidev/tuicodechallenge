package com.komorowskidev.tuicodechallenge.githubrepo

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(e: ResponseStatusException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(e.statusCode.value(), e.reason ?: "An error occurred"), e.statusCode)
    }

    @ExceptionHandler(XmlResponseException::class)
    fun handleErrorResponseException(e: XmlResponseException): ResponseEntity<String> {
        val response = "<status>${HttpStatus.NOT_ACCEPTABLE.value()}</status><Message>${e.message}</Message>"
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).contentType(MediaType.APPLICATION_XML).body(response)
    }
}