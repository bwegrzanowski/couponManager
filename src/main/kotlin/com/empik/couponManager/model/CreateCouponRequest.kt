package com.empik.couponManager.model

data class CreateCouponRequest(
    val code: String,
    val maxUsages: Int,
    val countryCode: String,
)
