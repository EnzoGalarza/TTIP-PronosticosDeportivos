package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Criteria
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.CriteriaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CriteriaService {

    @Autowired
    lateinit var repository : CriteriaRepository

    fun getCriterias(tournamentId: Long): List<Criteria>{
        return repository.findByTournamentId(tournamentId)
    }

}