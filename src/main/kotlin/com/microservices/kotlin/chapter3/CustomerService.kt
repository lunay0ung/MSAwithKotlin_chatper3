package com.microservices.kotlin.chapter3

import org.springframework.stereotype.Component
import sun.jvm.hotspot.tools.jcore.NameFilter

/*
* 현재 고객 빈이 컨트롤러의 일부가 됨으로써 결과적으로 결합을 만들게 되어 있는 상태이며,
* 맵으로 구성된 고객 목록은 추후 데이터베이스에 저장하거나 다른 방식으로 처리될 때 컨트롤러에 영향을 줄 수 있다.
* 이 문제를 처리하기 위해 서비스 패턴을 사용한다.
* 이를 통해 api 처리와 컨트롤러 간 커플링문제를 해결 할 수 있다.
* */

interface CustomerService { //이런 방식은 고객 정보가 어떻게 저장/변경 혹은 검색되는지 노출하지 않는다.
    fun getCustomer(id: Int) : Customer? //null-safety, 고객을 찾지 못할 때 널 값을 반환할 수 있다.
    fun createCustomer(customer:Customer)
    fun deleteCustomer(id: Int)
    fun updateCustomer(id: Int, customer: Customer)
    fun searchCustomer(nameFilter: String):List<Customer>
}