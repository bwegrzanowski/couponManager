package com.empik.couponManager.unit.service

import com.empik.couponManager.client.IPClient
import com.empik.couponManager.domain.UsedCouponId
import com.empik.couponManager.exception.CouponAlreadyExistsException
import com.empik.couponManager.exception.CouponCountryCodeFormatException
import com.empik.couponManager.exception.CouponMaxUsageNegativeException
import com.empik.couponManager.exception.CouponNotFoundException
import com.empik.couponManager.exception.CouponUsedByUserException
import com.empik.couponManager.exception.CouponUsedOutException
import com.empik.couponManager.exception.CouponWrongCountryCodeOriginException
import com.empik.couponManager.repository.CouponsRepository
import com.empik.couponManager.repository.UsedCouponsRepository
import com.empik.couponManager.service.CouponService
import com.empik.couponManager.util.COUPON_ENTITY
import com.empik.couponManager.util.CREATE_REQUEST
import com.empik.couponManager.util.IP
import com.empik.couponManager.util.USE_REQUEST
import io.mockk.called
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class CouponServiceTest {

    @MockK
    lateinit var couponsRepository: CouponsRepository

    @MockK
    lateinit var usedCouponsRepository: UsedCouponsRepository

    @MockK
    lateinit var ipClient: IPClient

    @InjectMockKs
    lateinit var couponService: CouponService

    @Test
    fun `should create coupon and change code to capital letters`() {
        every { couponsRepository.existsByCode(any()) } returns false
        every { couponsRepository.save(any()) } answers { firstArg() }

        val result = couponService.createCoupon(CREATE_REQUEST.copy(code = "summer"))

        assertEquals(result.code.value, "SUMMER")
        verify(exactly = 1) { couponsRepository.save(any()) }
    }

    @Test
    fun `should throw when coupon already exists`() {
        val request = CREATE_REQUEST
        every { couponsRepository.existsByCode(any()) } returns true

        assertThrows<CouponAlreadyExistsException> { couponService.createCoupon(request) }

        verify { couponsRepository.save(any()) wasNot called }
    }

    @Test
    fun `should throw for invalid country code`() {
        val request = CREATE_REQUEST.copy(countryCode = "INVALID_CODE")

        assertThrows<CouponCountryCodeFormatException> { couponService.createCoupon(request) }
    }

    @Test
    fun `should throw error for max usages less than 0`() {
        val request = CREATE_REQUEST.copy(maxUsages = Random.nextInt(Integer.MIN_VALUE, 1))

        assertThrows<CouponMaxUsageNegativeException> { couponService.createCoupon(request) }
    }

    @Test
    fun `should use coupon`() {
        val request = USE_REQUEST
        val coupon = COUPON_ENTITY
        every { usedCouponsRepository.existsById(UsedCouponId(request.userId, request.code.uppercase())) } returns false
        every { couponsRepository.findByCodeForUpdate(request.code.uppercase()) } returns coupon
        every { ipClient.resolveCountry(any()) } returns coupon.countryCode
        every { couponsRepository.save(any()) } answers { firstArg() }
        every { usedCouponsRepository.save(any()) } answers { firstArg() }

        couponService.useCoupon(request, IP)

        verify(exactly = 1) { couponsRepository.save(any()) }
        verify(exactly = 1) { usedCouponsRepository.save(any()) }
    }

    @Test
    fun `should throw when coupon already used`() {
        val request = USE_REQUEST
        every { usedCouponsRepository.existsById(UsedCouponId(request.userId, request.code.uppercase())) } returns true

        assertThrows<CouponUsedByUserException> { couponService.useCoupon(request, IP) }
    }

    @Test
    fun `should throw when coupon not found`() {
        every { usedCouponsRepository.existsById(UsedCouponId(any(), any())) } returns false
        every { couponsRepository.findByCodeForUpdate(any()) } returns null

        assertThrows<CouponNotFoundException> { couponService.useCoupon(USE_REQUEST, IP) }
    }

    @Test
    fun `should throw when country does not match`() {
        val coupon = COUPON_ENTITY
        every { usedCouponsRepository.existsById(UsedCouponId(any(), any())) } returns false
        every { couponsRepository.findByCodeForUpdate(any()) } returns coupon
        every { ipClient.resolveCountry(any()) } returns "DE"

        assertThrows<CouponWrongCountryCodeOriginException> { couponService.useCoupon(USE_REQUEST, IP) }
    }

    @Test
    fun `should throw when usage limit exceeded`() {
        val request = USE_REQUEST
        val coupon = COUPON_ENTITY.copy(usageCount = 5)
        every { usedCouponsRepository.existsById(UsedCouponId(any(), any())) } returns false
        every { couponsRepository.findByCodeForUpdate(any()) } returns coupon

        assertThrows<CouponUsedOutException> { couponService.useCoupon(request, IP) }
    }
}
