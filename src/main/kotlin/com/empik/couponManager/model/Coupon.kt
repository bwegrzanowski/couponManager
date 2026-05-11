package com.empik.couponManager.model

import java.time.Instant

data class Coupon(
    val usageCount: UsageCount,
    val code: Code,
    val createdAt: CreatedAt,
    val maxUsages: MaxUsages,
    val countryCode: CountryCode,
)

fun Coupon.toResponse(): CouponResponse =
    CouponResponse(
        code = code.value,
        createdAt = createdAt.value,
        usageCount = usageCount.value,
        maxUsages = maxUsages.value,
        countryCode = countryCode.value
    )

@JvmInline
value class UsageCount(val value: Int)

@JvmInline
value class Code(val value: String)

@JvmInline
value class CreatedAt(val value: Instant)

@JvmInline
value class MaxUsages(val value: Int)

@JvmInline
value class CountryCode(val value: String)
