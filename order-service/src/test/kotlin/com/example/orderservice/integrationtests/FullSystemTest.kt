package com.example.orderservice.integrationtests

import org.hamcrest.Matchers.containsString
import org.json.JSONObject
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class FullSystemTest (@Autowired private val mockMvc: MockMvc){

    val baseUrl = "http://localhost:8086/api/order"

    @Test
    @Order(1)
    fun shouldCreateOrder() {
        val orderPayload = JSONObject()
            .put("description","Light saber")
            .put("customerName","Donald Duck")
            .put("customerAddress","Snacks Street 123")

        mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = orderPayload
        }
            .andExpect { status { isOk() } }
            .andReturn()
    }

    @Test
    @Order(2)
    fun shouldGetOrder() {
        mockMvc.get("$baseUrl/1") {
            contentType = MediaType.APPLICATION_JSON
        }
            .andExpect { status { isOk() } }
            .andExpect { containsString("Board game") }
    }
}