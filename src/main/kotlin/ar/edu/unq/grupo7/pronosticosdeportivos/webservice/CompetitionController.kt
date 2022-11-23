package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.service.CompetitionService
import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Competition
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
class CompetitionController {

    @Autowired
    lateinit var competitionService: CompetitionService

    @GetMapping(value = ["/competitions"])
    fun getCompetitions(): ResponseEntity<List<Competition>> {
        val competitions : List<Competition> = competitionService.getCompetitions()

        return ResponseEntity<List<Competition>>(competitions, HttpStatus.OK)
    }

    @GetMapping(value = ["/currentMatchDay/{competition}"])
    fun getCurrentMatchDay(@PathVariable("competition") competition: String): ResponseEntity<Int> {
        val currentMatchDay : Int = competitionService.getCurrentMatchDay(competition)

        return ResponseEntity<Int>(currentMatchDay, HttpStatus.OK)
    }

}