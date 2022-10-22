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

    @GetMapping("/tournaments/{user}")
    fun getTournaments(@PathVariable("user") user : String) : ResponseEntity<List<Tournament>>{
        val tournaments = tournamentService.getTournamentsFromUser(user)
        return ResponseEntity(tournaments,HttpStatus.OK)
    }

    @PutMapping("/tournaments/update/{tournamentId}")
    fun updateTournament(@PathVariable("tournamentId") tournamentId : Long){
        tournamentService.updateTournament(tournamentId)
    }

    @PutMapping("/tournaments/{tournamentId}")
    fun inviteUsersToTournament(@PathVariable("tournamentId") tournamentId: Long, @RequestBody users : List<String>) : ResponseEntity<Any>{
        tournamentService.inviteUsers(tournamentId,users)
        return ResponseEntity(HttpStatus.OK)
    }
}