package com.microservices.kotlin.chapter3

import com.microservices.kotlin.chapter3.Customer.Telephone
//클래스 시작 부분에 static import문을 이용하면 Customer 내부 클래스를 호출하더라도 Customer.Telephone 대신 Telephone만 써도 된다.
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
@Component
class CustomerServiceImpl : CustomerService {
    //메소드를 구현하기 전에 고객을 해당 클래스에 포함시켜야 한다.
    companion object{
        val initialCustomers = arrayOf(
                Customer(1, "Kotlin"),
                Customer(2, "Spring", Telephone("+45", "26935693")),
                Customer(3, "MicroService", Telephone("+23", "794080357")),
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