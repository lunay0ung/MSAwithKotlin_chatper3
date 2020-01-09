package com.microservices.kotlin.chapter3

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
//데이터 클래스는 코틀린의 편리한 클래스로, 다양한 속성을 가진 클래스를 정의할 수 있다.
//기존 POJO클래스의 매우 유용한 대체 클래스 -> 보일러플레이트 코드를 크게 줄일 수 있다.
//코틀린은 클래스에, equals(), hashCode(), toString(), copy()와 할당연산자를 자동으로 제공한다.
                                     /*   고객 전화 파라미터를 옵셔널로 변경하고 기본값으로 null을 설정한다.
                                        [{"id":1,"name":"Kotlin","telephone":null},{"id":2,"name":"Spring","telephone":{"countryCode":"+45","telephoneNumber":"26935693"}},{"id":3,"name":"MicroService","telephone":{"countryCode":"+23","telephoneNumber":"794080357"}},{"id":4,"name":"Learn","telephone":null}]
                                        null값을 갖는 직렬화 객체를 얻을 수 있지만, 이게 바라던 바는 아닐 수 있다.
                                        옵셔널 필드를 직렬화하지 않기 위해서는 데이터 클래스에 Jackson애너테이션을 이용하면 된다.
                                    */
@JsonInclude(Include.NON_NULL) //@JsonInclude 애너테이션은 클래스에서 널이 아닌 값만 직렬화할 것을 나타낸다.
//[{"id":1,"name":"Kotlin"},{"id":2,"name":"Spring","telephone":{"countryCode":"+45","telephoneNumber":"26935693"}},{"id":3,"name":"MicroService","telephone":{"countryCode":"+23","telephoneNumber":"794080357"}},{"id":4,"name":"Learn"}]
data class Customer(var id : Int, var name: String = "", var telephone : Telephone? = null)  {
    //내부 클래스 생성
    data class Telephone(var countryCode : String = "", var telephoneNumber: String = "")

}