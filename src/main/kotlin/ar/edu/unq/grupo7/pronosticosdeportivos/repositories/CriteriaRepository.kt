package ar.edu.unq.grupo7.pronosticosdeportivos.repositories

import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Criteria
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Configuration
@Repository
interface CriteriaRepository : JpaRepository<Criteria, Long> {

    @Query("SELECT * FROM criteria\n" +
            "WHERE tournament_id = :tournamentId",nativeQuery = true)
    fun findByTournamentId(tournamentId: Long): List<Criteria>

}