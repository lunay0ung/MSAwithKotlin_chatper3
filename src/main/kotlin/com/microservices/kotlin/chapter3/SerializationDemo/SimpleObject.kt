package com.microservices.kotlin.chapter3

/*
* 자바 객체를 출력할 때 스프링은 내부적으로 Jackson이 제공하는 ObjectMapper를 사용해 JSON expression을 생성한다.
* 이 object mapper는 java reflection api를 사용해 제공된 객체의 public 속성을 검색하고 표현을 만든다.
* */
class SimpleObject {
    public val name = "hello"
    private val place = "world"
}