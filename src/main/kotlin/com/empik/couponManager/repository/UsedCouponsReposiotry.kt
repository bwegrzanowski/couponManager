package com.empik.couponManager.repository

import com.empik.couponManager.domain.UsedCouponEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UsedCouponsRepository : JpaRepository<UsedCouponEntity, Long> {

    fun existsByUserIdAndCode(userId: UUID, code: String): Boolean
}
