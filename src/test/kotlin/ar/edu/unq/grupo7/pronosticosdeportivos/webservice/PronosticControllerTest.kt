package ar.edu.unq.grupo7.pronosticosdeportivos.webservice

import ar.edu.unq.grupo7.pronosticosdeportivos.builders.MatchBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.builders.PronosticBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.builders.TeamBuilder
import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Match
import ar.edu.unq.grupo7.pronosticosdeportivos.model.competitions.Team
import ar.edu.unq.grupo7.pronosticosdeportivos.model.pronostics.Pronostic
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.CompetitionRepository
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.MatchRepository
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.PronosticRepository
import ar.edu.unq.grupo7.pronosticosdeportivos.repositories.TeamRepository
import ar.edu.unq.grupo7.pronosticosdeportivos.service.PronosticService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false )
@ActiveProfiles("test")
class PronosticControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    lateinit var mapper: ObjectMapper

    @Autowired
    lateinit var pronosticRepository: PronosticRepository

    @Autowired
    lateinit var teamRepository: TeamRepository

    @Autowired
    lateinit var matchRepository: MatchRepository

    private val pronosticBuilder = PronosticBuilder()
    private val matchBuilder = MatchBuilder()
    private val teamBuilder = TeamBuilder()

    @BeforeEach
    @Throws(Exception::class)
    fun setup() {

        mapper = ObjectMapper()
        mapper.registerModule(JavaTimeModule())

        val team1 = teamRepository.save(teamBuilder.withName("team 1 p").build())
        val team2 = teamRepository.save(teamBuilder.withName("team 2 p").build())

        val match = matchRepository.save(matchBuilder.withLocal(team1).withAway(team2).withCompetition("PD").withCode(1).build())
        val match2 = matchRepository.save(matchBuilder.withLocal(team1).withAway(team2).withCode(15).build())

        val pronostic = Pronostic("jose",match,2,1)
        val pronostic2 = Pronostic("pedro",match2,0,2)

        pronosticRepository.save(pronostic)
        pronosticRepository.save(pronostic2)
    }


    @Test
    fun registerPronostics(){
        var team1 = Team("team1","PD","Team crest")
        var team2 = Team("team2","PD","Team2 crest")

        teamRepository.save(team1)
        teamRepository.save(team2)

        val match = Match(team1,team2, LocalDateTime.of(2022,12,9,15,0),
            null,null,10,"SCHEDULED",10,"PD")

        matchRepository.save(match)

        val pronostic1 = Pronostic("jose",match,2,1)
        val pronostic2 = Pronostic("pedro",match,2,1)

        val pronosticsToSave = mutableListOf(pronostic1,pronostic2)


        mockMvc.perform(post("/pronostics")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsBytes(pronosticsToSave)))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].user").value("jose"))
            .andExpect(jsonPath("$[1].user").value("pedro"))
    }

    @Test
    fun getAllPronosticsFromUser(){
        mockMvc.perform(get("/pronostics/{user}/{competition}","jose","PD"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].user").value("jose"))
    }

    @Test
    fun triyingToSaveAPronosticFromNotSavedMatch(){
        val pronostic = pronosticBuilder.build()

        mockMvc.perform(post("/pronostics")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsBytes(listOf(pronostic))))
            .andExpect(status().isNotFound)

    }

    @Test
    fun registerPronosticWithMatchedAlreadyStartedShouldFail(){

        val pronostic = pronosticBuilder.withUsername("jose").build().apply {
            val team1 = teamBuilder.build()
            val team2 = teamBuilder.build()
            teamRepository.save(team1)
            teamRepository.save(team2)
            // Se crea un match que ya paso su fecha de juego y esto tiene que tirar un badrequest
            match = matchBuilder.withDate(LocalDateTime.now().minusDays(2)).withLocal(team1).withAway(team2).withCode(10).withCompetition("PD").build()
            matchRepository.save(match)
        }

        mockMvc.perform(post("/pronostics")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsBytes(listOf(pronostic))))
            .andExpect(status().isBadRequest)
    }

}