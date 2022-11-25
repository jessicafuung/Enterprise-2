package com.example.shippingservice.controller

import com.example.shippingservice.integration.RabbitSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/shipping")
class ShippingController(@Autowired private val rabbitSender: RabbitSender) {

    // Shipping service sends a message back to order service when the order is ready!
    @PostMapping("/{orderId}")
    fun messageToDispatchQueue(@PathVariable orderId: Int) {
        rabbitSender.sendMessageToOrderQueue("Order #$orderId is now on it's way!")
    }
}