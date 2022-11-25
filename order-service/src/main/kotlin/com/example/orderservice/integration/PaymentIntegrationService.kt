package com.example.orderservice.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

@Service
class PaymentIntegrationService(@Autowired private val restTemplate: RestTemplate) {

    val paymentUrl = "http://payment-service/api/payment"

    fun sendHttpCallToPaymentService(): String {
        val response: ResponseEntity<String> = restTemplate.getForEntity("$paymentUrl/", ResponseEntity::class)
        return response.body.toString()
    }
}