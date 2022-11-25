package com.example.orderservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "orders")
class Order(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_order_id_seq")
    @SequenceGenerator(
        name = "orders_order_id_seq",
        allocationSize = 1
    )

    @Column(name = "order_id")
    val id: Long? = null,

    @Column(name = "order_date")
    val date: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "order_description")
    val description: String?,

    @Column(name = "order_payment")
    val payment: Boolean,

    @Column(name = "order_dispatched")
    val dispatched: Boolean,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    val customerId: Customer? = null
) {
    override fun toString(): String {
        return "Order(id=$id, date=$date, description=$description, payment=$payment, dispatched=$dispatched, customerId=$customerId)"
    }
}