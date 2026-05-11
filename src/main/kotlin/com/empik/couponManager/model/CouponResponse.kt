package com.empik.couponManager.model

import java.time.Instant

data class CouponResponse(
    val id: Long,
    val usageCount: Int,
    val code: String,
    val createdAt: Instant,
    val maxUsages: Int,
    val countryCode: String,
)