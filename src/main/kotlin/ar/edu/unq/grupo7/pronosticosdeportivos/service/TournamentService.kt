package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.TournamentDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.toModel
import ar.edu.unq.grupo7.pronosticosdeportivos.model.exceptions.UserNotFoundException
import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Tournament
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
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

    @Transactional
    fun saveTournament(tournament: TournamentDTO) : Tournament{
        try {
            val createdTournament = tournament.toModel()
            for(userEmail in tournament.usersEmail){
                val getUser = userRepository.findByEmail(userEmail).get()
                getUser.addTournament(createdTournament)
            }
            return tournamentRepository.save(createdTournament)
        } catch (e:NoSuchElementException){
            throw UserNotFoundException("No se encontró algún usuario")
        }
    }

    fun getTournaments() : List<Tournament>{
        return tournamentRepository.findAll()
    }

}
