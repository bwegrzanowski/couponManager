package com.empik.couponManager.exception

import com.empik.couponManager.model.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CouponNotFoundException::class)
    fun handleNotFound(ex: CouponNotFoundException): ResponseEntity<ErrorResponse> =
        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(ex.message))

    @ExceptionHandler(CouponAlreadyExistsException::class)
    fun handleAlreadyExists(ex: CouponAlreadyExistsException): ResponseEntity<ErrorResponse> =
        ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ErrorResponse(ex.message))

    @ExceptionHandler(CouponUsedByUserException::class)
    fun handleUsedByUser(ex: CouponUsedByUserException): ResponseEntity<ErrorResponse> =
        ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ErrorResponse(ex.message))

    @ExceptionHandler(CouponUsedOutException::class)
    fun handleUsedOut(ex: CouponUsedOutException): ResponseEntity<ErrorResponse> =
        ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ErrorResponse(ex.message))

    @ExceptionHandler(CouponCountryCodeFormatException::class)
    fun handleCountryCodeFormat(ex: CouponCountryCodeFormatException): ResponseEntity<ErrorResponse> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(ex.message))

    @ExceptionHandler(CouponWrongCountryCodeOriginException::class)
    fun handleWrongCountryCodeOrigin(ex: CouponWrongCountryCodeOriginException): ResponseEntity<ErrorResponse> =
        ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(ErrorResponse(ex.message))

    @ExceptionHandler(CouponMaxUsageNegativeException::class)
    fun handleMaxUsageNegative(ex: CouponMaxUsageNegativeException): ResponseEntity<ErrorResponse> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(ex.message))

    @ExceptionHandler(UnknownIpException::class)
    fun handleUnknownIP(ex: UnknownIpException): ResponseEntity<ErrorResponse> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(ex.message))
}
