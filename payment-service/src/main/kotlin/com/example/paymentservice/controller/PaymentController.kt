package com.example.paymentservice.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/payment")
class PaymentController() {

    // Request from order-service
    @GetMapping
    fun sayHello(): ResponseEntity<String> {
        return ResponseEntity.ok("Payment is OK and updated")
    }
}