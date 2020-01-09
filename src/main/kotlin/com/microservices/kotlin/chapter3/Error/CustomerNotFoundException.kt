package com.microservices.kotlin.chapter3.Error

import java.lang.Exception

//일반적인 예외처리 방법 외, 비즈니스 로직 상 에러를 핸들링해야 하는 경우 - 가령 존재하지 않는 고객을 요청하는 경우를 위한 클래스를 생성하고
//Exception을 상송한다.
class CustomerNotFoundException (message: String) : Exception(message)