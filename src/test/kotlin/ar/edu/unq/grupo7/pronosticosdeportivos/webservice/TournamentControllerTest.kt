package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.builders.CompetitionBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.builders.UserBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.TournamentDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.toModel
import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.CompleteResult
import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Criteria
import ar.edu.unq.grupo7.pronosticosdeportivos.model.tournaments.Tournament
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TournamentControllerTest {

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

    @BeforeEach
    @Throws(Exception::class)
    fun setup() {
        mapper = ObjectMapper()
        tournamentRepository.deleteAll()
        userScoreRepository.deleteAll()
        notificationRepository.deleteAll()
        competitionRepository.deleteAll()
        userRepository.deleteAll()

    }

    @Test
    @WithMockUser("email")
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
    @WithMockUser("email")
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
    @WithMockUser("email")
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

    @Test
    @WithMockUser("email")
    fun getTournaments(){
        val userTournament = userRepository.save(userBuilder.withEmail("usert1@hotmail.com").build())

        val tournament = Tournament("tournament 1","PD", listOf(Criteria("complete",3)))

        tournament.addUser(userTournament)
        tournamentRepository.save(tournament)

        mockMvc.perform(get("/tournaments/{userId}",userTournament.id))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(tournament.name))
    }

    @Test
    @WithMockUser("email")
    fun getTournamentScores(){
        val userTournament = userRepository.save(userBuilder.withEmail("usert1@hotmail.com").build())

        val tournament = Tournament("tournament 1","PD", listOf(Criteria("complete",3)))

        tournament.addUser(userTournament)

        val savedTournament = tournamentRepository.save(tournament)

        mockMvc.perform(get("/tournamentScores/{tournamentId}",savedTournament.id))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].user.name").value(userTournament.getName()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].score").value(0))
    }


    @Test
    @WithMockUser("email")
    fun sendUserInvitation(){
        competitionRepository.save(CompetitionBuilder().withCode("PD").build())
        val userTournament = userRepository.save(userBuilder.withEmail("usert1@hotmail.com").build())

        val tournament = Tournament("tournament 1","PD", listOf(Criteria("complete",3)))

        val savedTournament = tournamentRepository.save(tournament)

        val response = mockMvc.perform(post("/tournaments/{tournamentId}",savedTournament.id)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsBytes(listOf(userTournament.username))))

        val notification = notificationRepository.getByUserId(userTournament.id)

        response.andExpect(MockMvcResultMatchers.status().isOk)
        assertEquals(notification.size,1)
        assertEquals(notification[0].tournamentId,savedTournament.id)
    }

    @Test
    @WithMockUser("email")
    fun acceptUserInvitation(){
        competitionRepository.save(CompetitionBuilder().withCode("PD").build())
        val userTournament = userRepository.save(userBuilder.withEmail("usert1@hotmail.com").build())

        val tournament = Tournament("tournament 1","PD", listOf(Criteria("complete",3)))

        val savedTournament = tournamentRepository.save(tournament)

        val notification = notificationRepository.save(Notification(false,"torneo",savedTournament.id))

        val response = mockMvc.perform(post("/acceptInvitation?tournamentId=${savedTournament.id}&userEmail=${userTournament.username}&invitationId=${notification.id}"))

        val getTournament = tournamentRepository.getReferenceById(savedTournament.id)

        response.andExpect(MockMvcResultMatchers.status().isOk)
        assertEquals(getTournament.users.size,1)
    }


}