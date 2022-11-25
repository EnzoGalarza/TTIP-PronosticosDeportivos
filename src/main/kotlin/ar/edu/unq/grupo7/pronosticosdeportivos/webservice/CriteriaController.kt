package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Criteria
import ar.edu.unq.grupo7.pronosticosdeportivos.service.CriteriaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class CriteriaController {

    @Autowired
    lateinit var criteriaService: CriteriaService

    @GetMapping(value = ["/criterias/{tournamentId}"])
    fun getCriterias(@PathVariable("tournamentId") tournamentId: Long): ResponseEntity<List<Criteria>> {
        val criterias = criteriaService.getCriterias(tournamentId)

        return ResponseEntity(criterias, HttpStatus.OK)
    }

}