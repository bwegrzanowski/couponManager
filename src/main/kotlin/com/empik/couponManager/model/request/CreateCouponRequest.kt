package com.empik.couponManager.model.request

data class CreateCouponRequest(
    val code: String,
    val maxUsages: Int,
    val countryCode: String,
)
