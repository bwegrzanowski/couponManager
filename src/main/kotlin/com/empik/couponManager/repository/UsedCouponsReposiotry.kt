package com.empik.couponManager.repository

import com.empik.couponManager.domain.UsedCouponEntity
import com.empik.couponManager.domain.UsedCouponId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsedCouponsRepository : JpaRepository<UsedCouponEntity, UsedCouponId> {

    override fun existsById(id: UsedCouponId): Boolean
}
