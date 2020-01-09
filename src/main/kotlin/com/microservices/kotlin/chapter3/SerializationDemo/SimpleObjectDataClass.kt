package com.microservices.kotlin.chapter3.SerializationDemo

/*
코틀린이 jvm클래스로 컴파일하는 데이터 클래스는 object mapper가 사용하는 것보다
더 나은 메소드를 생성하므로 데이터 클래스를 사용해 직렬화할 것이다. (????)
* */
data class SimpleObjectDataClass(var name : String = "hello", var place : String = "world") {
}