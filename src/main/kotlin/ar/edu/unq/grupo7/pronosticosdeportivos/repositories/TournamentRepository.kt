package ar.edu.unq.grupo7.pronosticosdeportivos.repositories

import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Tournament
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Configuration
@Repository
interface TournamentRepository : JpaRepository<Tournament,Long>{

    @Query("SELECT * FROM tournament\n" +
            "INNER JOIN tournament_user on tournament.id = tournament_user.tournament_id\n" +
            "INNER JOIN user_score on tournament_user.user_score_id = user_score.id\n" +
            "WHERE user_score.user_score_id = ?1", nativeQuery = true)
    fun findByUserId(id: Long): List<Tournament>

}