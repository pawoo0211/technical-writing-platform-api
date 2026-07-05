package dev.techwrite.platform

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TechWriteApiApplication

fun main(args: Array<String>) {
    runApplication<TechWriteApiApplication>(*args)
}
