package ar.edu.unq.grupo7.pronosticosdeportivos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class PronosticosDeportivosApplication

fun main(args: Array<String>) {
	runApplication<PronosticosDeportivosApplication>(*args)
}
