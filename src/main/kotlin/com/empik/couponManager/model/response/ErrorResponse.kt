package com.empik.couponManager.model.response

import java.time.Instant

data class ErrorResponse(
    val message: String?,
    val timestamp: Instant = Instant.now(),
)
