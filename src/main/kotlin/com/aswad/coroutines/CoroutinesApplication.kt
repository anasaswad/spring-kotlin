package com.aswad.coroutines

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration
import org.springframework.boot.runApplication


@SpringBootApplication(exclude = arrayOf(MongoReactiveDataAutoConfiguration::class))
class CoroutinesApplication

fun main(args: Array<String>) {
    runApplication<CoroutinesApplication>(*args)
}
