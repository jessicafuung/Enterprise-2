package com.example.orderservice.controller

import com.example.orderservice.exception.OrderNotFoundException
import com.example.orderservice.exception.PageNotDefinedException
import com.example.orderservice.exception.WantsYourOrderIdException
import com.example.orderservice.exception.WantsYourOrderInfoException
import com.example.orderservice.integration.PaymentIntegrationService
import com.example.orderservice.integration.RabbitSender
import com.example.orderservice.model.Order
import com.example.orderservice.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController
@RequestMapping("/api/order")
class OrderController(
    @Autowired private val orderService: OrderService,
    @Autowired private val paymentIntegrationService: PaymentIntegrationService,
    @Autowired private val rabbitSender: RabbitSender
) {

    // Retrieve all orders with pagination
    @GetMapping("/all")
    fun getAllOrdersWithPaging(@PathParam("page") page: Int?): ResponseEntity<List<Order>> {
        page?.let { return ResponseEntity.ok(orderService.getOrders(page).toList()) }
        throw PageNotDefinedException("Pass the page number!")
    }

    // Retrieve a single order, if not found - throw an exception
    @GetMapping("/{orderId}")
    fun getOrderById(@PathVariable orderId: Long?): ResponseEntity<String> {
        val order = orderService.getOrderById(orderId)
        order?.let { return ResponseEntity.ok("This is your order: $order") }
        throw OrderNotFoundException("Order not found, try again! ")
    }

    // Create new order with order info -> synchronous HTTP req to payment service -> update payment status
    @PostMapping
    fun createOrder(@RequestBody orderInfo: OrderInfo?): ResponseEntity<Order> {
        orderInfo?.let {
            val createdOrder = orderService.createOrder(orderInfo)
            println("Order created! Payment status: ${createdOrder.payment}")
            println("Response from payment-service: " + paymentIntegrationService.sendHttpCallToPaymentService())
            return ResponseEntity.ok(orderService.updatePayment(createdOrder))
        }

        throw WantsYourOrderInfoException("Hellooo, where is your order info? :/")
    }

    // Delete order by order ID and clear the cache in the service-layer
    @DeleteMapping("/{orderId}")
    fun deleteOrder(@PathVariable orderId: Long?): ResponseEntity<String> {
        val order = orderService.getOrderById(orderId)
        order?.let {
            orderService.deleteOrder(orderId)
            return ResponseEntity.ok("Order deleted!")
        }

        throw WantsYourOrderIdException("Hellooo, can not find the order ID?")
    }

    // Create an order message to shipping service
    @PostMapping("/{orderId}")
    fun createOrderMessageToShipping(@PathVariable orderId: Int) {
        val order = orderService.getOrderById(orderId.toLong())
        val orderInfo = order.toString()
        println("Order dispatch status: "+order?.dispatched)
        rabbitSender.sendOrderInfoToOrderQueue(orderInfo)
    }
}

data class OrderInfo(
    val description: String,
    val customerName: String,
    val customerAddress: String,
    val payment: Boolean,
    val dispatched: Boolean
)