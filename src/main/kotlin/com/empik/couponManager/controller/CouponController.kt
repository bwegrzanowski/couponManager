package com.empik.couponManager.controller

import com.empik.couponManager.exception.UnknownIpException
import com.empik.couponManager.model.CouponResponse
import com.empik.couponManager.model.CreateCouponRequest
import com.empik.couponManager.model.UseCouponRequest
import com.empik.couponManager.model.toResponse
import com.empik.couponManager.service.CouponService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/coupons")
open class CouponController(
    @Autowired private val couponService: CouponService,
) {

    @PostMapping
    open fun create(@RequestBody request: CreateCouponRequest): ResponseEntity<CouponResponse> {
        return ResponseEntity.ok(couponService.createCoupon(request).toResponse())
    }

    @PostMapping("/use")
    open fun use(@RequestBody request: UseCouponRequest, @RequestHeader("X-Forwarded-For", required = false) ip: String?): ResponseEntity<Unit> {
        ip ?: throw UnknownIpException()
        couponService.useCoupon(request, ip)
        return ResponseEntity.ok().build()
    }
}
