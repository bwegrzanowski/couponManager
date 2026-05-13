package com.empik.couponManager.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class IpClientResponse(
    val success: Boolean,
    @JsonProperty(value = "country_code")
    val countryCode: String
)
