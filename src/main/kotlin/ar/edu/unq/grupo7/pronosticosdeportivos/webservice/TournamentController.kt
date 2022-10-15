package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.TournamentDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Tournament
import ar.edu.unq.grupo7.pronosticosdeportivos.service.TournamentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
class TournamentController {

    @Autowired
    private lateinit var tournamentService : TournamentService

    @PostMapping("/tournaments")
    fun createTournament(@RequestBody tournament: TournamentDTO) : ResponseEntity<TournamentDTO>{
        tournamentService.saveTournament(tournament)
        return ResponseEntity(tournament,HttpStatus.CREATED)
    }

    @GetMapping("/tournaments")
    fun getTournaments() : ResponseEntity<List<Tournament>>{
        val tournaments = tournamentService.getTournaments()
        return ResponseEntity(tournaments,HttpStatus.OK)
    }
}