package com.microservices.kotlin.chapter3

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JsonExamplesController {

    @GetMapping(value = ["/jason"])
    fun getJson() = SimpleObject()
}