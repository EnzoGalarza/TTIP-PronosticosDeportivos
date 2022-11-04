package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.TournamentDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.UserTournamentDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.toModel
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.TournamentNotFoundException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.UserNotFoundException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Tournament
import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.UserScore
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.TournamentRepository
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.UserRepository
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.UserScoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TournamentService {

    @Autowired
    private lateinit var tournamentRepository : TournamentRepository

    @Autowired
    private lateinit var userRepository : UserRepository

    @Autowired
    private lateinit var userScoreRepository : UserScoreRepository

    @Autowired
    private lateinit var pronosticService: PronosticService

    @Transactional
    fun saveTournament(tournament: TournamentDTO) : Tournament{
        try {
            val createdTournament = tournament.toModel()
            createdTournament.validate()
            for(userEmail in tournament.usersEmail){
                val getUser = userRepository.findByEmail(userEmail).get()
                createdTournament.addUser(getUser)
            }
            return tournamentRepository.save(createdTournament)
        } catch (e:NoSuchElementException){
            throw UserNotFoundException("No se encontró algún usuario")
        }
    }

    @Transactional
    fun getTournamentsFromUser(userId : Long) : List<UserTournamentDTO>{
        return tournamentRepository.findByUserId(userId)
    }

    @Transactional
    fun updateTournament(tournamentId : Long){
        val tournament = tournamentRepository.findById(tournamentId).get()
        var hit : Boolean
        for(user in tournament.users){
            val pronosticsFromUser = pronosticService.notEvaluatedPronostics(user.user.username,tournament.competition,tournamentId)
            for(pronostic in pronosticsFromUser){
                hit = false
                tournament.addEvaluatedPronostic(pronostic.id)
                for(criteria in tournament.criterias){
                    val tournamentCriteria = tournament.getCriteria(criteria.name)
                    if(tournamentCriteria.eval(pronostic, pronostic.match)) {
                        user.sumPoints(criteria.score)
                        hit = true;
                    }
                }
                if(hit){
                    user.addHit()
                }
                user.addPronostic()
            }
            user.calculatePercentage()
        }
        tournamentRepository.save(tournament)
    }

    @Transactional
    fun inviteUsers(tournamentId: Long, users: List<String>) {
        val tournament = tournamentRepository.findById(tournamentId).orElseThrow {
            throw TournamentNotFoundException("No se encontró el torneo")
        }
        for(userEmail in users){
            val user = userRepository.findByEmail(userEmail).orElseThrow {
                throw UserNotFoundException("Usuario ${userEmail} no se encuentra registrado")
            }
            tournament.addUser(user)
        }
        tournamentRepository.save(tournament)
    }

    @Transactional
    fun getTournamentsUserScores(tournamentId : Long): List<UserScore> {
       return userScoreRepository.findTournamentScores(tournamentId)
    }

}
