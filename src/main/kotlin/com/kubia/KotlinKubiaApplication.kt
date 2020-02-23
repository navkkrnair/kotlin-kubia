package com.kubia

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest
import org.springframework.web.multipart.support.RequestPartServletServerHttpRequest
import reactor.core.publisher.Mono
import java.net.Inet4Address

@SpringBootApplication
@RestController
@RequestMapping("/")
class KotlinKubiaApplication
{

	@GetMapping()
	fun getHost(serverHttpRequest: ServerHttpRequest): Mono<String>
	{
		return Mono.just("You have hit ${Inet4Address.getLocalHost().hostName}")

	}
}

fun main(args: Array<String>)
{
    runApplication<KotlinKubiaApplication>(*args)
}
