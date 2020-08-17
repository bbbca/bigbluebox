package ca.bigbluebox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BigblueboxApplication

fun main(args: Array<String>) {
	runApplication<BigblueboxApplication>(*args)
}
