package com.example.orderservice.service

import com.example.orderservice.controller.OrderInfo
import com.example.orderservice.model.Customer
import com.example.orderservice.model.Order
import com.example.orderservice.repository.CustomerRepository
import com.example.orderservice.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
@CacheConfig(cacheNames = ["orders"])
class OrderService(
    @Autowired private val orderRepository: OrderRepository,
    @Autowired private val customerRepository: CustomerRepository
    ) {

    // Retrieve all orders with pagination
    fun getOrders(page: Int): Page<Order> {
        val pageSize = 5
        return orderRepository.findAll(Pageable.ofSize(pageSize).withPage(page))
    }

    // Retrieve one single order and caches it
    @Cacheable(key = "#id")
    fun getOrderById(id: Long?): Order? {
        return orderRepository.findByIdOrNull(id)
    }

    // Create a new order
    fun createOrder(orderInfo: OrderInfo): Order {
        val customer = registerCustomer(orderInfo)
        val newOrder = Order(
            description = orderInfo.description,
            payment = false,
            dispatched = false,
            customerId = customer.id?.let {
            customerRepository.getReferenceById(it)
        })

        return orderRepository.save(newOrder)
    }

    // Register customer
    fun registerCustomer(orderInfo: OrderInfo): Customer {
        val customer = Customer(name = orderInfo.customerName, address = orderInfo.customerAddress)
        return customerRepository.save(customer)
    }

    // Update the order: payment = true - after request to payment service
    fun updatePayment(order: Order): Order? {
        if(order.id?.let { orderRepository.existsById(it) } == true) {
            val updatedOrder = Order(id = order.id,description = order.description, payment = true, dispatched = false)
            return orderRepository.save(updatedOrder)
        }

        return null
    }

    // Update the order: dispatch = true - after message from shipping service
    fun updateDispatch(message: String) {
        val extract = message.filter { it.isDigit() }
        val orderId = extract.toLong()
        val order = getOrderById(orderId)
        val updatedOrder = Order(id = orderId, dispatched = true, description = order?.description, payment = true)

        orderRepository.save(updatedOrder)
        println("Order service: 'order dispatch status: ${updatedOrder?.dispatched}'")
    }

    // Delete order and clear the cache
    @CacheEvict(allEntries = true)
    fun deleteOrder(id: Long?) {
        id?.let { orderRepository.deleteById(id)  }
    }
}