package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.model.Pronostic
import ar.edu.unq.grupo7.pronosticosdeportivos.service.PronosticService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@EnableAutoConfiguration
@CrossOrigin
class PronosticController {

    @Autowired
    private lateinit var pronosticService: PronosticService

    @GetMapping(value = ["/pronosticos/{user}"])
    fun getPronosticsFromUser(@PathVariable("user") user : String) : List<Pronostic>{
         return pronosticService.pronosticsFromUser(user)
    }

    @PostMapping("/pronostico")
    fun registerPronostic(@RequestBody pronostic: Pronostic) : ResponseEntity<Any>{
        var pronosticoGuardado = pronosticService.save(pronostic)
        return ResponseEntity(pronosticoGuardado,HttpStatus.CREATED)
    }


}