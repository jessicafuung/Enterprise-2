package com.example.orderservice.repository

import com.example.orderservice.model.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository: JpaRepository<Customer, Long> {
}