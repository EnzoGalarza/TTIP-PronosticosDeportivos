package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.PartidoDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.service.PartidoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PartidoController {

    @Autowired
    lateinit var partidoService: PartidoService

    @GetMapping(value = ["/matches/{competition}"])
    fun getTodosLosPartidos(@PathVariable("competition") competition: String): ResponseEntity<Any> {
        val partidos = partidoService.getPartidos(competition)
        return ResponseEntity(partidos,HttpStatus.OK)
    }

}
