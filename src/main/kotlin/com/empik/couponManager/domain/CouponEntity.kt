package com.empik.couponManager.domain

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
class CouponEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Version
    val version: Int? = null,
    @Column(nullable = false, unique = true)
    val code: String,
    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),
    @Column(nullable = false)
    val maxUsages: Int,
    @Column(nullable = false)
    val countryCode: String,
)
