package com.microservices.kotlin.chapter3.Error

//오류 응답을 처리할 수 있는 ErrorResponse 데이터 클래스를 통해 에러메시지를 json형식으로 리턴한다.
data class ErrorResponse(val error : String, val message : String) {
}