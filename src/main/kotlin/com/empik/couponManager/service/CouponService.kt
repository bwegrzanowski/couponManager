package com.empik.couponManager.service

import com.empik.couponManager.client.IPClient
import com.empik.couponManager.domain.CouponEntity
import com.empik.couponManager.domain.UsedCouponEntity
import com.empik.couponManager.domain.toCoupon
import com.empik.couponManager.exception.CouponAlreadyExistsException
import com.empik.couponManager.exception.CouponCountryCodeFormatException
import com.empik.couponManager.exception.CouponMaxUsageNegativeException
import com.empik.couponManager.exception.CouponNotFoundException
import com.empik.couponManager.exception.CouponUsedException
import com.empik.couponManager.exception.CouponWrongCountryCodeOriginException
import com.empik.couponManager.model.Coupon
import com.empik.couponManager.model.CreateCouponRequest
import com.empik.couponManager.model.UseCouponRequest
import com.empik.couponManager.repository.CouponsRepository
import com.empik.couponManager.repository.UsedCouponsRepository
import com.neovisionaries.i18n.CountryCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponService @Autowired constructor(
    private val couponsRepository: CouponsRepository,
    private val usedCouponsRepository: UsedCouponsRepository,
    private val ipClient: IPClient,
) {
    fun createCoupon(request: CreateCouponRequest): Coupon {
        request.validate()
        val code = request.code.uppercase()
        if (couponsRepository.existsByCode(code)) throw CouponAlreadyExistsException(code)

        val coupon = CouponEntity(
            usageCount = 0,
            code = code,
            maxUsages = request.maxUsages,
            countryCode = request.countryCode,
        )
        return couponsRepository.save(coupon).toCoupon()
    }

    fun useCoupon(request: UseCouponRequest, ip: String) {
        val code = request.code.uppercase()
        if (usedCouponsRepository.existsByUserIdAndCode(request.userId, code)) throw CouponUsedException(code)
        val coupon = couponsRepository.findByCodeForUpdate(code) ?: throw CouponNotFoundException(code)
        coupon.validate(ip)
        couponsRepository.save(coupon.copy(usageCount = coupon.usageCount + 1))
        usedCouponsRepository.save(UsedCouponEntity(code = code, userId = request.userId))
    }

    private fun CouponEntity.validate(ip: String) {
        if (usageCount >= maxUsages) throw CouponUsedException(code)
        val country = ipClient.resolveCountry(ip).uppercase()
        if (countryCode != country) throw CouponWrongCountryCodeOriginException(countryCode)
    }
}

private fun CreateCouponRequest.validate() {
    CountryCode.getByAlpha2Code(countryCode.uppercase()) ?: throw CouponCountryCodeFormatException(countryCode)
    maxUsages.takeIf { it > 0 } ?: throw CouponMaxUsageNegativeException(maxUsages)
}
