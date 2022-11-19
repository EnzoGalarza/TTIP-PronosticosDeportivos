package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.builders.UserBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.Notification
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NotificationControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    lateinit var mapper: ObjectMapper

    @Autowired
    lateinit var tournamentRepository : TournamentRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var competitionRepository: CompetitionRepository

    @Autowired
    lateinit var userScoreRepository: UserScoreRepository

    @Autowired
    lateinit var notificationRepository : NotificationRepository

    val userBuilder = UserBuilder()

    lateinit var user : User
    lateinit var notification: Notification

    @BeforeEach
    @Throws(Exception::class)
    fun setup() {
        mapper = ObjectMapper()
        userRepository.deleteAll()
        notificationRepository.deleteAll()

        notification = Notification(true,"Mensaje",1)

        user = userBuilder.build()
        user.addNotification(notification)

        userRepository.save(user)
    }

    @Test
    @WithMockUser("email")
    fun getNotifications(){
        mockMvc.perform(get("/notifications/{userId}",user.id))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(1))
    }

    @Test
    @WithMockUser("email")
    fun deleteNotification(){
        val oldNotifications = notificationRepository.findAll()
        val response = mockMvc.perform(delete("/notifications/{userId}/{notificationId}/delete",user.id,1))
        val notifications = notificationRepository.findAll()

        response.andExpect(status().isNoContent)
        assertEquals(oldNotifications.size,1)
        assertEquals(notifications.size,0)
    }
}