package com.empik.couponManager.util

import com.empik.couponManager.domain.CouponEntity
import com.empik.couponManager.model.CreateCouponRequest
import com.empik.couponManager.model.UseCouponRequest
import java.util.UUID

const val IP_HEADER = "X-Forwarded-For"
const val IP = "127.0.0.1"

val CREATE_REQUEST = CreateCouponRequest(
    code = "SUMMER",
    maxUsages = 10,
    countryCode = "PL"
)

val USE_REQUEST = UseCouponRequest(
    code = "SUMMER",
    userId = UUID.fromString("8be4df61-93ca-11d2-aa0d-00e098032b87")
)

val COUPON_ENTITY = CouponEntity(
    usageCount = 0,
    code = "SUMMER",
    maxUsages = 5,
    countryCode = "PL"
)
