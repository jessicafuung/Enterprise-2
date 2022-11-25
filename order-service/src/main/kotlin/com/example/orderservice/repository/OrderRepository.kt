package com.example.orderservice.repository

import com.example.orderservice.model.Order
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<Order, Long>, PagingAndSortingRepository<Order, Long> {

    override fun findAll(pageable: Pageable): Page<Order>

    override fun findAll(): List<Order>

}