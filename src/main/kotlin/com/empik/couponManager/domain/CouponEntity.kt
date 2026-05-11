package com.empik.couponManager.domain

import com.empik.couponManager.model.Code
import com.empik.couponManager.model.CountryCode
import com.empik.couponManager.model.Coupon
import com.empik.couponManager.model.CouponId
import com.empik.couponManager.model.CreatedAt
import com.empik.couponManager.model.MaxUsages
import com.empik.couponManager.model.UsageCount
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.time.Instant

@Entity
@Table(
    name = "coupons",
    indexes = [Index(name = "idx_coupon_code", columnList = "code")]
)
data class CouponEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Version
    val version: Int = 0,
    @Column(nullable = false, unique = true)
    val code: String,
    @Column(nullable = false)
    val usageCount: Int,
    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),
    @Column(nullable = false)
    val maxUsages: Int,
    @Column(nullable = false)
    val countryCode: String,
)

fun CouponEntity.toCoupon(): Coupon =
    Coupon(
        id = id?.let { CouponId(it) } ?: throw IllegalStateException("Id is null!"),
        code = Code(code),
        usageCount = UsageCount(usageCount),
        createdAt = CreatedAt(createdAt),
        maxUsages = MaxUsages(maxUsages),
        countryCode = CountryCode(countryCode)
    )