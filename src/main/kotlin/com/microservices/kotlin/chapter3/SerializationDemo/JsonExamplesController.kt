package com.microservices.kotlin.chapter3.SerializationDemo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JsonExamplesController {

    @GetMapping(value = ["/json"])
    //fun getJson() = SimpleObject()
    //fun getJson() = SimpleObjectDataClass("hi", "kotlin")
    fun getJson() = ComplexObject(object1 = SimpleObjectDataClass("more", "complex"))
    //{"object1":{"name":"more","place":"complex"}}
}