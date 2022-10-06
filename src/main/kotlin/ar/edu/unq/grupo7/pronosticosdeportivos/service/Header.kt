package ar.edu.unq.grupo7.pronosticosdeportivos.service

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders

class GenerateHeader {
    companion object InitHeader{
        fun generateHeader() : HttpEntity<String>{
            val headers: HttpHeaders = HttpHeaders()
            headers.set("X-Auth-Token", "f5bedce0ca024352a218d300e71d0798")
            return HttpEntity("parameters", headers)
        }
    }
}