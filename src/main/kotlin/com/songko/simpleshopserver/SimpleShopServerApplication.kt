package com.songko.simpleshopserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleShopServerApplication

fun main(args: Array<String>) {
	runApplication<SimpleShopServerApplication>(*args)
}
