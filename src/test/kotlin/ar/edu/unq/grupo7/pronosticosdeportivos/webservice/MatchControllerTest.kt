package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.builders.MatchBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.builders.TeamBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.toDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.model.dto.MatchListDTO
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.MatchRepository
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.PronosticRepository
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.TeamRepository
import ar.edu.unq.grupo7.pronosticosdeportivos.service.GenerateHeader
import ar.edu.unq.grupo7.pronosticosdeportivos.service.MatchService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.web.client.RestTemplate

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false )
@ActiveProfiles("test")
class MatchControllerTest {

    @Autowired
    lateinit var teamRepository: TeamRepository

    @Autowired
    lateinit var matchRepository: MatchRepository

    @Autowired
    lateinit var pronosticRepository: PronosticRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var restTemplate : RestTemplate

    private val matchBuilder : MatchBuilder = MatchBuilder()
    private val teamBuilder : TeamBuilder = TeamBuilder()

    @BeforeEach
    @Throws(Exception::class)
    fun setup() {
        pronosticRepository.deleteAll()
        matchRepository.deleteAll()
        teamRepository.deleteAll()
    }

    @Test
    fun getMatchesOfCompetitionPLWhenEmptyDB(){
        val match1 = matchBuilder.withCode(1).build().toDTO()
        val match2 = matchBuilder.withCode(5).build().toDTO()

        val entity = GenerateHeader.generateHeader()

        `when`(restTemplate.exchange("https://api.football-data.org/v4/competitions/PL/matches?matchday=1",
            HttpMethod.GET, entity,object : ParameterizedTypeReference<MatchListDTO>() {})).thenReturn(ResponseEntity(
            MatchListDTO(mutableListOf(match1,match2)), HttpStatus.OK
        ))

        mockMvc!!.perform(get("/matches/{competition}/{matchDay}","PL",1))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[1].id").value(5))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun getMatchesOfCompetitionPDWhenDBNotEmpty(){
        val team1 = teamRepository.save(teamBuilder.withName("team 1 p").build())
        val team2 = teamRepository.save(teamBuilder.withName("team 2 p").build())

        matchRepository.save(matchBuilder.withStatus("FINISHED").withLocal(team1).withAway(team2).withCompetition("PD").withCode(1).withDay(1).build())
        matchRepository.save(matchBuilder.withCompetition("PL").withLocal(team1).withAway(team2).withCode(2).build())

        mockMvc!!.perform(get("/matches/{competition}/{matchDay}","PD",1))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }
}