package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.TournamentDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.toModel
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.TournamentNotFoundException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.UserNotFoundException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Tournament
import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.UserScore
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.TournamentRepository
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.UserRepository
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

    fun getTournamentsFromUser(user : String) : List<Tournament>{
        val getUser = userRepository.findByEmail(user).get()
        return tournamentRepository.findByUserId(getUser.id)
    }

    @Transactional
    fun updateTournament(tournamentId : Long){
        val tournament = tournamentRepository.findById(tournamentId).get()
        for(user in tournament.users){
            val pronosticsFromUser = pronosticService.notEvaluatedPronostics(user.user.username,tournament.competition,tournamentId)
            for(pronostic in pronosticsFromUser){
                tournament.addEvaluatedPronostic(pronostic.id)
                for(criteria in tournament.criterias){
                    val tournamentCriteria = tournament.getCriteria(criteria.name)
                    if(tournamentCriteria.eval(pronostic.localGoals,pronostic.awayGoals,
                            pronostic.match.localGoals!!,pronostic.match.awayGoals!!)){
                        user.sumPoints(criteria.score)
                    }
                }
            }

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

    fun getTournamentsUserScores(tournamentId : Long): List<UserScore> {
       return listOf()
    }

}
