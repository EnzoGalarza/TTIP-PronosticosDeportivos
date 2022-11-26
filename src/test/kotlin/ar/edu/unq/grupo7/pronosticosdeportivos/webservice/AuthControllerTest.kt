package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.builders.PronosticBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.builders.UserBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.LoginDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.RegisterDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.TournamentDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Criteria
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    lateinit var mapper: ObjectMapper

    @Autowired
    lateinit var userRepository : UserRepository

    @Autowired
    lateinit var confirmationTokenRepository: ConfirmationTokenRepository

    val userBuilder = UserBuilder()

    lateinit var userToRegister : RegisterDTO

    @BeforeEach
    @Throws(Exception::class)
    fun setup() {
        mapper = ObjectMapper()
        mapper.registerModule(JavaTimeModule())
        confirmationTokenRepository.deleteAll()
        userRepository.deleteAll()
        userToRegister = RegisterDTO("name","enzogalarza2@gmail.com","123","image.png")

    }

    @Test
    fun registerUser(){
        val response = mockMvc.perform(
            MockMvcRequestBuilders.post("/register")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsBytes(userToRegister)))

        val getUser = userRepository.findByEmail("enzogalarza2@gmail.com").get()

        response.andExpect(MockMvcResultMatchers.status().isOk)

        assertEquals("enzogalarza2@gmail.com",getUser.username)
        assertEquals("name",getUser.getName())
        assertEquals("image.png",getUser.getProfileImage())
    }

    @Test
    fun loginUser(){
        mockMvc.perform(
            MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(userToRegister)))

        val response = mockMvc.perform(
            MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(LoginDTO(userToRegister.email,userToRegister.password))))

        val headers = response.andReturn().response.headerNames

        response.andExpect(MockMvcResultMatchers.status().isOk)
        response.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userToRegister.name))
        response.andExpect(MockMvcResultMatchers.jsonPath("$.profileImage").value(userToRegister.image))
        assertTrue(headers.contains("authentication"))
    }

    @Test
    @WithMockUser("email")
    fun testGetUsersEmails(){
        val user = userRepository.save(userBuilder.withEmail("enzogalarza2@gmail.com").build())
        val user2 = userRepository.save(userBuilder.withEmail("EnzoGl33@hotmail.com").build())
        val user3 = userRepository.save(userBuilder.withEmail("EnzoGl33@outlook.com").build())

        mockMvc.perform(
            MockMvcRequestBuilders.get("/users/{user}",user3.username))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(user2.username))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value(user.username))
    }

    @Test
    fun testGetUsersEmailsWithoutLogin(){
        mockMvc.perform(
            MockMvcRequestBuilders.get("/users/{user}","email"))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun testCreateTournamentWithoutLogin(){
        val user = userBuilder.withEmail("user1@hotmail.com").build()

        userRepository.save(user)
        val usersToInvite = listOf(user.username)
        val criteria = listOf(Criteria("Complete",3))
        val tournament = TournamentDTO("Tournament 1", "PD", "user1@hotmail.com",usersToInvite, criteria)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/tournaments")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(tournament)))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun getTournamentWithoutLogin(){
        mockMvc.perform(MockMvcRequestBuilders.get("/tournaments/{userId}", 1))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun getPronosticsFromUserWithoutLogin(){
        mockMvc.perform(MockMvcRequestBuilders.get("/pronostics/{user}/{competition}", "jose", "PD"))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun registerPronosticWithoutLogin(){
        val pronosticsToSave = listOf(PronosticBuilder().build())
        mockMvc.perform(
            MockMvcRequestBuilders.post("/pronostics")
            .content(mapper.writeValueAsBytes(pronosticsToSave)))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun getNotificationsWithoutLogin(){
        mockMvc.perform(MockMvcRequestBuilders.get("/notifications/{userId}", 1))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun deleteNotificationWithoutLogin(){
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/notifications/{userId}/{notificationId}/delete", 1, 1)
        ).andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun getMatchesWithoutLogin(){
        mockMvc.perform(MockMvcRequestBuilders.get("/matches/{competition}/{matchDay}", "PD", 1))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun getCriteriaWithoutLogin(){
        mockMvc.perform(MockMvcRequestBuilders.get("/criterias/{tournamentId}", 1))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun getCompetitionsWithoutLogin(){
        mockMvc.perform(MockMvcRequestBuilders.get("/competitions"))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun getCompetitionCurrentMatchDayWithoutLogin(){
        mockMvc.perform(MockMvcRequestBuilders.get("/currentMatchDay/{competition}","Competition"))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

}