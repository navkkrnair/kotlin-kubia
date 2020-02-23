package com.kubia

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinKubiaApplication

fun main(args: Array<String>) {
	runApplication<KotlinKubiaApplication>(*args)
}
