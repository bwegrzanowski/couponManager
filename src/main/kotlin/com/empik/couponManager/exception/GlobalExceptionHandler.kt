package com.empik.couponManager.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CouponNotFoundException::class)
    fun handleNotFound(ex: CouponNotFoundException): ResponseEntity<String> =
        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.message)

    @ExceptionHandler(CouponAlreadyExistsException::class)
    fun handleAlreadyExists(ex: CouponAlreadyExistsException): ResponseEntity<String> =
        ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ex.message)

    @ExceptionHandler(CouponUsedByUserException::class)
    fun handleUsedByUser(ex: CouponUsedByUserException): ResponseEntity<String> =
        ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ex.message)

    @ExceptionHandler(CouponUsedOutException::class)
    fun handleUsedOut(ex: CouponUsedOutException): ResponseEntity<String> =
        ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ex.message)

    @ExceptionHandler(CouponCountryCodeFormatException::class)
    fun handleCountryCodeFormat(ex: CouponCountryCodeFormatException): ResponseEntity<String> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ex.message)

    @ExceptionHandler(CouponWrongCountryCodeOriginException::class)
    fun handleWrongCountryCodeOrigin(ex: CouponWrongCountryCodeOriginException): ResponseEntity<String> =
        ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(ex.message)

    @ExceptionHandler(CouponMaxUsageNegativeException::class)
    fun handleMaxUsageNegative(ex: CouponMaxUsageNegativeException): ResponseEntity<String> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ex.message)

    @ExceptionHandler(UnknownIpException::class)
    fun handleUnknownIP(ex: UnknownIpException): ResponseEntity<String> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ex.message)
}
