package ar.edu.unq.grupo7.pronosticosdeportivos.service

import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.Notification
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.NotificationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationService {

    @Autowired
    lateinit var notificationRepository : NotificationRepository

    @Autowired
    lateinit var userService : UserService

    fun getNotificationsFromUser(userId: Long): List<Notification> {
        return notificationRepository.getByUserId(userId)
    }

    @Transactional
    fun deleteNotification(notificationId: Long, userId: Long) {
        val notification = notificationRepository.findById(notificationId).orElseThrow {
            Exception("error")
        }
        notificationRepository.delete(notification)
        userService.deleteNotification(userId, notification)
    }
}