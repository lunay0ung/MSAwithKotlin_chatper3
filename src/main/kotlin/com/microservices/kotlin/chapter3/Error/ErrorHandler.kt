package com.microservices.kotlin.chapter3.Error

import com.fasterxml.jackson.core.JsonParseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.Exception
import javax.servlet.http.HttpServletRequest

/*
* 스프링은 ControllerAdvice와 ExceptionHandler 애너테이션을 사용해
* 애플리케이션 코드에서 캐치하지 않은 예외를 처리하는 매커니즘을 제공한다.
* */
@ControllerAdvice //컨텍스트 스캔을 통해 스프링 컨텍스트에 추가된다.
class ErrorHandler {
    @ExceptionHandler(JsonParseException::class) //@ExceptionHandler 애너테이션을 추가한 메소드를 선언하고, exception 클래스를 참조해 처리할 예외 유형을 나타낸다
    fun JsonParserExceptionHandler(servletRquest: HttpServletRequest, exception: Exception) :
    //JsonParserException이 발생할 때 이를 처리하는 catch절이 없으며,
    //스프링이 해당 클래스에 사용 가능한 예외 핸들러를 검색해 오류를 전송한다.

    ResponseEntity<String> {
        return ResponseEntity("JsonError", HttpStatus.BAD_REQUEST) //-> 잘못된 json 요청 전송 시 JSON Error 메시지와 함께 400 bad request가 리턴된다.
    }
}