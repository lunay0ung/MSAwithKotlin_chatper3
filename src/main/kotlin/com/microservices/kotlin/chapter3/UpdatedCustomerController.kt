package com.microservices.kotlin.chapter3

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sun.jvm.hotspot.tools.jcore.NameFilter
import kotlin.concurrent.fixedRateTimer

@RestController
class UpdatedCustomerController {

    //컨트롤러를 변경해서 고객 서비스 구현과 완전히 분리했기 때문에, 보다 유연해졌다.
    @Autowired //컴포넌트 스캔이 빈 구현(CustomerServiceImpl)을 해당 인터페이스에 주입한다.
    private lateinit var customerService: CustomerService

    /*
    * http 동사 처리처럼 api 서비스의 소비자에게 응답할 때도 정확하게 처리해야 한다.
    * 스프링은 response entity라는 제너릭 클래스를 제공하는데,
    * 이를 통해 컨트롤러에서 api 응답 시 상태코드를 지정할 수 있다.
    * */

    @GetMapping(value = ["/customer/{id}"])
    /*fun getCustomer(@PathVariable id:Int) = customerService.getCustomer(id), HttpStatus.OK
         */
    //리소스에 대한 get요청을 받으면 리소스와 200OK 또는 리소스를 찾을 수 없으면 404 not found를 응답해야 한다.
    fun getCustomer(@PathVariable id: Int) : ResponseEntity<Customer?> {
       val customer = customerService.getCustomer(id)
       val status = if (customer == null) HttpStatus.NOT_FOUND else HttpStatus.OK //고객이 없으면 null 반환 -> 상태는 404 not found
        return ResponseEntity(customer, status)
   }


    @PostMapping(value = ["/customer/"])
    /*fun createCustomer(@RequestParam customer: Customer){
        customerService.createCustomer(customer)
    }*/
    //post 요청에 대한 응답을 정의할 땐 200ok 대신 201 created를 반환
    //객체를 반환하지 않기 때문에 응답을 위한 본문을 지정할 필요가 없다
    fun createCustomer(@RequestBody customer: Customer) : ResponseEntity<Unit> {
        customerService.createCustomer(customer)
        //아무 것도 출력하지 않으므로 void타입과 동격인 코틀린의 unit을 사용한다
        return ResponseEntity(Unit, HttpStatus.CREATED)
    }
    /*
  * api로 요청을 보낼 때 json 객체를 보낼 수 있으며, @RequestMapper에 변수가 body를 갖도록 설정하면
  * 이를 객체로 변환할 수 있다. 이를 역직렬화라고 한다.
  * 스프링은 jackson을 사용해 ---json 객체를 jvm 클래스로 역직렬화---하며,
  * 직렬화와 마찬가지로 object mapper클래스를 사용한다.
  *
  * 그런다음 request를 보내면, 스프링은 request body를 가져온 후
  * objectMapper를 사용해 지정된 클래스의 jvm 객체를 생성한다. 
  * */



    //위 메소드에서는 리턴할 body가 필요없으므로 unit과 상태를 반환한다. 하지만 존재하지 않는 id로 delete, update 메소드를 호출하면 empty json object가 반환된다
    //그러나 unit 메소드에서도 반환하는 내용이 없도록 코드를 변경할 수 있다. 필요한 경우 다음과 같이 수정한다.
    fun createCustomer2(@RequestBody customer: Customer) : ResponseEntity<Unit?> {
        customerService.createCustomer(customer)
        return ResponseEntity<Unit?>(null, HttpStatus.CREATED)
        //api 사용자 입장에서는 빈 객체보다는 단순히 내용이 없는 것이 덜 혼란스러울 수 있기 떄문에 빈 응답을 선호한다.
        //*rest api에서는 항상 값을 반환하는 것이 좋다. 가령 모든 것이 정상이라는 {"result" : "ok"}를 반환할 수 있다.
        //이를 통해 소비자가 응답을 오해하는 것을 방지할 수 있다. 소비자는 응답 본문에 관계없이 항상 http status를 신뢰해야 한다.
    }

    //찾을 수 없는 리소스를 삭제하려면 404 not found 응답, 삭제 성공 시 200ok
    @DeleteMapping(value = ["/customer/{id}"])
   /* fun deleteCustomer(@PathVariable id: Int){
        customerService.deleteCustomer(id)
    }*/
    fun deleteCustomer(@PathVariable id: Int) : ResponseEntity<Unit> {
        var status = HttpStatus.NOT_FOUND //먼저 고객을 찾지 못했다고 설정하고
        if(customerService.getCustomer(id) != null) { //실제 고객이 있는지 서비스를 확인한다
            customerService.deleteCustomer(id) //고객이 존재하면 고객을 삭제한 후
            status = HttpStatus.OK //상태를 200 ok로 설정
        }
        return ResponseEntity(Unit, status)
    }

    /*
    * 이 로직이 customer service에 있어야 하는지, 컨트롤러에 있어야 하는지에 대해서는 논란의 여지가 있지만,
    * 고객의 존재 여부에 따라 다르게 응답해야 하는 요건이 컨트롤러에 있으므로 컨트롤러에 둔다
    * */


    @PutMapping(value = ["/customer/{id}"])
    /*fun updateCustomer(@PathVariable id: Int, @RequestBody customer: Customer){
        customerService.updateCustomer(id, customer)
    }*/
    fun updateCustomer(@PathVariable id: Int, @RequestBody customer: Customer) :
            ResponseEntity<Unit> {
        var status = HttpStatus.NOT_FOUND
        if(customerService.getCustomer(id) != null) {
            customerService.updateCustomer(id, customer)
            status = HttpStatus.ACCEPTED //리소스를 수정한 경우 202 accepted를 반환한다
        }
        return ResponseEntity(Unit, status)
    }

    @GetMapping(value = ["/customers"])
    fun getCustomers(@RequestParam(required = false, defaultValue = "") nameFilter: String)
    = customerService.searchCustomer(nameFilter)
}