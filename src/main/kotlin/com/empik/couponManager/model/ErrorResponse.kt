package com.empik.couponManager.model

import java.time.Instant

data class ErrorResponse(
    val message: String?,
    val timestamp: Instant = Instant.now(),
)
