package com.empik.couponManager.model.request

import java.util.UUID

data class UseCouponRequest(
    val code: String,
    val userId: UUID,
)
