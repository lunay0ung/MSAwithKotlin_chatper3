package com.microservices.kotlin.chapter3.SerializationDemo

//직렬화는 복잡한 객체에도 적용된다.
data class ComplexObject(var object1 : SimpleObjectDataClass? = null) {

    /*
    * 이 클래스는 데이터 클래스이기 때문에 getObject메소드를 갖는다.
    * 역직렬화할 때 objectmapper는 값 또는 객체를 object1이라는 속성으로 역질렬화한ㄷ.
    * 필요한 만큼의 중첩 레벨을 가진 복잡한 객체를 생성할 수 있고,
    * 필요하다면 중첩된 클래스nested class를 사용할 수 있다.
    * */
}