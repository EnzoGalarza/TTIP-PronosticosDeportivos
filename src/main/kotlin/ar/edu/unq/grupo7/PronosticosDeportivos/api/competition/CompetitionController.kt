package ar.edu.unq.grupo7.PronosticosDeportivos.api.competition

import ar.edu.unq.grupo7.PronosticosDeportivos.model.competition.Competition
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
class CompetitionController {

    @Autowired
    private val competitionService: CompetitionService? = null

    //@GetMapping(value = ["/getCompetitions"])
    //open fun getCompetitions(): ResponseEntity<MutableList<Competition>> {
    //    val competitions: MutableList<Competition> = competitionService?.getCompetitions() ?:
    //    //if (competitions.isEmpty()) {
    //    //    //arrojar excepción
    //    //}
    //    return ResponseEntity<MutableList<Competition?>?>(competitions, HttpStatus.OK)
    //}

}