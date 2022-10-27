package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.builders.UserBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.TournamentDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.toModel
import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.CompleteResult
import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Criteria
import ar.edu.unq.grupo7.pronosticosdeportivos.model.user.User
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.TournamentRepository
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false )
@ActiveProfiles("test")
class TournamentControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    lateinit var mapper: ObjectMapper

    @Autowired
    lateinit var tournamentRepository : TournamentRepository

    @Autowired
    lateinit var userRepository: UserRepository

    val userBuilder = UserBuilder()

    @BeforeEach
    @Throws(Exception::class)
    fun setup() {
        mapper = ObjectMapper()
        tournamentRepository.deleteAll()
        userRepository.deleteAllInBatch()
    }

    @Test
    fun testCreateTournament(){
        val user = userBuilder.withEmail("user1@hotmail.com").build()

        userRepository.save(user)
        val usersToInvite = listOf(user.username)
        val criteria = listOf(Criteria("Complete",3))
        val tournament = TournamentDTO("Tournament 1", "PD", "user1@hotmail.com",usersToInvite, criteria)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/tournaments")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsBytes(tournament)))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Tournament 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.competition").value("PD"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.creator").value("user1@hotmail.com"))
    }

    @Test
    fun testCreateTournamentWithUnsavedUser(){
        val user = userBuilder.build()
        val usersToInvite = listOf(user.username)
        val criteria = listOf(Criteria("Complete",3))
        val tournament = TournamentDTO("Tournament 1", "PD", "user1@hotmail.com",usersToInvite, criteria)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/tournaments")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(tournament)))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun testCreateTournamentWithInvalidName(){
        val user = userBuilder.withEmail("user1@hotmail.com").build()

        userRepository.save(user)
        val usersToInvite = listOf(user.username)
        val criteria = listOf(Criteria("Complete",3))
        val tournament = TournamentDTO("T", "PD", "user1@hotmail.com",usersToInvite, criteria)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/tournaments")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(tournament)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

}