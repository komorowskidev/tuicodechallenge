package com.komorowskidev.tuicodechallenge.githubrepo.api

import com.komorowskidev.tuicodechallenge.githubrepo.domain.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(e: UserNotFoundException): ResponseEntity<ErrorResponse> {
        val httpStatus = HttpStatus.NOT_FOUND
        return ResponseEntity(ErrorResponse(httpStatus.value(), e.message ?: "An error occurred"), httpStatus)
    }

    @ExceptionHandler(XmlResponseException::class)
    fun handleErrorResponseException(e: XmlResponseException): ResponseEntity<String> {
        val httpStatus = HttpStatus.NOT_ACCEPTABLE
        val response = "<status>${httpStatus.value()}</status><Message>${e.message}</Message>"
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_XML).body(response)
    }
}
