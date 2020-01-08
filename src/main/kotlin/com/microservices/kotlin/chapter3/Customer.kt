package com.microservices.kotlin.chapter3

//데이터 클래스는 코틀린의 편리한 클래스로, 다양한 속성을 가진 클래스를 정의할 수 있다.
//기존 POJO클래스의 매우 유용한 대체 클래스 -> 보일러플레이트 코드를 크게 줄일 수 있다.
//코틀린은 클래스에, equals(), hashCode(), toString(), copy()와 할당연산자를 자동으로 제공한다.
data class Customer(var id : Int, var name: String = "") {


}