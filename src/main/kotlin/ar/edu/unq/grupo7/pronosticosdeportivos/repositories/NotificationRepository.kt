package ar.edu.unq.grupo7.pronosticosdeportivos.repositories

import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.Notification
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Configuration
@Repository
interface NotificationRepository : JpaRepository<Notification, Long> {

     @Query("SELECT * FROM notification\n" +
            "WHERE user_id = :userId", nativeQuery = true)
     fun getByUserId(userId: Long): List<Notification>
}
