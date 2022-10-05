package ar.edu.unq.grupo7.pronosticosdeportivos.repositories

import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Match
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Configuration
@Repository
interface MatchRepository : JpaRepository<Match, Long> {

    @Query("SELECT * FROM partidos WHERE (competition = ?2 AND match_day = ?1)", nativeQuery = true)
    fun findByCompetitionAndMatchDay(matchDay : Int, competition: String) : List<Match>

    @Query("SELECT * FROM partidos WHERE (code = ?1)",nativeQuery = true)
    fun findByCode(code : Long) : Match
}