package com.example.shippingservice.integration

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RabbitSender(@Autowired private val rabbitTemplate: RabbitTemplate) {

    // shipping -> order
    fun sendMessageToOrderQueue(orderInfo: String) {
        rabbitTemplate.convertAndSend("order-queue", orderInfo)
    }
}