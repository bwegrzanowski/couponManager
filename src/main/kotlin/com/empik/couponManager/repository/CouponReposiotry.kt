package com.empik.couponManager.repository

import com.empik.couponManager.domain.CouponEntity
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CouponRepository : JpaRepository<CouponEntity, Long> {

    fun existsByCode(code: String): Boolean

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM CouponEntity c WHERE c.code = :code")
    fun findByCodeForUpdate(code: String): CouponEntity?
}