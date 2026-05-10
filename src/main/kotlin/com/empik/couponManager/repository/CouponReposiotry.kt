package com.empik.couponManager.repository

import com.empik.couponManager.domain.CouponEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CouponRepository : JpaRepository<CouponEntity, Long> {

    fun existsByCode(code: String): Boolean

}