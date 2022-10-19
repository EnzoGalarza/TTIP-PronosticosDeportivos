package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.Pronostic
import ar.edu.unq.grupo7.pronosticosdeportivos.service.PronosticService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
class PronosticController {

    @Autowired
    private lateinit var pronosticService: PronosticService

    @GetMapping(value = ["/pronostics/{user}"])
    fun getPronosticsFromUser(@PathVariable("user") user : String) : List<Pronostic>{
         return pronosticService.pronosticsFromUser(user)
    }

    @PostMapping("/pronostics")
    fun registerPronostics(@RequestBody pronosticList: MutableList<Pronostic>) : ResponseEntity<List<Pronostic>>{
        pronosticService.saveAll(pronosticList)
        return ResponseEntity(pronosticList,HttpStatus.CREATED)
    }

}