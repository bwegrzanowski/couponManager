package com.empik.couponManager.controller

import com.empik.couponManager.model.CouponResponse
import com.empik.couponManager.model.CreateCouponRequest
import com.empik.couponManager.model.toResponse
import com.empik.couponManager.service.CouponService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/coupons")
class CouponController(
    @Autowired private val service: CouponService
) {

    @PostMapping
    fun create(@RequestBody request: CreateCouponRequest): ResponseEntity<CouponResponse> {
        service.createCoupon(request).toResponse()
        return ResponseEntity.ok(service.createCoupon(request).toResponse())
    }

}