package com.example.orderservice.integration

import com.example.orderservice.service.OrderService
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@RabbitListener(queues = ["order-queue"])
class RabbitReceiver(@Autowired private val orderService: OrderService) {

    // order <- shipping
    @RabbitHandler
    fun receive(message: String) {
        println("Received: '$message'")
        orderService.updateDispatch(message)
    }
}