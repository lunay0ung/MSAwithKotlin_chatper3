package com.microservices.kotlin.chapter3.SerializationDemo

/*
* 자바 객체를 출력할 때 스프링은 내부적으로 Jackson이 제공하는 ObjectMapper를 사용해 JSON expression을 생성한다.
* 이 object mapper는 java reflection api를 사용해 제공된 객체의 public 속성을 검색하고 표현을 만든다.
* */
class SimpleObject {
    public val name = "hello"
    //private val place = "world" //이렇게 하고 api를 호출하면 {"name":"hello"} 데이터만 리턴된다. public아니고 private으로 선언되었기 때문
    //public fun getPlace() = place //-> {"name":"hello","place":"world"}

    /*
    * 내부적으로는 ObjectMapper가 public 메소드인 getPlace를 찾아 직렬화하는데,
    * get과 대문자로 이루어진 이름을 갖는 public 메소드는 get을 제외한 이름의 속성으로 JSON객체에 직렬화된다.
    * 따라서 getPlace는 해당함수의 값을 place라는 이름의 속성으로 직렬화한다.
    * --> 무슨말인지 모르겠다. 원문은 어떻게 되어있을까..?
    * */

    /*
    * 다음과 같이 수정하면 ObjectMapper가 속성이 아니라 메소드를 직렬화하기 때문에
    * 정확히 동일한 결과를 얻을 수 있다.
    * */
    private val zone = "world"
    public fun getPlace() = zone;
}