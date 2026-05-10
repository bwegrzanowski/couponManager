package com.empik.couponManager.model

import java.time.Instant

data class Coupon(
    val id: CouponId,
    val version: Version,
    val code: Code,
    val createdAt: CreatedAt,
    val maxUsages: MaxUsages,
    val countryCode: CountryCode,
)

fun Coupon.toResponse(): CouponResponse =
    CouponResponse(
        id = id.value,
        version = version.value,
        code = code.value,
        createdAt = createdAt.value,
        maxUsages = maxUsages.value,
        countryCode = countryCode.value
    )

@JvmInline
value class CouponId(val value: Long)

@JvmInline
value class Version(val value: Int)

@JvmInline
value class Code(val value: String)

@JvmInline
value class CreatedAt(val value: Instant)

@JvmInline
value class MaxUsages(val value: Int)

@JvmInline
value class CountryCode(val value: String)
