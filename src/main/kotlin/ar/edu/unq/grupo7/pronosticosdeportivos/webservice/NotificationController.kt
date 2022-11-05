package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.Notification
import ar.edu.unq.grupo7.pronosticosdeportivos.service.NotificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
class NotificationController {

    @Autowired
    lateinit var notificationService : NotificationService

    @GetMapping("/notifications/{userId}")
    fun getNotifications(@PathVariable("userId") userId : Long) : ResponseEntity<List<Notification>>{
        val notifications = notificationService.getNotificationsFromUser(userId)
        return ResponseEntity(notifications, HttpStatus.OK)
    }

    @DeleteMapping("/notifications/{userId}/{notificationId}/delete")
    fun deleteNotification(@PathVariable("userId") userId: Long,@PathVariable("notificationId") notificationId : Long) : ResponseEntity<Any>{
        notificationService.deleteNotification(notificationId, userId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}