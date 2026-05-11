package com.empik.couponManager.model

import java.util.UUID

data class UseCouponRequest(
    val code: String,
    val userId: UUID,
)
