package com.empik.couponManager.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.util.*

@Entity
@Table(
    name = "usedcoupons",
    indexes = [Index(name = "uq_userId_code", columnList = "user_id,code")],
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "coupon_id"])],
)
data class UsedCouponEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val code: String,
    @Column(nullable = false)
    val userId: UUID,
)
