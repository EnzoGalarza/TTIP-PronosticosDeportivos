package ar.edu.unq.grupo7.pronosticosdeportivos.repositories

import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface UserRepository: JpaRepository<User, Int> {

    fun findByEmail(email: String?): Optional<User>

    @Transactional
    @Modifying
    @Query(
        "UPDATE User a " +
                "SET a.enabled = TRUE WHERE a.email = ?1"
    )
    fun enableAppUser(email: String?): Int

}