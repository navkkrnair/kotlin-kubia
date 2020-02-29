package com.kubia

import io.micrometer.core.instrument.MeterRegistry
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.ReactiveHealthIndicator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.net.Inet4Address

@SpringBootApplication
@RestController
@RequestMapping("")
class KotlinKubiaApplication
{

    @Autowired
    lateinit var meterRegistry: MeterRegistry

    val logger: Logger = LoggerFactory.getLogger(KotlinKubiaApplication::class.java)

    @GetMapping()
    fun getHost(serverHttpRequest: ServerHttpRequest): Mono<String>
    {
        logger.info("Received request from ${serverHttpRequest.remoteAddress?.address?.hostAddress}")
        meterRegistry.counter("number.of.hits")
                .increment()
        return Mono.just("You have hit ${Inet4Address.getLocalHost().hostName}")
    }
}

fun main(args: Array<String>)
{
    runApplication<KotlinKubiaApplication>(*args)

    Runtime.getRuntime()
            .addShutdownHook(Thread {
                println("Shutting down the system")
            })
}

@Component
class KubiaHealth : ReactiveHealthIndicator
{
    @Autowired
    lateinit var meterRegistry: MeterRegistry

    override fun health(): Mono<Health>
    {
        val count = meterRegistry.counter("number.of.hits")
                .count()
        return if (count > 10 && count < 30)
            Mono.just(Health.down().build())
        else
            Mono.just(Health.up().build())
    }
}
