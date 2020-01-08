package com.microservices.kotlin.chapter3

import java.util.concurrent.ConcurrentHashMap

class CustomerServiceImpl : CustomerService {
    //메소드를 구현하기 전에 고객을 해당 클래스에 포함시켜야 한다.
    companion object{
        val initialCustomers = arrayOf(
                Customer(1, "Kotlin"),
                Customer(2, "Spring"),
                Customer(3, "MicroService"),
                Customer(4, "Learn")
        )
        val customers = ConcurrentHashMap<Int, Customer>(
            initialCustomers.associateBy(Customer::id)
        )
    }

    override fun getCustomer(id: Int) = customers[id]

    override fun createCustomer(customer: Customer) {
        customers[customer.id] = customer
    }

    override fun deleteCustomer(id: Int) {
        customers.remove(id)
    }

    override fun updateCustomer(id: Int, customer: Customer) {
        deleteCustomer(id)
        createCustomer(customer)
    }

    override fun searchCustomer(nameFilter: String): List<Customer> =
        customers.filter {
            it.value.name.contains(nameFilter, true)
        }.map(Map.Entry<Int, Customer>::value).toList()

}