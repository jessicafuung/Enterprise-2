package com.example.shippingservice.integration

import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
@RabbitListener(queues = ["shipping-request"])
class RabbitReceiver {

    // order -> shipping
    @RabbitHandler
    fun receive(orderInfo: String) {
        println("Received order information: '$orderInfo'")
    }
}