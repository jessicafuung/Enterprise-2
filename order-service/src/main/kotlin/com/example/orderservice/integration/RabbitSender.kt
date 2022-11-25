package com.example.orderservice.integration

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RabbitSender(@Autowired private val rabbitTemplate: RabbitTemplate) {

    // order -> shipping
    fun sendOrderInfoToOrderQueue(orderInfo: String) {
        rabbitTemplate.convertAndSend("shipping-request", orderInfo)
    }
}