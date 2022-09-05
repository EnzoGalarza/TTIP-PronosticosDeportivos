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
    lateinit var competitionService: CompetitionService

    @GetMapping(value = ["/competitions"])
    fun getCompetitions(): ResponseEntity<MutableList<Competition>> {
        val competitions : MutableList<Competition> = competitionService.getCompetitions()

        return ResponseEntity<MutableList<Competition>>(competitions, HttpStatus.OK)
    }

}