package com.empik.couponManager.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
open class HttpConfig {

    @Bean
    open fun restClient(): RestClient {
        return RestClient.create()
    }
}