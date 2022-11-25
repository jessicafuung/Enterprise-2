package com.example.orderservice.integrationtests

import com.example.orderservice.controller.OrderInfo
import com.example.orderservice.service.OrderService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(OrderService::class)
class DatabaseIntegrationTests(@Autowired private val orderService: OrderService) {

    @Test
    fun shouldGetOrdersFromFirstPage() {
        val result = orderService.getOrders(0)
        assert(result.size == 5)
    }

    @Test
    fun registerAndFindOrder() {
        val newOrder = OrderInfo(
            description = "Noodle",
            payment = false,
            dispatched = false,
            customerName = "jess",
            customerAddress = "candy shop 123"
        )
        val createdOrder = orderService.createOrder(newOrder)
        assert(createdOrder.description == "Noodle")

        val foundOrder = orderService.getOrderById(createdOrder.id)
        assert(foundOrder?.description == createdOrder.description)
    }
}