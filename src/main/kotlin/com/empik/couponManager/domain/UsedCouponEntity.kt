package com.empik.couponManager.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.io.Serializable
import java.util.UUID

@Entity
@Table(name = "usedcoupons")
data class UsedCouponEntity(
    @EmbeddedId
    val id: UsedCouponId,
)

@Embeddable
data class UsedCouponId(
    @Column(name = "user_id", nullable = false)
    val userId: UUID,
    @Column(name = "code", nullable = false)
    val code: String,
) : Serializable
