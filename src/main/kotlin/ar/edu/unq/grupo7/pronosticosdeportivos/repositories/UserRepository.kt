package ar.edu.unq.grupo7.pronosticosdeportivos.repositories

import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface UserRepository: JpaRepository<User, Long> {

    fun findByEmail(email: String?): Optional<User>

    @Transactional
    @Modifying
    @Query(
        "UPDATE User a " +
                "SET a.enabled = TRUE WHERE a.email = :email"
    )
    fun enableAppUser(email: String?): Int

    @Query("SELECT email FROM public.user WHERE email <> :user", nativeQuery = true)
    fun findUsersEmail(user: String): List<String>

}