package com.empik.couponManager.service

import com.empik.couponManager.domain.CouponEntity
import com.empik.couponManager.domain.toCoupon
import com.empik.couponManager.model.Coupon
import com.empik.couponManager.model.CreateCouponRequest
import com.empik.couponManager.repository.CouponRepository
import com.neovisionaries.i18n.CountryCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponService (@Autowired private val repository: CouponRepository) {
    fun createCoupon(request: CreateCouponRequest): Coupon {
        request.validate()
        val code = request.code.uppercase()
        if (repository.existsByCode(code)) throw IllegalStateException("Coupon already exists")

        val coupon = CouponEntity(
            code = code,
            maxUsages = request.maxUsages,
            countryCode = request.countryCode,
        )
        return repository.save(coupon).toCoupon()
    }


}

private fun CreateCouponRequest.validate() {
    CountryCode.getByAlpha2Code(countryCode.uppercase())
        ?: throw IllegalArgumentException("Country code: $countryCode not found! Try with 2 letters representing an existing country!")
    maxUsages.takeIf { it > 0 } ?: throw IllegalArgumentException("Max usages: $maxUsages should be greater than 0!")
}
