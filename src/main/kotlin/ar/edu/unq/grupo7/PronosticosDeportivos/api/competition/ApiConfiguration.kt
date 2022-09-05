package ar.edu.unq.grupo7.PronosticosDeportivos.api.competition

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class ApiConfiguration {
    @Bean
    fun restTemplate(): RestTemplate? {
        return RestTemplate()
    }
}