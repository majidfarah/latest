package com.stemguide.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StemGuideApplication

fun main(args: Array<String>) {
    runApplication<StemGuideApplication>(*args)
}
