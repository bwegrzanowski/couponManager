package com.empik.couponManager.client

import com.empik.couponManager.model.IpClientResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

private const val IP_CLIENT_URL = "https://ipwho.is/"

@Service
class IPClient(@Autowired private val restClient: RestClient) {

    fun resolveCountry(ip: String): String {
        val response = restClient.get()
            .uri("$IP_CLIENT_URL$ip")
            .retrieve()
            .body(IpClientResponse::class.java)
            ?: throw IllegalStateException("Cannot resolve IP")

        if (!response.success) throw IllegalStateException("Cannot resolve country")

        return response.countryCode
    }
}