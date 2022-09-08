package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.model.Pronostico
import ar.edu.unq.grupo7.pronosticosdeportivos.service.PronosticoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@EnableAutoConfiguration
@CrossOrigin
class PronosticoController {

    @Autowired
    private lateinit var pronosticoService: PronosticoService

    @GetMapping(value = ["/pronosticos"])
    fun getPronosticos() : List<Pronostico>{
         return pronosticoService.findAll()
    }

    @PostMapping("/pronostico")
    fun registrarPronostico(@RequestBody pronostico: Pronostico) : ResponseEntity<Any>{
        var pronosticoGuardado = pronosticoService.save(pronostico)
        return ResponseEntity(pronosticoGuardado,HttpStatus.CREATED)
    }


}