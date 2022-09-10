package ar.edu.unq.grupo7.pronosticosdeportivos.repositories

import ar.edu.unq.grupo7.pronosticosdeportivos.model.Pronostico
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Configuration
@Repository
interface PronosticoRepository : JpaRepository<Pronostico,Long>{

    @Query("SELECT * FROM pronosticos p WHERE (p.user = ?1)", nativeQuery = true)
    fun pronosticosDeUsuario(user : String) : Optional<List<Pronostico>>
}
