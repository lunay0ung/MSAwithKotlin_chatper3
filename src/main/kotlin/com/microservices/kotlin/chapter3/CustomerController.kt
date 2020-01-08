package com.microservices.kotlin.chapter3

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import sun.jvm.hotspot.tools.jcore.NameFilter
import java.util.concurrent.ConcurrentHashMap

/*
컨트롤러는 클라이언트의 요청을 처리하고 응답을 보내는 역할을 하는 특수 컴포넌트다.
컨트롤러는 다양한 통신 프로토콜에 사용할 수 있지만, RESTful API를 다룰 것이므로 RestController을 사용한다.
RestController는 컨트롤러가 요청을 처리하고 body를 출력하도록 지정한다. 이것은 response를 만드는 데 도움이 된다.
애플리케이션 시작 시 컴포넌트 스캔이 컨트롤러를 찾아서 스프링 컨텍스트에 bean으로 추가한다.
RestController이므로 JSON객체로 응답한다.

JSON(JavaScript Object Notation)은 객체를 전송하기 위한 사람이 읽을 수 있는 형태의 데이터 형식이다.
이것은 자바스크립트에서 파생됐지만 언어독립적인 데이터 형식으로,
대부분의 프로그래밍 언어에는 JSON형식의 데이터를 생성하고 파싱하는 코드가 포함돼있다.
* */
@RestController
class CustomerController {
   /* @RequestMapping(value = ["/customer"], method = arrayOf(RequestMethod.GET))
    fun getCustomer() = Customer(1, "강하늘")*/
    /*
    * 서버가 실행돼 모든 것이 준비되고 매핑된 URL의 각 요청이 실행되면
    * 메소드가 실행돼 Customer객체의 인스턴스가 생성되고,
    * 이 객체가 JSON으로 변환돼 요청에 대한 응답으로 반환된다.
    * */

    @Autowired //해당 애너테이션을 사용해 빈을 얻고, 이를 이용해 요청에 응답한다.
    lateinit var customers : ConcurrentHashMap<Int, Customer>


    /*@RequestMapping(value = ["/customer/"], method = arrayOf(RequestMethod.GET))
    fun getCustomer() = customers[2]*/
    //arrayOf~로 구성된 매개변수는 실제로 배열이며, 필요하면 둘 이상의 메소드를 허용하게 변경할 수 있다.
    //eg. arrayOf(RequestMethod.GET, RequestMothod.POST)
    //위와 같이 변경할 경우 HTTP GET, POST를 둘다 허용한 것이나, 컨트롤러의 기능별로 하나의 메소드를 유지하는 것이 좋다.

    @RequestMapping(value = ["/customer/"], method = arrayOf(RequestMethod.POST))
    fun createCustomer(@RequestBody customer: Customer) {//@RequestBody 애너테이션을 통해 객체를 보내도록 지정 -> RestController내에 있기 떄문에 예상되는 객체는 JSON형식이어야 한다.
        customers[customer.id] = customer
    }

    //요청 매개 변수를 통해 요청된 리소스를 필터링해서는 안 된다. RESTful API에서는 항상 경로변수를 통해 수행돼야 한다.
    @RequestMapping(value = ["/customer/{id}"], method = arrayOf(RequestMethod.GET))
    fun getCustomer(@PathVariable id : Int) = customers[id]
    //스프링이 컨트롤러를 자동 설정할 때 이 애너테이션을 인식해서 URL로 전달되는 값을 메소드에 필요한 값으로 매핑하고 지정된 데이터 타입으로 변환한다.

    //이 메소드에서는 customers맵을 리스트로 변환하여 출력한다.
    @RequestMapping(value = ["/customers"], method = arrayOf(RequestMethod.GET))
    fun getCustomers() = customers.map(Map.Entry<Int, Customer> :: value).toList()

    //고객 목록의 일부를 필터링해야 할 경우 새 메소드를 다음과 같이 수정한다.
    @RequestMapping(value = ["/customers2"], method = arrayOf(RequestMethod.GET))
    //requestparam애너테이션을 통해 매개변수가 필수인지 여부와 기본값 설정이 가능하다. 기본값은 항상 문자열이야 하며, 스프링이 이를 다시 다른 타입으로 자동 변환한다.
    fun getCustomer(@RequestParam(required = false, defaultValue = "")
    nameFilter: String) = customers.filter {
        it.value.name.contains(nameFilter, true)//contains의 두번째 매개변수로 대문자/소문자를 무시하도록 설정할 수 있다
    }.map(Map.Entry<Int, Customer>::value).toList()

    //id를 사용하면 리소스를 제거할 수 있으므로 body 매개변수로 고객 정보를 가질 필요는 없다
    @RequestMapping(value = ["/customer/{id}"], method = arrayOf(RequestMethod.DELETE))
    fun deleteUser(@PathVariable id:Int) = customers.remove(id)

    //PUT -> 지정된 리소스를 업데이트하도록 요청한다
    //고객정보를 json body로 보내기 때문에 업데이트할 내용을 정확히 반영할 수 있다
    @RequestMapping(value = ["/customer/{id}"], method = arrayOf(RequestMethod.PUT))
    fun updateCustomer(@PathVariable id:Int, @RequestBody customer: Customer){
        customers.remove(id)
        customers[customer.id] = customer
    }

    //@RequestMapping 애너테이션을 사용해 메소드에 매개변수를 전달하는 것도 좋지만, 목적에 비해 코드량이 비교적 많다.
    //스프링은 이 선언문을 줄일 수 있는 헬퍼를 제공한다.
    @GetMapping(value = ["/customer/{id}"]) //비슷한 방식으로 @PostMapping, @PutMapping, @DeleteMapping 애너테이션을 사용할 수 있다.
    fun getCustomer2(@PathVariable id:Int) = customers[id]

}
