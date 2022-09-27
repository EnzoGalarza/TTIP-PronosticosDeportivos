package ar.edu.unq.grupo7.pronosticosdeportivos.repositories

import ar.edu.unq.grupo7.pronosticosdeportivos.model.token.ConfirmationToken
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Repository
interface ConfirmationTokenRepository : JpaRepository<ConfirmationToken?, Long?> {
    fun findByToken(token: String): Optional<ConfirmationToken>
    fun findByUser(user: User): ConfirmationToken

    @Transactional
    @Modifying
    @Query(
        "UPDATE ConfirmationToken c " +
                "SET c.isConfirmed = ?2 " +
                "WHERE c.token = ?1"
    )
    fun updateConfirmed(
        token: String?,
        isConfirmed: Boolean
    ): Int
}