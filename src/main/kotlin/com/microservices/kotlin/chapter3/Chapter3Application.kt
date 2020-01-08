package com.microservices.kotlin.chapter3

import com.microservices.kotlin.chapter3.Chapter3Application.Companion.initialCustomers
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.concurrent.ConcurrentHashMap

@SpringBootApplication
class Chapter3Application {
	companion object {
		val initialCustomers = arrayOf(
				Customer(1, "Gray"),
				Customer(2, "Blue"),
				Customer(3, "Red")
		)
	}

	@Bean
	fun customers() = ConcurrentHashMap<Int, Customer>(initialCustomers.associateBy(Customer :: id))
	//이 코드는 해시맵을 생성하고 이를 스프링 컨텍스트에 빈으로 추가한다.
	//서로 다른 요청이 맵의 동일한 요소에 액세스할 때와 같이 동기화 문제가 발생할 수가 있으므로 ConcurrentHashMap을 사용한다.
	//빈이 준비됐으니 그것을 컨트롤러에 주입autowire할 수 있다.

}


fun main(args: Array<String>) {
	runApplication<Chapter3Application>(*args)
}
