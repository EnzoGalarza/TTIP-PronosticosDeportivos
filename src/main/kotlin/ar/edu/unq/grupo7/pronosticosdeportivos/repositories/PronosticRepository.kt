package ar.edu.unq.grupo7.pronosticosdeportivos.repositories

import ar.edu.unq.grupo7.pronosticosdeportivos.model.Pronostic
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

    @Query("SELECT * FROM pronosticos p WHERE (p.user = ?1)", nativeQuery = true)
    fun pronosticsFromUser(user : String) : List<Pronostic>

    @Modifying
    @Query("UPDATE Pronostic p SET p.localGoals = :localGoals, p.awayGoals = :awayGoals WHERE p.id = :id")
    fun updatePronostic(@Param("id") id : Long, @Param("localGoals") localGoals : Int, @Param("awayGoals") awayGoals : Int)

    fun findByMatchId(matchId : Int) : Optional<Pronostic>
}
