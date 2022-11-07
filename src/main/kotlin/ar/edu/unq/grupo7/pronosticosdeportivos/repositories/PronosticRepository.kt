package ar.edu.unq.grupo7.pronosticosdeportivos.repositories

import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.Pronostic
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Configuration
@Repository
interface PronosticRepository : JpaRepository<Pronostic,Long>{

    @Query("SELECT * FROM pronostic\n" +
            "INNER JOIN match on match.id = pronostic.match_id\n" +
            "WHERE pronostic.user = :user AND match.competition = :competition",nativeQuery = true)
    fun findByUserAndCompetition(user : String, competition : String) : List<Pronostic>

    @Query("SELECT * FROM pronostic\n" +
            "INNER JOIN match on match.id = pronostic.match_id\n" +
            "WHERE pronostic.user = :user \n" +
            "AND match.competition = :competition \n" +
            "AND match.status = 'FINISHED' \n" +
            "AND pronostic.id NOT IN (\n" +
            "    SELECT pronostic_id FROM evaluated_pronostic\n" +
            "    WHERE tournament_id = :tournamentId \n" +
            ")",nativeQuery = true)
    fun findNotEvaluatedPronostics(user: String, competition: String, tournamentId: Long): List<Pronostic>
}
