package com.example.orderservice.model

import javax.persistence.*

@Entity
@Table(name = "customers")
class Customer(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_customer_id_seq")
    @SequenceGenerator(
        name = "customers_customer_id_seq",
        allocationSize = 1
    )
    @Column(name = "customer_id")
    val id: Long? = null,

    @Column(name = "customer_name")
    val name: String?,

    @Column(name = "customer_address")
    val address: String?,
) {

    override fun toString(): String {
        return "Customer(id=$id, name=$name, address=$address)"
    }
}