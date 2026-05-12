package com.empik.couponManager.integration.controller

import com.empik.couponManager.client.IPClient
import com.empik.couponManager.integration.config.IntegrationTestConfig
import com.empik.couponManager.repository.CouponsRepository
import com.empik.couponManager.repository.UsedCouponsRepository
import com.empik.couponManager.util.CREATE_REQUEST
import com.empik.couponManager.util.IP
import com.empik.couponManager.util.IP_HEADER
import com.empik.couponManager.util.USE_REQUEST
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

private const val CREATE_COUPON_PATH = "/coupons"
private const val USE_COUPON_PATH = "/coupons/use"

@SpringBootTest
@AutoConfigureMockMvc
class CouponControllerIntegrationTest : IntegrationTestConfig() {

    private val objectMapper: ObjectMapper = jacksonObjectMapper()

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var ipClient: IPClient

    @Autowired
    lateinit var couponsRepository: CouponsRepository

    @Autowired
    lateinit var usedCouponsRepository: UsedCouponsRepository

    @AfterEach
    fun cleanup() {
        usedCouponsRepository.deleteAll()
        couponsRepository.deleteAll()
    }

    @Test
    fun `should create coupon`() {
        mockMvc.post(CREATE_COUPON_PATH) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(CREATE_REQUEST)
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.code") { value("SUMMER") }
            }
    }

    @Test
    fun `should return conflict when coupon already exists`() {
        mockMvc.post(CREATE_COUPON_PATH) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(CREATE_REQUEST)
        }

        mockMvc.post(CREATE_COUPON_PATH) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(CREATE_REQUEST)
        }
            .andExpect {
                status { isConflict() }
                content { string(containsString("already exists")) }
            }
    }

    @Test
    fun `should use coupon`() {
        every { ipClient.resolveCountry(any()) } returns "PL"
        mockMvc.post(CREATE_COUPON_PATH) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(CREATE_REQUEST)
        }

        mockMvc.post(USE_COUPON_PATH) {
            header(IP_HEADER, IP)
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(USE_REQUEST)
        }
            .andExpect { status { isOk() } }
    }

    @Test
    fun `should return not found when coupon not found`() {
        val request = USE_REQUEST.copy(code = "INVALID")
        every { ipClient.resolveCountry(any()) } returns "PL"

        mockMvc.post(USE_COUPON_PATH) {
            header(IP_HEADER, IP)
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect { status { isNotFound() } }
    }

    @Test
    fun `should return forbidden for wrong country`() {
        every { ipClient.resolveCountry(any()) } returns "DE"
        mockMvc.post(CREATE_COUPON_PATH) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(CREATE_REQUEST)
        }

        mockMvc.post(USE_COUPON_PATH) {
            header(IP_HEADER, IP)
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(USE_REQUEST)
        }
            .andExpect { status { isForbidden() } }
    }

    @Test
    fun `should return bad request when ip header missing`() {
        mockMvc.post(USE_COUPON_PATH) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(USE_REQUEST)
        }
            .andExpect { status { isBadRequest() } }
    }
}
