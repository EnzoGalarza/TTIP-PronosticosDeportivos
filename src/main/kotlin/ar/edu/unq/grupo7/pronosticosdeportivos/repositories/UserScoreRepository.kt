package ar.edu.unq.grupo7.pronosticosdeportivos.repositories

import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.UserScore
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserScoreRepository: JpaRepository<UserScore, Int> {

    @Query("SELECT * FROM user_score\n" +
           "INNER JOIN tournament_user on tournament_user.user_score_id = user_score.id\n" +
           "INNER JOIN public.user on public.user.id = user_score.user_score_id\n" +
           "WHERE tournament_user.tournament_id = ?1", nativeQuery = true)
    fun findTournamentScores(id: Long): List<UserScore>

}