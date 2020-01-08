package com.microservices.kotlin.chapter3

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import sun.jvm.hotspot.tools.jcore.NameFilter

@RestController
class UpdatedCustomerController {

    //컨트롤러를 변경해서 고객 서비스 구현과 완전히 분리했기 때문에, 보다 유연해졌다.
    @Autowired //컴포넌트 스캔이 빈 구현(CustomerServiceImpl)을 해당 인터페이스에 주입한다.
    private lateinit var customerService: CustomerService

    @GetMapping(value = ["/customer/{id}"])
    fun getCustomer(@PathVariable id:Int) = customerService.getCustomer(id)

    @PostMapping(value = ["/customer/"])
    fun createCustomer(@RequestParam customer: Customer){
        customerService.createCustomer(customer)
    }

    @DeleteMapping(value = ["/customer/{id}"])
    fun deleteCustomer(@PathVariable id: Int){
        customerService.deleteCustomer(id)
    }

    @PutMapping(value = ["/customer/{id}"])
    fun updateCustomer(@PathVariable id: Int, @RequestBody customer: Customer){
        customerService.updateCustomer(id, customer)
    }

    @GetMapping(value = ["/customers"])
    fun getCustomers(@RequestParam(required = false, defaultValue = "") nameFilter: String)
    = customerService.searchCustomer(nameFilter)
}