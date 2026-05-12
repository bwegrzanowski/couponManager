package com.empik.couponManager.exception

class CouponAlreadyExistsException(code: String)
    : RuntimeException("Coupon with code: '$code' already exists!")

class CouponNotFoundException(code: String)
    : RuntimeException("Could not found coupon with code: '$code'!")

class CouponUsedOutException(code: String)
    : RuntimeException("Coupon '$code' already used!")

class CouponUsedByUserException(code: String)
    : RuntimeException("Coupon '$code' already used by current user!")

class CouponCountryCodeFormatException(countryCode: String)
    : RuntimeException("Country code: '$countryCode' not found! Try with 2 letters representing an existing country!")

class CouponWrongCountryCodeOriginException(countryCode: String)
    : RuntimeException("Code can be activated only from within country: '$countryCode'!")

class CouponMaxUsageNegativeException(maxUsages: Int)
    : RuntimeException("Max usages: '$maxUsages' should be greater than 0!")

class UnknownIpException
    : RuntimeException("Could not determine mandatory request ip!")
